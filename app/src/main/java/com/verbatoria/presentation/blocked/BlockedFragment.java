package com.verbatoria.presentation.blocked;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remnev.verbatoria.R;

/**
 * Фрагмент для отображения заблокированного состояния
 *
 * @author nikitaremnev
 */
public class BlockedFragment extends Fragment {

    public BlockedFragment() {
        // Required empty public constructor
    }

    public static BlockedFragment newInstance() {
        return new BlockedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blocked, container, false);
    }
}
