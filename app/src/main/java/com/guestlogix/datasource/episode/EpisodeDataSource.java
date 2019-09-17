package com.guestlogix.datasource.episode;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.guestlogix.model.episode.EpisodeSchema;
import com.guestlogix.model.episode.Result;
import com.guestlogix.repository.episode.EpisodeRepository;
import com.guestlogix.utils.Constants;
import com.guestlogix.utils.NetworkState;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeDataSource extends PageKeyedDataSource<String, Result> {

  private final EpisodeRepository repository;
  private final MutableLiveData networkState;
  private final MutableLiveData initialLoading;
  private boolean noMoreDataBool = false;
  private final Listener listener;

  EpisodeDataSource(EpisodeRepository repository, Listener listener) {
    this.repository = repository;
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
      @NonNull LoadInitialCallback<String, Result> callback) {
    initialLoading.postValue(NetworkState.LOADING);
    networkState.postValue(NetworkState.LOADING);

    repository.fetchEpisodeFromApi("").enqueue(new Callback<EpisodeSchema>() {
      @Override
      public void onResponse(@NotNull Call<EpisodeSchema> call,
          @NotNull Response<EpisodeSchema> response) {
        listener.onApiSuccess();
        if (response.isSuccessful()) {
          if (response.body() != null) {
            EpisodeSchema schema = response.body();
            noMoreDataBool = schema.getInfo().getNext().trim().isEmpty();
            callback.onResult(schema.getResults(), "", String.valueOf(schema.getInfo().getPages()));
            initialLoading.postValue(NetworkState.LOADED);
            networkState.postValue(NetworkState.LOADED);
          } else {
            networkState.postValue(
                new NetworkState(NetworkState.Status.FAILED, Constants.SOMETHING_WRONG));
          }
        } else {
          initialLoading.postValue(
              new NetworkState(NetworkState.Status.FAILED, response.message()));
          networkState.postValue(
              new NetworkState(NetworkState.Status.FAILED, response.message()));
        }
      }

      @Override
      public void onFailure(@NotNull Call<EpisodeSchema> call, @NotNull Throwable t) {
        listener.onApiSuccess();
        String errorMessage = t.getMessage();
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
      }
    });
  }

  @Override public void loadBefore(@NonNull LoadParams<String> params,
      @NonNull LoadCallback<String, Result> callback) {

  }

  @SuppressWarnings("unchecked")
  @Override
  public void loadAfter(@NonNull LoadParams<String> params,
      @NonNull LoadCallback<String, Result> callback) {

    if (!noMoreDataBool) {
      networkState.postValue(NetworkState.LOADING);
      repository.fetchEpisodeFromApi(params.key).enqueue(new Callback<EpisodeSchema>() {
        @SuppressWarnings("unchecked")
        @Override
        public void onResponse(@NotNull Call<EpisodeSchema> call,
            @NotNull Response<EpisodeSchema> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              EpisodeSchema schema = response.body();
              noMoreDataBool = schema.getInfo().getNext().trim().isEmpty();
              callback.onResult(schema.getResults(), String.valueOf(schema.getInfo().getPages()));
              networkState.postValue(NetworkState.LOADED);
            } else {
              networkState.postValue(
                  new NetworkState(NetworkState.Status.FAILED, Constants.SOMETHING_WRONG));
            }
          } else {
            networkState.postValue(
                new NetworkState(NetworkState.Status.FAILED, response.message()));
          }
        }

        @Override
        public void onFailure(@NotNull Call<EpisodeSchema> call, @NotNull Throwable t) {
          String errorMessage = t.getMessage();
          networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
        }
      });
    }
  }


  public interface Listener {
    void onApiSuccess();
  }
}
