package com.guestlogix.datasource.episode;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.guestlogix.repository.episode.EpisodeRepository;

public class EpisodeDataFactory extends DataSource.Factory {

    private final MutableLiveData<EpisodeDataSource> mutableLiveData;
    private final EpisodeRepository repository;
    private final EpisodeDataSource.Listener listener;


    public EpisodeDataFactory(EpisodeRepository repository, EpisodeDataSource.Listener listener) {
        this.repository = repository;
        this.listener = listener;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        EpisodeDataSource dataSource = new EpisodeDataSource(repository, listener);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<EpisodeDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
