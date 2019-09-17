package com.guestlogix.ui.fragment;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.guestlogix.MyApp;
import com.guestlogix.R;
import com.guestlogix.adapters.episode.EpisodeAdapter;
import com.guestlogix.databinding.EpisodeBinding;
import com.guestlogix.datasource.episode.EpisodeDataSource;
import com.guestlogix.repository.episode.EpisodeRepository;
import com.guestlogix.utils.BaseFragment;
import com.guestlogix.viewmodel.episode.EpisodeVMFactory;
import com.guestlogix.viewmodel.episode.EpisodeViewModel;
import javax.inject.Inject;

public class EpisodeFragment extends BaseFragment implements EpisodeDataSource.Listener {

  @Inject EpisodeRepository repo;

  @Override
  protected int getContentView() {
    return R.layout.fragment_episode;
  }

  @Override
  protected void onFragmentReady(Bundle savedInstanceState) {
    super.onFragmentReady(savedInstanceState);
    init();
  }

  private void init() {
    EpisodeBinding binding = (EpisodeBinding) baseBinding;
    showDialog();

    ((MyApp) mActivity.getApplication()).getComponent().inject(this);
    EpisodeVMFactory factory = new EpisodeVMFactory(repo, this);
    EpisodeViewModel viewModel =
        ViewModelProviders.of(this, factory).get(EpisodeViewModel.class);

    binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
    EpisodeAdapter adapter = new EpisodeAdapter(mActivity, getFragmentManager());
    binding.recyclerView.setAdapter(adapter);

    viewModel.getEpisodeLiveData().observe(this, adapter :: submitList);
    viewModel.getNetworkState().observe(this, adapter :: setNetworkState);
  }

  @Override public void onApiSuccess() {
    dismissDialog();
  }
}