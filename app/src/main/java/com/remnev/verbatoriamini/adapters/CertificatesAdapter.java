package com.remnev.verbatoriamini.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.model.Certificate;
import java.util.List;

public class CertificatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Certificate> mCertificateList;
    private ICertificateClick mCertificateClick;

    public interface ICertificateClick {
        void onCertificateClick(int position);
    }

    public class CertificateViewHolder extends RecyclerView.ViewHolder {

        TextView certificateName;
        View rootView;

        public CertificateViewHolder(View view) {
            super(view);
            rootView = view;
            certificateName = (TextView) itemView.findViewById(R.id.itemName);
        }
    }

    public CertificatesAdapter(Context mContext, List<Certificate> certificateList, ICertificateClick certificateClick) {
        this.mContext = mContext;
        this.mCertificateClick = certificateClick;
        this.mCertificateList = certificateList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_certificate, parent, false);
        return new CertificateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Certificate certificate = mCertificateList.get(position);
        ((CertificateViewHolder) holder).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCertificateClick != null) {
                    mCertificateClick.onCertificateClick(position);
                }
            }
        });
        ((CertificateViewHolder) holder).certificateName.setText(certificate.getSpecialistName());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mCertificateList.size();
    }


}

