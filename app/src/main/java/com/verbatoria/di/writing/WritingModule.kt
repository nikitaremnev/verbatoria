package com.verbatoria.di.writing

import com.verbatoria.ui.writing.WritingPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class WritingModule {

    @Provides
    @Reusable
    fun provideWritingPresenter(): WritingPresenter =
        WritingPresenter()

}