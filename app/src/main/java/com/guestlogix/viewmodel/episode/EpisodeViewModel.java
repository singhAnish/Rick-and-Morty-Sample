package com.guestlogix.viewmodel.episode;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.guestlogix.datasource.episode.EpisodeDataFactory;
import com.guestlogix.datasource.episode.EpisodeDataSource;
import com.guestlogix.model.episode.Result;
import com.guestlogix.repository.episode.EpisodeRepository;
import com.guestlogix.utils.NetworkState;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EpisodeViewModel extends ViewModel {

  private final EpisodeRepository repository;
  private final EpisodeDataSource.Listener listener;
  private LiveData<NetworkState> networkState;
  private LiveData<PagedList<Result>> liveData;

  EpisodeViewModel(EpisodeRepository repository, EpisodeDataSource.Listener listener) {
    this.repository = repository;
    this.listener = listener;
    init();
  }

  @SuppressWarnings("unchecked")
  private void init() {
    EpisodeDataFactory episodeDataFactory = new EpisodeDataFactory(repository, listener);

    networkState = Transformations.switchMap(episodeDataFactory.getMutableLiveData(),
        (Function<EpisodeDataSource, LiveData<NetworkState>>) EpisodeDataSource::getNetworkState);

    PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
        .setEnablePlaceholders(false).setInitialLoadSizeHint(10).setPageSize(10).build();

    Executor executor = Executors.newFixedThreadPool(5);
    liveData = (new LivePagedListBuilder<>(episodeDataFactory, pagedListConfig))
        .setFetchExecutor(executor).build();
  }

  public LiveData<NetworkState> getNetworkState() {
    return networkState;
  }

  public LiveData<PagedList<Result>> getEpisodeLiveData() {
    return liveData;
  }

}
