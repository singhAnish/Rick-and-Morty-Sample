package com.guestlogix.di.module;

import com.guestlogix.repository.character.CharacterRepository;
import com.guestlogix.rest.RestApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class CharacterModule {

  @Provides
  @Singleton
  CharacterRepository getRepository(RestApi api) {
    return new CharacterRepository(api);
  }
}
