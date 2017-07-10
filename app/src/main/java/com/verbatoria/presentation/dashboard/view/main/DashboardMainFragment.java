package com.verbatoria.presentation.dashboard.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.IDashboardMainPresenter;
import com.verbatoria.presentation.dashboard.view.main.events.IVerbatologEventsView;
import com.verbatoria.presentation.dashboard.view.main.events.VerbatologEventsFragment;
import com.verbatoria.presentation.dashboard.view.main.info.IVerbatologInfoView;
import com.verbatoria.presentation.dashboard.view.main.info.VerbatologInfoFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Фрагмент для "Главная"
 *
 * @author nikitaremnev
 */
public class DashboardMainFragment extends Fragment implements IDashboardMainView {

    @Inject
    IDashboardMainPresenter mDashboardPresenter;

    private IVerbatologInfoView mVerbatologInfoView;
    private IVerbatologEventsView mVerbatologEventsView;

    @BindView(R.id.events_text_view)
    public TextView mEventsTextView;

    public DashboardMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DashboardMainFragment.
     */
    public static DashboardMainFragment newInstance() {
        return new DashboardMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpFragments();

        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new DashboardModule()).inject(this);
        mDashboardPresenter.bindView(this);
        mDashboardPresenter.updateVerbatologInfo();
        mDashboardPresenter.updateVerbatologEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDashboardPresenter.unbindView();
    }

    private void setUpFragments() {
        setUpVerbatologInfoFragment();
        setUpVerbatologEventsFragment();
    }

    private void setUpVerbatologInfoFragment() {
        VerbatologInfoFragment verbatologInfoFragment = VerbatologInfoFragment.newInstance();
        mVerbatologInfoView = verbatologInfoFragment;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.verbatolog_info_frame_layout, verbatologInfoFragment);
        transaction.commit();
    }

    private void setUpVerbatologEventsFragment() {
        VerbatologEventsFragment verbatologEventsFragment = VerbatologEventsFragment.newInstance();
        mVerbatologEventsView = verbatologEventsFragment;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.verbatolog_events_frame_layout, verbatologEventsFragment);
        transaction.commit();
    }

    private void setUpEventsLabel(int eventsSize) {
        if (eventsSize == 0) {
            mEventsTextView.setText(getString(R.string.dashboard_no_events_title));
        } else {
            mEventsTextView.setText(getString(R.string.dashboard_events_title));
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showVerbatologInfo(String verbatologFullName, String verbatologPhone, String verbatologEmail) {
        mVerbatologInfoView.showVerbatologName(verbatologFullName);
        mVerbatologInfoView.showVerbatologPhone(verbatologPhone);
        mVerbatologInfoView.showVerbatologEmail(verbatologEmail);
    }

    @Override
    public void showVerbatologEvents(List<EventModel> verbatologEvents) {
        mVerbatologEventsView.showVerbatologEvents(verbatologEvents);
        setUpEventsLabel(verbatologEvents.size());
    }

}
