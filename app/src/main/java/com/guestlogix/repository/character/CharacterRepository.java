package com.guestlogix.repository.character;

import com.guestlogix.model.character.CharacterSchema;
import com.guestlogix.rest.RestApi;
import java.util.List;
import retrofit2.Call;

public class CharacterRepository {

  private final RestApi api;

  public CharacterRepository(RestApi restApi) {
    api = restApi;
  }

  public Call<List<CharacterSchema>> fetchCharacterFromApi(String chartacters) {
    return api.getCharacter(chartacters);
  }
}
