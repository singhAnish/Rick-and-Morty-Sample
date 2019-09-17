package com.guestlogix.rest;

import com.guestlogix.model.character.CharacterSchema;
import com.guestlogix.model.episode.EpisodeSchema;
import com.guestlogix.utils.Constants;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

  //https://rickandmortyapi.com/api/character/1,2,35
  @GET(Constants.EPISODE)
  Call<EpisodeSchema> getEpisode(@Query(Constants.PAGE) String page);

  //https://rickandmortyapi.com/api/episode/?page=1
  @GET(Constants.CHARACTER + "/{characterIds}")
  Call<List<CharacterSchema>> getCharacter(@Path("characterIds") String characterIds);
}
