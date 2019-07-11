package com.verbatoria.di

/**
 * @author n.remnev
 */

interface BaseInjector<I : Any> {

    fun inject(i: I)

}