package com.guestlogix.ui.fragment;

import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guestlogix.R;
import com.guestlogix.databinding.CharacterDetailBinding;
import com.guestlogix.model.character.CharacterSchema;
import com.guestlogix.utils.BaseFragment;
import com.guestlogix.utils.Constants;

public class CharacterDetailFragment extends BaseFragment {


  @Override
  protected int getContentView() {
    return R.layout.fragment_character_detail;
  }

  @Override
  protected void onFragmentReady(Bundle savedInstanceState) {
    super.onFragmentReady(savedInstanceState);
    init();
  }

  private void init() {
    CharacterDetailBinding binding = (CharacterDetailBinding) baseBinding;
    RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher);
    if(getArguments() != null) {
      final CharacterSchema data = getArguments().getParcelable(Constants.CHARACTER_DATA);

      if (data != null) {
        binding.name.setText(data.getName());
        binding.time.setText(String.valueOf(data.getId()).concat(" : ").concat(data.getCreated()));
        Glide.with(binding.characterImage).load(data.getImage())
            .apply(options).thumbnail(0.1f).into(binding.characterImage);

        binding.status.setText(data.getStatus());
        binding.species.setText(data.getSpecies());
        binding.gender.setText(data.getGender());
        binding.origin.setText(data.getOrigin().getName());
        binding.lastLocation.setText(data.getLocation().getName());
      }
    }
  }


}