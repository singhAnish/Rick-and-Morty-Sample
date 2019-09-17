package com.guestlogix.di.component;

import com.guestlogix.di.module.CharacterModule;
import com.guestlogix.di.module.EpsodeModule;
import com.guestlogix.di.module.app.AppModule;
import com.guestlogix.ui.fragment.CharacterFragment;
import com.guestlogix.ui.fragment.EpisodeFragment;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { AppModule.class, EpsodeModule.class, CharacterModule.class })
public interface AppComponent {

  void inject(EpisodeFragment fragment);

  void inject(CharacterFragment fragment);

}
