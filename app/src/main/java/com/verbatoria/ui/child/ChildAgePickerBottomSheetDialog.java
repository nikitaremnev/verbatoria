package com.verbatoria.ui.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.remnev.verbatoria.R;
import com.verbatoria.ui.calendar.view.add.children.age.ChildAgeClickListener;

/**
 * Диалог для выбора даты
 *
 * @author nikitaremnev
 */
public class ChildAgePickerBottomSheetDialog extends BottomSheetDialogFragment {

    public static final int START_AGE = 4;
    public static final int SCHOOL_MODE_END_AGE = 17;
    private static final int END_AGE = 65;

    private static final int COLUMN_COUNT = 4;

    private static final String SCHOOL_ACCOUNT_EXTRA = "SCHOOL_ACCOUNT_EXTRA";

    private ChildAgeClickListener mChildAgeClickListener;

    private boolean isSchoolAccount;

    public static ChildAgePickerBottomSheetDialog newInstance(boolean isSchoolAccount) {
        ChildAgePickerBottomSheetDialog fragment = new ChildAgePickerBottomSheetDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(SCHOOL_ACCOUNT_EXTRA, isSchoolAccount);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isSchoolAccount = getArguments().getBoolean(SCHOOL_ACCOUNT_EXTRA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_child_age, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMN_COUNT));
        recyclerView.setAdapter(new ChildAgeAdapter((isSchoolAccount ? SCHOOL_MODE_END_AGE : END_AGE) - START_AGE + 1));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mChildAgeClickListener = (ChildAgeClickListener) parent;
        } else {
            mChildAgeClickListener = (ChildAgeClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        mChildAgeClickListener = null;
        super.onDetach();
    }

    class ChildAgeViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        ChildAgeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_child_age, parent, false));
            text = itemView.findViewById(R.id.text);
            text.setOnClickListener(v -> {
                if (mChildAgeClickListener != null) {
                    mChildAgeClickListener.onChildAgeClicked(START_AGE + getAdapterPosition());
                    dismiss();
                }
            });
        }

    }

    class ChildAgeAdapter extends RecyclerView.Adapter<ChildAgePickerBottomSheetDialog.ChildAgeViewHolder> {

        private final int mItemCount;

        ChildAgeAdapter(int itemCount) {
            mItemCount = itemCount;
        }

        @Override
        public ChildAgePickerBottomSheetDialog.ChildAgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ChildAgePickerBottomSheetDialog.ChildAgeViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ChildAgePickerBottomSheetDialog.ChildAgeViewHolder holder, int position) {
            holder.text.setText(String.valueOf(START_AGE + position));
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }

    }

}