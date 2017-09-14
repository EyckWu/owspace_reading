package com.eyck.fxreading.di.components;

import com.eyck.fxreading.di.modules.DailyModule;
import com.eyck.fxreading.di.scopes.UserScope;
import com.eyck.fxreading.view.activity.DailyActivity;

import dagger.Component;

/**
 * Created by Eyck on 2017/9/12.
 */

@UserScope
@Component(modules = DailyModule.class,dependencies = NetComponent.class)
public interface DailyComponent {
    void inject(DailyActivity activity);
}
