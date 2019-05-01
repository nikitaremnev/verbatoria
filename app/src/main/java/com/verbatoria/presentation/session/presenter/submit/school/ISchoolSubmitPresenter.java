package com.verbatoria.presentation.session.presenter.submit.school;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.verbatoria.presentation.session.view.submit.school.ISchoolSubmitView;

/**
 * Презентер для экрана отправки результатов для режима школы
 *
 * @author nikitaremnev
 */

public interface ISchoolSubmitPresenter {

    void bindView(@NonNull ISchoolSubmitView schoolSubmitView);
    void unbindView();

    void sendResults();
    void obtainEvent(Intent intent);

}
