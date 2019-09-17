package com.guestlogix.repository.episode;

import com.guestlogix.model.episode.EpisodeSchema;
import com.guestlogix.rest.RestApi;
import retrofit2.Call;

public class EpisodeRepository {

  private final RestApi api;

  public EpisodeRepository(RestApi restApi) {
    api = restApi;
  }

  public Call<EpisodeSchema> fetchEpisodeFromApi(String page) {
    return api.getEpisode(page);
  }

}
