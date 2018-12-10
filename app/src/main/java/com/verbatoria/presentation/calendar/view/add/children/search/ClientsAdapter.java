package com.verbatoria.presentation.calendar.view.add.children.search;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.presentation.calendar.view.add.clients.ClientsActivity;

import java.lang.ref.SoftReference;
import java.util.List;

import javax.inject.Inject;

/**
 * Адаптер для отображения найденных клиентов
 *
 * @author nikitaremnev
 */
public class ClientsAdapter extends RecyclerView.Adapter<ClientViewHolder> {

    private final List<ClientModel> mClientModelList;

    @Inject
    public Context mContext;

    private SoftReference<Activity> mActivitySoftReference;

    public ClientsAdapter(@NonNull List<ClientModel> clientModels, Activity activity) {
        VerbatoriaApplication.getApplicationComponent().inject(this);
        mClientModelList = clientModels;
        mActivitySoftReference = new SoftReference<>(activity);
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mClientModelList.size();
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {
        ClientModel clientModel = mClientModelList.get(position);
        holder.setClientName(clientModel.getName());
        holder.setClientPhone(clientModel.getPhone());
        holder.setClientEmail(clientModel.getEmail());
        holder.setOnClickListener(new ClientOnClickListener(clientModel));
    }

    private class ClientOnClickListener implements View.OnClickListener {

        private SoftReference<ClientModel> mClientModelSoftReference;

        ClientOnClickListener(ClientModel clientModel) {
            mClientModelSoftReference = new SoftReference<>(clientModel);
        }

        @Override
        public void onClick(View v) {
            Activity activity = mActivitySoftReference.get();
            ClientModel clientModel = mClientModelSoftReference.get();
            if (activity != null && clientModel != null) {
                activity.setResult(Activity.RESULT_OK, ClientsActivity.createClientIntent(clientModel));
                activity.finish();
            }
        }
    }


}
