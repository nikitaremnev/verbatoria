package com.verbatoria.di.login;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by nikitaremnev on 30.05.17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginScope {
}
