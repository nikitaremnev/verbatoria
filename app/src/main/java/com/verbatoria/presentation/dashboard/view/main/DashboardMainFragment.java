package com.verbatoria.presentation.dashboard.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.IDashboardPresenter;
import com.verbatoria.presentation.dashboard.view.IDashboardView;
import com.verbatoria.presentation.dashboard.view.main.events.IVerbatologEventsView;
import com.verbatoria.presentation.dashboard.view.main.events.VerbatologEventsFragment;
import com.verbatoria.presentation.dashboard.view.main.info.IVerbatologInfoView;
import com.verbatoria.presentation.dashboard.view.main.info.VerbatologInfoFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Фрагмент для "Главная"
 *
 * @author nikitaremnev
 */
public class DashboardMainFragment extends Fragment implements IDashboardView {

    @Inject
    IDashboardPresenter mDashboardPresenter;

    private IVerbatologInfoView mVerbatologInfoView;
    private IVerbatologEventsView mVerbatologEventsView;

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


        return inflater.inflate(R.layout.fragment_dashboard_main, container, false);
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
    }

}
