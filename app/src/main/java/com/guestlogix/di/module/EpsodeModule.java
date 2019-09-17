package com.guestlogix.di.module;

import com.guestlogix.repository.episode.EpisodeRepository;
import com.guestlogix.rest.RestApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class EpsodeModule {

  @Provides
  @Singleton
  EpisodeRepository getRepository(RestApi api) {
    return new EpisodeRepository(api);
  }
}
