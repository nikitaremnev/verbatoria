package com.verbatoria.presentation.dashboard.view.main.events;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.remnev.verbatoriamini.R;
import com.verbatoria.business.dashboard.models.EventModel;
import java.util.List;

/**
 * Фрагмент для отображения информации о ближайших событиях вербатолога
 *
 * @author nikitaremnev
 */
public class VerbatologEventsFragment extends Fragment implements IVerbatologEventsView {

    private RecyclerView mEventsRecyclerView;

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
        mEventsRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_verbatolog_events, container, false);
        setUpRecyclerView();
        return mEventsRecyclerView;
    }

    private void setUpRecyclerView() {
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mEventsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void showVerbatologEvents(List<EventModel> verbatologEvents) {
        mEventsRecyclerView.setAdapter(new VerbatologEventsAdapter(verbatologEvents));
    }
}
