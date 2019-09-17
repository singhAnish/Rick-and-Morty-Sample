package com.guestlogix.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.guestlogix.MyApp;
import com.guestlogix.R;
import com.guestlogix.adapters.character.CharacterAdapter;
import com.guestlogix.databinding.CharacterBinding;
import com.guestlogix.datasource.character.CharacterDataSource;
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


      new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override public boolean onMove(@NonNull RecyclerView recyclerView,
            @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
          return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
          Snackbar.make(binding.recyclerView, R.string.character_dead, Snackbar.LENGTH_SHORT).show();
          adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
      }).attachToRecyclerView(binding.recyclerView);
    }
  }

  @Override public void onApiSuccess() {
    dismissDialog();
  }
}