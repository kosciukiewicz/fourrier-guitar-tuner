package com.witold.dagger_mvp_taks_app.DependencyInjection.Scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Witold on 26.08.2017.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {
}
