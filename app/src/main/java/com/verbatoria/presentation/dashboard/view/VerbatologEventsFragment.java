package com.verbatoria.presentation.dashboard.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.remnev.verbatoriamini.R;
import butterknife.ButterKnife;

/**
 * Фрагмент для отображения информации о ближайших событиях вербатолога
 *
 * @author nikitaremnev
 */
public class VerbatologEventsFragment extends Fragment implements IVerbatologEventsView {


    public VerbatologEventsFragment() {
        // Required empty public constructor
    }

    public static VerbatologEventsFragment newInstance() {
        return new VerbatologEventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verbatolog_events, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

}
