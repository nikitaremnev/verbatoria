package com.remnev.verbatoriamini.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.remnev.verbatoriamini.util.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.INFCCallback;
import com.remnev.verbatoriamini.model.Certificate;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthorityFragment extends Fragment implements INFCCallback {

    public static boolean read = true;
    private ImageView imageView;

    public AuthorityFragment() {
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
        View rootView = inflater.inflate(R.layout.activity_authority, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        return rootView;
    }

    @Override
    public void onNFCTagReaded(Tag msg) {
        Log.e("hello", "i'm in authority fragment");
        if (msg != null && read) {
            read = false;
            String readedText = Helper.readTag(msg, getActivity());
            if (TextUtils.isEmpty(readedText)) {
                showNotCertificate();
                read = true;
                return;
            } else {
                try {
                    Certificate certificate = new Certificate();
                    if (certificate.parseCertificate(readedText)) {
                        if (SpecialistSharedPrefs.getCurrentSpecialist(getActivity()) == null) {
                            if (checkDate(certificate)) {
                                showExpiryDialog();
                                read = true;
//                                return;
                            }
                            SpecialistSharedPrefs.setCertificateExpired(getActivity(), false);
                            SpecialistSharedPrefs.setCurrentSpecialist(getActivity(), certificate);
                            startApplication(certificate);
                            return;
                        } else if (LoganSquare.serialize(SpecialistSharedPrefs.getCurrentSpecialist(getActivity())).equals(LoganSquare.serialize(certificate))) {//cvc is correct //if (specialist.getCvc().equals(SpecialistSharedPrefs.getCurrentSpecialist(AuthorityActivity.this).getCvc())) {
                            if (checkDate(certificate)) {
                                showExpiryDialog();
                                read = true;
//                                return;
                            }
                            SpecialistSharedPrefs.setCertificateExpired(getActivity(), false);
                            startApplication(certificate);
                            return;
                        } else {
                            showCvcNotEqual();
                            read = true;
                            return;
                        }
                    } else {
                        showNotCertificate();
                        read = true;
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showNotCertificate();
                    read = true;
                    return;
                }
            }

        }
    }

    private boolean checkDate(Certificate certificate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(System.currentTimeMillis());
        Log.e("currentDate", "currentDate: " + currentDate);
        try {
            return simpleDateFormat.parse(certificate.getExpiry()).before(simpleDateFormat.parse(currentDate));
        } catch (ParseException e) {
            return false;
        }
    }

    private void showExpiryDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(getString(R.string.authority_expiry));
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SpecialistSharedPrefs.setCertificateExpired(getActivity(), true);
            }
        });
        dialog.show();
    }

    private void showCvcNotEqual() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(getString(R.string.cvc_not_equal));
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void showNotCertificate() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(getString(R.string.tag_not_specialist));
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void startApplication(Certificate certificate) {
        Snackbar snackbar = Snackbar.make(imageView, (String.format(getString(R.string.authority_success), certificate.getSpecialistName()) + " " + certificate.getExpiry()), Snackbar.LENGTH_LONG);
        snackbar.show();
//        final long currentTime = System.currentTimeMillis();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (System.currentTimeMillis() - currentTime < 1000) {}
//                Intent intent = new Intent(AuthorityActivity.this, NavigationDrawerActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                AuthorityActivity.this.finish();
//                startActivity(intent);
//            }
//        });
//        thread.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        read = true;
    }

}

