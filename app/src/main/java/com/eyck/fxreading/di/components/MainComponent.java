package com.eyck.fxreading.di.components;

import com.eyck.fxreading.di.modules.MainModule;
import com.eyck.fxreading.di.scopes.UserScope;
import com.eyck.fxreading.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by Eyck on 2017/9/6.
 */

@UserScope
@Component(modules = MainModule.class,dependencies = NetComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
