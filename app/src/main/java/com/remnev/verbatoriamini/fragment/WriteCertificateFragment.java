package com.remnev.verbatoriamini.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;

import com.remnev.verbatoriamini.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;
import com.remnev.verbatoriamini.callbacks.INFCCallback;
import com.remnev.verbatoriamini.databases.CertificatesDatabase;
import com.remnev.verbatoriamini.model.Certificate;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteCertificateFragment extends Fragment implements INeuroInterfaceCallback, INFCCallback {

    public ImageView statusImageView;
    public Spinner profileSpinner;
    public AutoCompleteTextView specialistNameEditText;
    public AutoCompleteTextView phoneEditText;
    public AutoCompleteTextView emailEditText;
    public AutoCompleteTextView cityEditText;
    public Button writeButton;
    public Button expiryButton;
    private AlertDialog dialog;
    private List<Certificate> certificatesList;

    public WriteCertificateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbarButton();
    }

    private void setUpToolbarButton() {
//        ImageView imageView = (ImageView) getActivity().findViewById(R.id.exit);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().finish();
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_specialist_card, container, false);
        statusImageView = (ImageView) rootView.findViewById(R.id.attention_status);
        specialistNameEditText = (AutoCompleteTextView) rootView.findViewById(R.id.child_name);
        phoneEditText = (AutoCompleteTextView) rootView.findViewById(R.id.phone);
        emailEditText = (AutoCompleteTextView) rootView.findViewById(R.id.email);
        cityEditText = (AutoCompleteTextView) rootView.findViewById(R.id.city);
        profileSpinner = (Spinner) rootView.findViewById(R.id.spinnerProfile);
        writeButton = (Button) rootView.findViewById(R.id.write_card);
        expiryButton = (Button) rootView.findViewById(R.id.choose_date_button);
        setUpViews();
        setUpAutoCompleteListeners();
        return rootView;
    }

    private boolean write = false;

    public void setUpViews() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getActivity().getResources().getStringArray(R.array.profiles)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileSpinner.setAdapter(spinnerArrayAdapter);

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
                    WriteCertificateFragment.this.dialog = dialog.create();
                    WriteCertificateFragment.this.dialog.show();
                } else {
                    Snackbar.make(writeButton, getString(R.string.error_write_child), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        final Calendar calendar = Calendar.getInstance();
        final Calendar expiry = Calendar.getInstance();
        expiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        expiry.set(i, i1, i2);
                        expiryButton.setText(simpleDateFormat.format(expiry.getTimeInMillis()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
    }

    public boolean checkValidation() {
        return !TextUtils.isEmpty(specialistNameEditText.getText().toString()) &&
                !TextUtils.isEmpty(phoneEditText.getText().toString()) &&
                !TextUtils.isEmpty(emailEditText.getText().toString()) &&
                !TextUtils.isEmpty(cityEditText.getText().toString()) &&
                !expiryButton.getText().toString().equals(getString(R.string.expiry));
    }

    public Certificate getSpecialist() {
        Certificate certificate = new Certificate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        certificate.setCreationDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
        certificate.setSpecialistName(specialistNameEditText.getText().toString());
        certificate.setPhone(phoneEditText.getText().toString());
        certificate.setEmail(emailEditText.getText().toString());
        certificate.setCity(cityEditText.getText().toString());
        certificate.setSpecialistProfile(profileSpinner.getSelectedItemPosition());
        certificate.setExpiry(expiryButton.getText().toString());
        if (certificate.getConstructedID() == null) {
            return null;
        }
        return certificate;
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
        specialistNameEditText.setText("");
        phoneEditText.setText("");
        emailEditText.setText("");
        cityEditText.setText("");
        expiryButton.setText(getString(R.string.expiry));
        profileSpinner.setSelection(0);
    }


    @Override
    public void onNFCTagReaded(Tag msg) {
        if (write && msg != null) {
            Certificate certificate = getSpecialist();
            if (certificate == null) {
                if (dialog != null) {
                    write = false;
                    dialog.dismiss();
                    Helper.snackBar(writeButton, getString(R.string.write_card_error));
                    return;
                }
            }
            try {
                Helper.write(certificate.certificateToString(), msg, getActivity());
                SpecialistSharedPrefs.setCurrentSpecialist(getActivity(), certificate);
                CertificatesDatabase.addCertificate(getActivity(), certificate);
                setUpAutoCompleteListeners();
                if (dialog != null) {
                    write = false;
                    dialog.dismiss();
                    Helper.snackBar(writeButton, getString(R.string.write_card_specialist_success));
                }
                clearFields();
            } catch (IOException e) {
                Helper.snackBar(writeButton, getString(R.string.write_card_error));
                e.printStackTrace();
            } catch (FormatException e) {
                Helper.snackBar(writeButton, getString(R.string.write_card_error));
                e.printStackTrace();
            }
            write = false;
        } else if (msg != null) {
            try {
                String readedText = Helper.readTag(msg, getActivity());
                Log.e("readedText", readedText);
                Certificate certificate = new Certificate();
                if (certificate.parseCertificate(readedText)) {
                    specialistNameEditText.setText(certificate.getSpecialistName());
                    phoneEditText.setText(certificate.getPhone());
                    cityEditText.setText(certificate.getCity());
                    emailEditText.setText(certificate.getEmail());
                    expiryButton.setText(certificate.getExpiry());
                    profileSpinner.setSelection(certificate.getSpecialistProfile());
                } else {
                    Helper.snackBar(writeButton, getString(R.string.tag_not_specialist));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Helper.snackBar(writeButton, getString(R.string.tag_empty));
            }
        }
    }

    private void setUpAutoCompleteListeners() {
        certificatesList = CertificatesDatabase.readAllCertificates(getActivity());
        final List<String> names = new ArrayList<>();
        final List<String> phones = new ArrayList<>();
        final List<String> cities = new ArrayList<>();
        final List<String> emails = new ArrayList<>();
        for (int i = 0; i < certificatesList.size(); i++) {
            names.add(certificatesList.get(i).getSpecialistName());
            phones.add(certificatesList.get(i).getPhone());
            cities.add(certificatesList.get(i).getCity());
            emails.add(certificatesList.get(i).getEmail());
        }

        final ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
        namesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> phonesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, phones);
        phonesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cities);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> emailsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, emails);
        emailsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        specialistNameEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String currentName = specialistNameEditText.getText().toString();
                for (int i = 0 ; i < names.size(); i ++) {
                    if (names.get(i).toLowerCase().equals(currentName.toLowerCase())) {
                        specialistNameEditText.setText(names.get(i));
                        phoneEditText.setText(phones.get(i));
                        cityEditText.setText(cities.get(i));
                        emailEditText.setText(emails.get(i));
                        break;
                    }
                }
            }
        });
        specialistNameEditText.setAdapter(namesAdapter);
        specialistNameEditText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentName = specialistNameEditText.getText().toString();
                for (int i = 0 ; i < names.size(); i ++) {
                    if (names.get(i).toLowerCase().equals(currentName.toLowerCase())) {
                        specialistNameEditText.setText(names.get(i));
                        phoneEditText.setText(phones.get(i));
                        cityEditText.setText(cities.get(i));
                        emailEditText.setText(emails.get(i));
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //phones
        phoneEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String currentPhone = phoneEditText.getText().toString();
                for (int i = 0 ; i < phones.size(); i ++) {
                    if (phones.get(i).toLowerCase().equals(currentPhone.toLowerCase())) {
                        specialistNameEditText.setText(names.get(i));
                        phoneEditText.setText(phones.get(i));
                        cityEditText.setText(cities.get(i));
                        emailEditText.setText(emails.get(i));
                        break;
                    }
                }
            }
        });
        phoneEditText.setAdapter(phonesAdapter);
        phoneEditText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentPhone = phoneEditText.getText().toString();
                for (int i = 0 ; i < phones.size(); i ++) {
                    if (phones.get(i).toLowerCase().equals(currentPhone.toLowerCase())) {
                        specialistNameEditText.setText(names.get(i));
                        phoneEditText.setText(phones.get(i));
                        cityEditText.setText(cities.get(i));
                        emailEditText.setText(emails.get(i));
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //cities
        cityEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String currentCity = cityEditText.getText().toString();
                for (int i = 0 ; i < cities.size(); i ++) {
                    if (cities.get(i).toLowerCase().equals(currentCity.toLowerCase())) {
                        cityEditText.setText(cities.get(i));
                        break;
                    }
                }
            }
        });
        cityEditText.setAdapter(citiesAdapter);
        cityEditText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentCity = cityEditText.getText().toString();
                for (int i = 0 ; i < cities.size(); i ++) {
                    if (cities.get(i).toLowerCase().equals(currentCity.toLowerCase())) {
                        cityEditText.setText(cities.get(i));
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //emails
        emailEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String currentEmail = emailEditText.getText().toString();
                for (int i = 0 ; i < emails.size(); i ++) {
                    if (emails.get(i).toLowerCase().equals(currentEmail.toLowerCase())) {
                        specialistNameEditText.setText(names.get(i));
                        phoneEditText.setText(phones.get(i));
                        cityEditText.setText(cities.get(i));
                        emailEditText.setText(emails.get(i));
                        break;
                    }
                }
            }
        });
        emailEditText.setAdapter(emailsAdapter);
        emailEditText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentEmail = emailEditText.getText().toString();
                for (int i = 0 ; i < emails.size(); i ++) {
                    if (emails.get(i).toLowerCase().equals(currentEmail.toLowerCase())) {
                        specialistNameEditText.setText(names.get(i));
                        phoneEditText.setText(phones.get(i));
                        cityEditText.setText(cities.get(i));
                        emailEditText.setText(emails.get(i));
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

