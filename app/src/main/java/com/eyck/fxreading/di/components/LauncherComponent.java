package com.eyck.fxreading.di.components;

import com.eyck.fxreading.di.modules.LauncherModule;
import com.eyck.fxreading.di.scopes.UserScope;
import com.eyck.fxreading.view.activity.LauncherActivity;

import dagger.Component;

/**
 * Created by Eyck on 2017/9/1.
 */

@UserScope
@Component(modules = LauncherModule.class,dependencies = NetComponent.class)
public interface LauncherComponent {
    void inject(LauncherActivity launcherActivity);
}
