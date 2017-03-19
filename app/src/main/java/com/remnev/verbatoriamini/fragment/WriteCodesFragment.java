package com.remnev.verbatoriamini.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.nfc.FormatException;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bluelinelabs.logansquare.LoganSquare;
import com.remnev.verbatoriamini.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.OnBCIConnectionCallback;
import com.remnev.verbatoriamini.callbacks.OnNewIntentCallback;
import com.remnev.verbatoriamini.databases.CertificatesDatabase;
import com.remnev.verbatoriamini.model.Certificate;
import com.remnev.verbatoriamini.model.Code;
import com.remnev.verbatoriamini.model.ExcelBCI;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteCodesFragment extends Fragment implements OnBCIConnectionCallback, OnNewIntentCallback {

    public ImageView statusImageView;
    public EditText codeEditText;
    public Button writeButton;
    private AlertDialog dialog;

    public WriteCodesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbarButton();
    }

    private void setUpToolbarButton() {
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.exit);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_codes, container, false);
        statusImageView = (ImageView) rootView.findViewById(R.id.attention_status);
        codeEditText = (EditText) rootView.findViewById(R.id.code_edittext);
        writeButton = (Button) rootView.findViewById(R.id.write_card);
        setUpViews();
        return rootView;
    }

    private boolean write = false;

    public void setUpViews() {
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (!TextUtils.isEmpty(codeEditText.getText().toString())) {
                    try {
                        Integer.parseInt(codeEditText.getText().toString());
                    } catch (Exception ex) {
                        Helper.snackBar(writeButton, getString(R.string.incorrect_code));
                        ex.printStackTrace();
                        return;
                    }
                    write = true;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.progress_dialog, null);
                    dialog.setView(dialogView);
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            write = false;
                        }
                    });
                    WriteCodesFragment.this.dialog = dialog.create();
                    WriteCodesFragment.this.dialog.show();
                } else {
                    Snackbar.make(writeButton, getString(R.string.error_write_code), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    Drawable firstDrawable;
    Drawable secondDrawable;

    @Override
    public void onMessageReceived(Message message) {

    }

    @Override
    public void animateStatusChanged(int value) {
        if (secondDrawable != null) {
            firstDrawable = secondDrawable;
        }
        if (value < 20) {
            secondDrawable = getResources().getDrawable(R.drawable.status_lowest);
        } else if (value < 40) {
            secondDrawable = getResources().getDrawable(R.drawable.status_low);
        } else if (value < 60) {
            secondDrawable = getResources().getDrawable(R.drawable.status_middle);
        } else if (value < 80) {
            secondDrawable = getResources().getDrawable(R.drawable.status_high);
        } else if (value < 100) {
            secondDrawable = getResources().getDrawable(R.drawable.status_highest);
        }
        if (firstDrawable == null) {
            firstDrawable = getResources().getDrawable(R.drawable.status_middle);
        }
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[] {
                firstDrawable,
                secondDrawable,
        });
        statusImageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(700);
        Helper.animateStatusChange(firstDrawable, secondDrawable, getActivity(), statusImageView, value);
    }

    public void clearFields() {
        codeEditText.setText("");
    }


    @Override
    public void promptForContent(Tag msg) {
        if (write && msg != null) {
            try {
                Code code = new Code(Integer.parseInt(codeEditText.getText().toString()));
                Helper.write(LoganSquare.serialize(code), msg, getActivity());
                if (dialog != null) {
                    write = false;
                    dialog.dismiss();
                    Helper.snackBar(writeButton, getString(R.string.write_card_code_success));
                }
            } catch (FormatException e) {
                Helper.snackBar(writeButton, getString(R.string.write_card_error));
                e.printStackTrace();
            } catch (NumberFormatException e) {
                Helper.snackBar(writeButton, getString(R.string.incorrect_code));
                e.printStackTrace();
            } catch (IOException e) {
                Helper.snackBar(writeButton, getString(R.string.write_card_error));
                e.printStackTrace();
            }
            if (dialog != null) {
                write = false;
                dialog.dismiss();
            }
            clearFields();
        } else if (msg != null) {
            try {
                String readedText = Helper.readTag(msg, getActivity());
                try {
                    Code code = LoganSquare.parse(readedText, Code.class);
                    if (code != null) {
                        codeEditText.setText("" + code.getCode());
                        codeEditText.setSelection(Integer.toString(code.getCode()).length());
                    }
                } catch (Exception ex) {
                    Helper.snackBar(writeButton, getString(R.string.tag_not_code));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Helper.snackBar(writeButton, getString(R.string.tag_empty));
            }
        }
    }

}

