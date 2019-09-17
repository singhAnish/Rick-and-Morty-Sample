package com.guestlogix.adapters.character;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guestlogix.R;
import com.guestlogix.databinding.CharacterItemBinding;
import com.guestlogix.databinding.NetworkItemBinding;
import com.guestlogix.model.character.CharacterSchema;
import com.guestlogix.ui.fragment.CharacterDetailFragment;
import com.guestlogix.utils.Constants;
import com.guestlogix.utils.NetworkState;
import java.util.Objects;

public class CharacterAdapter extends PagedListAdapter<CharacterSchema, RecyclerView.ViewHolder> {

  private static final int TYPE_PROGRESS = 0;
  private static final int TYPE_ITEM = 1;
  private NetworkState networkState;
  private final RequestOptions options;
  private final FragmentManager manager;

  public CharacterAdapter(FragmentManager manager) {
    super(CharacterSchema.DIFF_CALLBACK);
    options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher);
    this.manager = manager;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    if (viewType == TYPE_PROGRESS) {
      NetworkItemBinding headerBinding = NetworkItemBinding.inflate(layoutInflater, parent, false);
      return new NetworkStateItemViewHolder(headerBinding);
    } else {
      CharacterItemBinding itemBinding =
          CharacterItemBinding.inflate(layoutInflater, parent, false);
      return new ItemViewHolder(itemBinding);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ItemViewHolder) {
      ((ItemViewHolder) holder).bindTo(Objects.requireNonNull(getItem(position)));
    } else {
      ((NetworkStateItemViewHolder) holder).bindView(networkState);
    }
  }

  private boolean hasExtraRow() {
    return networkState != null && networkState != NetworkState.LOADED;
  }

  @Override
  public int getItemViewType(int position) {
    if (hasExtraRow() && position == getItemCount() - 1) {
      return TYPE_PROGRESS;
    } else {
      return TYPE_ITEM;
    }
  }

  public void setNetworkState(NetworkState newNetworkState) {
    NetworkState previousState = this.networkState;
    boolean previousExtraRow = hasExtraRow();
    this.networkState = newNetworkState;
    boolean newExtraRow = hasExtraRow();
    if (previousExtraRow != newExtraRow) {
      if (previousExtraRow) {
        notifyItemRemoved(getItemCount());
      } else {
        notifyItemInserted(getItemCount());
      }
    } else if (newExtraRow && previousState != newNetworkState) {
      notifyItemChanged(getItemCount() - 1);
    }
  }

  private class ItemViewHolder extends RecyclerView.ViewHolder {
    private final CharacterItemBinding binding;

    private ItemViewHolder(CharacterItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bindTo(CharacterSchema character) {
      if (character != null) {
        binding.characterName.setText(character.getName());
        binding.characterStatus.setText(character.getStatus());
        Glide.with(binding.characterImage).load(character.getImage())
            .apply(options).thumbnail(0.1f).into(binding.characterImage);

        binding.characterMainLay.setOnClickListener(v -> {
          if (manager != null) {
            Fragment fragment = new CharacterDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.CHARACTER_DATA, character);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_right, R.animator.slide_out_left);
            transaction.replace(R.id.frameLayout, fragment, "CharacterDetailFragment");
            transaction.addToBackStack("CharacterDetailFragment");
            transaction.commitAllowingStateLoss();
          }
        });
      }
    }
  }

  private class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {
    private final NetworkItemBinding binding;

    private NetworkStateItemViewHolder(NetworkItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bindView(NetworkState networkState) {
      if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
        binding.progressBar.setVisibility(View.VISIBLE);
      } else {
        binding.progressBar.setVisibility(View.GONE);
      }

      if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
        binding.errorMsg.setText(networkState.getMsg());
      } else {
        binding.errorMsg.setText(Constants.LOADING);
      }
    }
  }
}
