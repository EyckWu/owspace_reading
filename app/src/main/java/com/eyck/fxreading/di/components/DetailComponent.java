package com.eyck.fxreading.di.components;

import com.eyck.fxreading.di.modules.DetailModule;
import com.eyck.fxreading.di.scopes.UserScope;
import com.eyck.fxreading.view.activity.DetailActivity;
import com.eyck.fxreading.view.activity.VideoDetailActivity;
import com.eyck.fxreading.view.activity.VoiceDetailActivity;

import dagger.Component;

/**
 * Created by Eyck on 2017/9/12.
 */

@UserScope
@Component(modules = DetailModule.class,dependencies = NetComponent.class)
public interface DetailComponent {
    void inject(DetailActivity activity);
    void inject(VideoDetailActivity activity);
    void inject(VoiceDetailActivity activity);
}
