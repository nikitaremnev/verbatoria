package com.verbatoria.ui.session.presenter.submit;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.verbatoria.ui.session.view.submit.ISubmitView;

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


}
