package com.verbatoria.ui.session.presenter.submit.school;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.verbatoria.ui.session.view.submit.school.ISchoolSubmitView;

/**
 * Презентер для экрана отправки результатов для режима школы
 *
 * @author nikitaremnev
 */

public interface ISchoolSubmitPresenter {

    void bindView(@NonNull ISchoolSubmitView schoolSubmitView);
    void unbindView();

    void sendResults();

}
