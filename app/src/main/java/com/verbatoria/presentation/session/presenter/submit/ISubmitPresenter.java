package com.verbatoria.presentation.session.presenter.submit;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.verbatoria.presentation.session.view.submit.ISubmitView;

import java.util.Map;

/**
 * Презентер для экрана отправки результатов
 *
 * @author nikitaremnev
 */
public interface ISubmitPresenter {

    void bindView(@NonNull ISubmitView submitView);
    void unbindView();

    void sendResults(Map<String, String> answers);
    void obtainEvent(Intent intent);
}
