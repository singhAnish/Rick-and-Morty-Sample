package com.guestlogix.viewmodel.character;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.guestlogix.datasource.character.CharacterDataFactory;
import com.guestlogix.datasource.character.CharacterDataSource;
import com.guestlogix.model.character.CharacterSchema;
import com.guestlogix.repository.character.CharacterRepository;
import com.guestlogix.utils.NetworkState;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CharacterViewModel extends ViewModel {

  private final CharacterRepository repository;
  private LiveData<NetworkState> networkState;
  private LiveData<PagedList<CharacterSchema>> liveData;
  private final CharacterDataSource.Listener listener;


  CharacterViewModel(CharacterRepository repository, String characterIds, CharacterDataSource.Listener listener) {
    this.repository = repository;
    this.listener = listener;
    init(characterIds);
  }

  @SuppressWarnings("unchecked")
  private void init(String characterIds) {
    CharacterDataFactory dataFactory = new CharacterDataFactory(repository, characterIds, listener);
    networkState = Transformations.switchMap(dataFactory.getMutableLiveData(),
        (Function<CharacterDataSource, LiveData<NetworkState>>) CharacterDataSource :: getNetworkState);

    PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
        .setEnablePlaceholders(false).setInitialLoadSizeHint(10).setPageSize(10).build();

    Executor executor = Executors.newFixedThreadPool(5);
    liveData = (new LivePagedListBuilder<>(dataFactory, pagedListConfig))
        .setFetchExecutor(executor).build();
  }

  public LiveData<NetworkState> getNetworkState() {
    return networkState;
  }

  public LiveData<PagedList<CharacterSchema>> getCharacterLiveData() {
    return liveData;
  }

}
