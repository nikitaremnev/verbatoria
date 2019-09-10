package com.verbatoria.di.submit

import com.verbatoria.ui.submit.SubmitPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class SubmitModule {

    @Provides
    @Reusable
    fun provideSubmitPresenter(): SubmitPresenter =
        SubmitPresenter()

}