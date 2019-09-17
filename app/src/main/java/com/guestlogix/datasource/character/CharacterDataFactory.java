package com.guestlogix.datasource.character;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.guestlogix.repository.character.CharacterRepository;

public class CharacterDataFactory extends DataSource.Factory {

  private final MutableLiveData<CharacterDataSource> mutableLiveData;
  private final CharacterRepository repository;
  private final String characterIds;
  private final CharacterDataSource.Listener listener;

  public CharacterDataFactory(CharacterRepository repository, String characterIds, CharacterDataSource.Listener listener) {
    this.repository = repository;
    this.characterIds = characterIds;
    this.listener = listener;
    this.mutableLiveData = new MutableLiveData<>();
  }

  @Override
  public DataSource create() {
    CharacterDataSource dataSource = new CharacterDataSource(repository, characterIds, listener);
    mutableLiveData.postValue(dataSource);
    return dataSource;
  }

  public MutableLiveData<CharacterDataSource> getMutableLiveData() {
    return mutableLiveData;
  }
}
