package com.verbatoria.di.calendar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * @author nikitaremnev
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface CalendarScope {

}
