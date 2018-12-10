package com.verbatoria.presentation.calendar.view.add.clients.search;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.presentation.calendar.view.add.children.ChildActivity;

import java.lang.ref.SoftReference;
import java.util.List;

import javax.inject.Inject;

/**
 * Адаптер для отображения найденных детей
 *
 * @author nikitaremnev
 */
public class ChildrenAdapter extends RecyclerView.Adapter<ChildViewHolder> {

    private final List<ChildModel> mChildModelList;

    @Inject
    public Context mContext;

    private SoftReference<Activity> mActivitySoftReference;

    public ChildrenAdapter(@NonNull List<ChildModel> childModels, Activity activity) {
        VerbatoriaApplication.getApplicationComponent().inject(this);
        mChildModelList = childModels;
        mActivitySoftReference = new SoftReference<>(activity);
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mChildModelList.size();
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        ChildModel childModel = mChildModelList.get(position);
        holder.setChildName(childModel.getName());
        holder.setChildBirthday(childModel.getBirthdayDateString());
        holder.setOnClickListener(new ChildOnClickListener(childModel));
    }

    private class ChildOnClickListener implements View.OnClickListener {

        private SoftReference<ChildModel> mClientModelSoftReference;

        ChildOnClickListener(ChildModel childModel) {
            mClientModelSoftReference = new SoftReference<>(childModel);
        }

        @Override
        public void onClick(View v) {
            Activity activity = mActivitySoftReference.get();
            ChildModel childModel = mClientModelSoftReference.get();
            if (activity != null && childModel != null) {
                activity.setResult(Activity.RESULT_OK, ChildActivity.createChildIntent(childModel));
                activity.finish();
            }
        }
    }


}
