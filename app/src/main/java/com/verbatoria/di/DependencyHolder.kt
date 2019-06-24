package com.verbatoria.di

/**
 * @author n.remnev
 */

interface DependencyHolder<C> {

    fun put(key: String, dependency: Any)

    fun <D> pop(key: String): D?

    val component: C

}