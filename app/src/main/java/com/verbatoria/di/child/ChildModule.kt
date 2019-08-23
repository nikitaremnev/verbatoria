package com.verbatoria.di.child

import com.verbatoria.ui.child.ChildPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class ChildModule {

    @Provides
    @Reusable
    fun provideChildPresenter(): ChildPresenter =
        ChildPresenter()

}