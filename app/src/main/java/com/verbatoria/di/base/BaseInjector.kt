package com.verbatoria.di.base

/**
 * @author n.remnev
 */

interface BaseInjector<I : Any> {

    fun inject(i: I)

}