package com.verbatoria.presentation.late_send.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.late_send.view.ILateSendView;

/**
 * Презентер для экрана поздней отправки
 *
 * @author nikitaremnev
 */
public interface ILateSendPresenter {

    void bindView(@NonNull ILateSendView lateSendView);

    void unbindView();

    void sendReport(int position);

}
