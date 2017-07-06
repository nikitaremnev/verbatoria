package com.verbatoria.presentation.dashboard.view.main.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.remnev.verbatoriamini.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Фрагмент для отображения информации о вербатологе
 *
 * @author nikitaremnev
 */
public class VerbatologInfoFragment extends Fragment implements IVerbatologInfoView {

    @BindView(R.id.name_text_view)
    public TextView verbatologNameTextView;

    @BindView(R.id.phone_text_view)
    public TextView verbatologPhoneTextView;

    @BindView(R.id.email_text_view)
    public TextView verbatologEmailTextView;

    public VerbatologInfoFragment() {
        // Required empty public constructor
    }

    public static VerbatologInfoFragment newInstance() {
        return new VerbatologInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verbatolog_info, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void showVerbatologName(String verbatologName) {
        verbatologNameTextView.setText(verbatologName);
    }

    @Override
    public void showVerbatologEmail(String verbatologEmail) {
        verbatologEmailTextView.setText(verbatologEmail);
    }

    @Override
    public void showVerbatologPhone(String verbatologPhone) {
        verbatologPhoneTextView.setText(verbatologPhone);
    }

}
