package com.guestlogix.viewmodel.episode;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.guestlogix.datasource.episode.EpisodeDataSource;
import com.guestlogix.repository.episode.EpisodeRepository;
import com.guestlogix.utils.Constants;
import javax.inject.Inject;

public class EpisodeVMFactory implements ViewModelProvider.Factory {

  private final EpisodeRepository repository;
  private final EpisodeDataSource.Listener listener;

  @Inject
  public EpisodeVMFactory(EpisodeRepository repository, EpisodeDataSource.Listener listener) {
    this.repository = repository;
    this.listener = listener;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(EpisodeViewModel.class)) {
      return (T) new EpisodeViewModel(repository, listener);
    }
    throw new IllegalArgumentException(Constants.UNKNOWN_CLASS);
  }
}