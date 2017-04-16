package com.remnev.verbatoriamini.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.nfc.FormatException;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.remnev.verbatoriamini.util.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.INFCCallback;
import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;
import com.remnev.verbatoriamini.databases.ChildsDatabase;
import com.remnev.verbatoriamini.model.Child;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteChildCardFragment extends Fragment implements INeuroInterfaceCallback, INFCCallback {

    public ImageView statusImageView;
    public Button birthdayButton;
    public EditText childNameEditText;
    public EditText parentNameEditText;
    public EditText phoneEditText;
    public EditText emailEditText;
    public EditText cityEditText;
    public EditText diagnoseEditText;
    public EditText attentionEditText;
    public EditText mediationEditText;
    public EditText alfa1EditText;
    public EditText alfa2EditText;
    public EditText alfa3EditText;
    public EditText beta1EditText;
    public EditText beta2EditText;
    public EditText beta3EditText;
    public EditText gamma1EditText;
    public EditText gamma2EditText;
    public EditText deltaEditText;
    public EditText thetaEditText;
    public Button writeButton;
    private AlertDialog dialog;

    public WriteChildCardFragment() {
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
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_child_card, container, false);
        statusImageView = (ImageView) rootView.findViewById(R.id.attention_status);
        birthdayButton = (Button) rootView.findViewById(R.id.choose_date_button);
        childNameEditText = (EditText) rootView.findViewById(R.id.child_name);
        parentNameEditText = (EditText) rootView.findViewById(R.id.parent_name);
        phoneEditText = (EditText) rootView.findViewById(R.id.phone);
        emailEditText = (EditText) rootView.findViewById(R.id.email);
        cityEditText = (EditText) rootView.findViewById(R.id.city);
        diagnoseEditText = (EditText) rootView.findViewById(R.id.diagnose);
        attentionEditText = (EditText) rootView.findViewById(R.id.attention);
        mediationEditText = (EditText) rootView.findViewById(R.id.mediation);
        alfa1EditText = (EditText) rootView.findViewById(R.id.alfa1);
        alfa2EditText = (EditText) rootView.findViewById(R.id.alfa2);
        alfa3EditText = (EditText) rootView.findViewById(R.id.alfa3);
        beta1EditText = (EditText) rootView.findViewById(R.id.beta1);
        beta2EditText = (EditText) rootView.findViewById(R.id.beta2);
        beta3EditText = (EditText) rootView.findViewById(R.id.beta3);
        gamma1EditText = (EditText) rootView.findViewById(R.id.gamma1);
        gamma2EditText = (EditText) rootView.findViewById(R.id.gamma2);
        deltaEditText = (EditText) rootView.findViewById(R.id.delta);
        thetaEditText = (EditText) rootView.findViewById(R.id.theta);
        writeButton = (Button) rootView.findViewById(R.id.write_card);
        setUpViews();
        return rootView;
    }

    private boolean write = false;

    public void setUpViews() {
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
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
                    WriteChildCardFragment.this.dialog = dialog.create();
                    WriteChildCardFragment.this.dialog.show();
                } else {
                    Snackbar.make(writeButton, getString(R.string.error_write_child), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        final Calendar calendar = Calendar.getInstance();
        final Calendar birthday = Calendar.getInstance();
        birthdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        birthday.set(i, i1, i2);
                        birthdayButton.setText(simpleDateFormat.format(birthday.getTimeInMillis()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
    }

    public boolean checkValidation() {
        return !TextUtils.isEmpty(childNameEditText.getText().toString()) &&
                !TextUtils.isEmpty(parentNameEditText.getText().toString()) &&
                !TextUtils.isEmpty(phoneEditText.getText().toString()) &&
                !TextUtils.isEmpty(emailEditText.getText().toString()) &&
                !TextUtils.isEmpty(cityEditText.getText().toString()) &&
                !TextUtils.isEmpty(diagnoseEditText.getText().toString()) &&
                !TextUtils.isEmpty(attentionEditText.getText().toString()) &&
                !TextUtils.isEmpty(mediationEditText.getText().toString()) &&
                !TextUtils.isEmpty(alfa1EditText.getText().toString()) &&
                !TextUtils.isEmpty(alfa2EditText.getText().toString()) &&
                !TextUtils.isEmpty(alfa3EditText.getText().toString()) &&
                !TextUtils.isEmpty(beta1EditText.getText().toString()) &&
                !TextUtils.isEmpty(beta2EditText.getText().toString()) &&
                !TextUtils.isEmpty(beta3EditText.getText().toString()) &&
                !TextUtils.isEmpty(gamma1EditText.getText().toString()) &&
                !TextUtils.isEmpty(gamma2EditText.getText().toString()) &&
                !TextUtils.isEmpty(deltaEditText.getText().toString()) &&
                !TextUtils.isEmpty(thetaEditText.getText().toString()) &&
                !birthdayButton.getText().toString().equals(getString(R.string.date_of_birth));
    }

    public Child getChild() {
        Child child = new Child();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        child.setCreationDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
        child.setBirthday(birthdayButton.getText().toString());
        child.setChildName(childNameEditText.getText().toString());
        child.setParentName(parentNameEditText.getText().toString());
        child.setPhone(phoneEditText.getText().toString());
        child.setEmail(emailEditText.getText().toString());
        child.setCity(cityEditText.getText().toString());
        child.setDiagnose(diagnoseEditText.getText().toString());
        child.setAttention(Long.parseLong(attentionEditText.getText().toString()));
        child.setMediation(Long.parseLong(mediationEditText.getText().toString()));
        child.setAlfa1(Long.parseLong(alfa1EditText.getText().toString()));
        child.setAlfa2(Long.parseLong(alfa2EditText.getText().toString()));
        child.setAlfa3(Long.parseLong(alfa3EditText.getText().toString()));
        child.setBeta1(Long.parseLong(beta1EditText.getText().toString()));
        child.setBeta2(Long.parseLong(beta2EditText.getText().toString()));
        child.setBeta3(Long.parseLong(beta3EditText.getText().toString()));
        child.setGamma1(Long.parseLong(gamma1EditText.getText().toString()));
        child.setGamma2(Long.parseLong(gamma2EditText.getText().toString()));
        child.setDelta(Long.parseLong(deltaEditText.getText().toString()));
        child.setTheta(Long.parseLong(thetaEditText.getText().toString()));
        child.getConstuctedNeuroprofile();
        if (child.getConstructedID() == null) {
            return null;
        }
        return child;
    }

    Drawable firstDrawable;
    Drawable secondDrawable;

    @Override
    public void onNeuroInterfaceStateChanged(int code) {

    }

    @Override
    public void onNeuroDataReceived(int code, int attention) {

    }

    public void clearFields() {
        birthdayButton.setText(getString(R.string.date_of_birth));
        childNameEditText.setText("");
        parentNameEditText.setText("");
        phoneEditText.setText("");
        emailEditText.setText("");
        cityEditText.setText("");
        diagnoseEditText.setText("");
        attentionEditText.setText("");
        mediationEditText.setText("");
        alfa1EditText.setText("");
        alfa2EditText.setText("");
        alfa3EditText.setText("");
        beta1EditText.setText("");
        beta2EditText.setText("");
        beta3EditText.setText("");
        gamma1EditText.setText("");
        gamma2EditText.setText("");
        deltaEditText.setText("");
        thetaEditText.setText("");
    }


    @Override
    public void onNFCTagReaded(Tag msg) {
        if (write && msg != null) {
            Child child = getChild();
            if (child == null) {
                if (dialog != null) {
                    dialog.dismiss();
                    Helper.showSnackBar(writeButton, getString(R.string.write_card_error));
                    return;
                }
            }
            try {
                Helper.write(LoganSquare.serialize(child), msg, getActivity());
                ChildsDatabase.addChild(getActivity(), child);
                if (dialog != null) {
                    dialog.dismiss();
                    Helper.showSnackBar(writeButton, getString(R.string.write_card_success));
                }
                clearFields();
            } catch (IOException e) {
                Helper.showSnackBar(writeButton, getString(R.string.write_card_error));
                e.printStackTrace();
            } catch (FormatException e) {
                Helper.showSnackBar(writeButton, getString(R.string.write_card_error));
                e.printStackTrace();
            }

        }
    }
}

