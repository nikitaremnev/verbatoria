package com.verbatoria.di.dashboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author nikitaremnev
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DashboardScope {
}
