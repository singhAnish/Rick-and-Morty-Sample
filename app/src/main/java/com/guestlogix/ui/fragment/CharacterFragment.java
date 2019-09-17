package com.guestlogix.ui.fragment;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.guestlogix.MyApp;
import com.guestlogix.R;
import com.guestlogix.adapters.character.CharacterAdapter;
import com.guestlogix.databinding.CharacterBinding;
import com.guestlogix.datasource.character.CharacterDataSource;
import com.guestlogix.model.character.CharacterSchema;
import com.guestlogix.repository.character.CharacterRepository;
import com.guestlogix.utils.BaseFragment;
import com.guestlogix.utils.Constants;
import com.guestlogix.viewmodel.character.CharacterVMFactory;
import com.guestlogix.viewmodel.character.CharacterViewModel;
import javax.inject.Inject;

public class CharacterFragment extends BaseFragment implements CharacterDataSource.Listener {

  @Inject CharacterRepository repo;

  @Override
  protected int getContentView() {
    return R.layout.fragment_character;
  }

  @Override
  protected void onFragmentReady(Bundle savedInstanceState) {
    super.onFragmentReady(savedInstanceState);
    init();
  }

  private void init() {
    CharacterBinding binding = (CharacterBinding) baseBinding;
    ((MyApp) mActivity.getApplication()).getComponent().inject(this);
    showDialog();

    if(getArguments() != null){
      CharacterVMFactory factory = new CharacterVMFactory(repo, getArguments().getString(Constants.CHARACTER_ID), this);
      CharacterViewModel viewModel =
          ViewModelProviders.of(this, factory).get(CharacterViewModel.class);

      binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
      CharacterAdapter adapter = new CharacterAdapter(getFragmentManager());
      binding.recyclerView.setAdapter(adapter);
      viewModel.getCharacterLiveData().observe(this, adapter :: submitList);
      viewModel.getNetworkState().observe(this, adapter :: setNetworkState);
    }
  }

  @Override public void onApiSuccess() {
    dismissDialog();
  }
}