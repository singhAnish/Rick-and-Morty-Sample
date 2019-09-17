package com.guestlogix.datasource.character;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import com.guestlogix.model.character.CharacterSchema;
import com.guestlogix.repository.character.CharacterRepository;
import com.guestlogix.utils.NetworkState;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDataSource extends PageKeyedDataSource<String, CharacterSchema> {

  private final CharacterRepository repository;
  private final MutableLiveData networkState;
  private final MutableLiveData initialLoading;
  private final String characterIds;
  private Listener listener;

  CharacterDataSource(CharacterRepository repository, String characterIds, Listener listener) {
    this.repository = repository;
    this.characterIds = characterIds;
    this.listener = listener;
    networkState = new MutableLiveData<>();
    initialLoading = new MutableLiveData();
  }

  public MutableLiveData getNetworkState() {
    return networkState;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void loadInitial(@NonNull LoadInitialParams<String> params,
      @NonNull LoadInitialCallback<String, CharacterSchema> callback) {
    initialLoading.postValue(NetworkState.LOADING);
    networkState.postValue(NetworkState.LOADING);

    repository.fetchCharacterFromApi(characterIds).enqueue(new Callback<List<CharacterSchema>>() {
      @Override
      public void onResponse(@NotNull Call<List<CharacterSchema>> call,
          @NotNull Response<List<CharacterSchema>> response) {
        listener.onApiSuccess();
        if (response.isSuccessful()) {
          if (response.body() != null) {
            callback.onResult(sortCharacterSchemaForDeadAndAlive(response.body()), "", "");
          }
        }
      }

      @Override
      public void onFailure(@NotNull Call<List<CharacterSchema>> call, @NotNull Throwable t) {
        listener.onApiSuccess();
      }
    });
  }

  private List<CharacterSchema> sortCharacterSchemaForDeadAndAlive(
      List<CharacterSchema> characterSchemas) {
    List<CharacterSchema> characterSchemaForDead = new ArrayList<>();
    List<CharacterSchema> characterSchemaForAlive = new ArrayList<>();
    for (CharacterSchema characterSchema : characterSchemas) {
      if (characterSchema.getStatus().equalsIgnoreCase("dead")) {
        characterSchemaForDead.add(characterSchema);
      } else {
        characterSchemaForAlive.add(characterSchema);
      }
    }
    ArrayList<CharacterSchema> modifiedList = new ArrayList<>(characterSchemaForAlive);
    modifiedList.addAll(characterSchemaForDead);
    return modifiedList;
  }

  @Override public void loadBefore(@NonNull LoadParams<String> params,
      @NonNull LoadCallback<String, CharacterSchema> callback) {

  }

  @SuppressWarnings("unchecked")
  @Override
  public void loadAfter(@NonNull LoadParams<String> params,
      @NonNull LoadCallback<String, CharacterSchema> callback) {

    networkState.postValue(NetworkState.LOADED);
  }

  public interface Listener {
    void onApiSuccess();
  }
}

