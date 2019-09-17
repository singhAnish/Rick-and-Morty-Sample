package com.guestlogix.viewmodel.character;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.guestlogix.datasource.character.CharacterDataSource;
import com.guestlogix.repository.character.CharacterRepository;
import com.guestlogix.utils.Constants;
import javax.inject.Inject;

public class CharacterVMFactory implements ViewModelProvider.Factory {

  private final CharacterRepository repository;
  private final String characterIds;
  private final CharacterDataSource.Listener listener;

  @Inject
  public CharacterVMFactory(CharacterRepository repository, String characterIds, CharacterDataSource.Listener listener) {
    this.repository = repository;
    this.characterIds = characterIds;
    this.listener = listener;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(CharacterViewModel.class)) {
      return (T) new CharacterViewModel(repository, characterIds, listener);
    }
    throw new IllegalArgumentException(Constants.UNKNOWN_CLASS);
  }
}