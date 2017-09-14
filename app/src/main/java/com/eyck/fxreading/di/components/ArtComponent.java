package com.eyck.fxreading.di.components;

import com.eyck.fxreading.di.modules.ArtModule;
import com.eyck.fxreading.di.scopes.UserScope;
import com.eyck.fxreading.view.activity.ArtActivity;

import dagger.Component;

/**
 * Created by Eyck on 2017/9/12.
 */

@UserScope
@Component(modules = ArtModule.class,dependencies = NetComponent.class)
public interface ArtComponent {
    void inject(ArtActivity activity);
}
