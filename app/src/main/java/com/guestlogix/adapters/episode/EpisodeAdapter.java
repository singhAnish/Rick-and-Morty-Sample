package com.guestlogix.adapters.episode;

import android.content.Context;
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
import com.guestlogix.BuildConfig;
import com.guestlogix.R;
import com.guestlogix.databinding.EpisodeItemBinding;
import com.guestlogix.databinding.NetworkItemBinding;
import com.guestlogix.model.episode.Result;
import com.guestlogix.ui.fragment.CharacterFragment;
import com.guestlogix.utils.Constants;
import com.guestlogix.utils.NetworkState;
import java.util.Objects;

public class EpisodeAdapter extends PagedListAdapter<Result, RecyclerView.ViewHolder> {

  private static final int TYPE_PROGRESS = 0;
  private static final int TYPE_ITEM = 1;
  private Context context;
  private NetworkState networkState;
  private final FragmentManager manager;

  public EpisodeAdapter(Context context, FragmentManager manager) {
    super(Result.DIFF_CALLBACK);
    this.context = context;
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
      EpisodeItemBinding itemBinding = EpisodeItemBinding.inflate(layoutInflater, parent, false);
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
    private final EpisodeItemBinding binding;

    private ItemViewHolder(EpisodeItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bindTo(Result result) {
      if (result != null) {
        binding.name.setText(result.getName());
        binding.episode.setText(result.getEpisode());
        binding.time.setText(context.getResources().getString(R.string.release_date)
            .concat(" ").concat(result.getCreated().substring(0, 10)));

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < result.getCharacters().size() - 1; i++) {
          String id = result.getCharacters().get(i)
              .replace(BuildConfig.BASE_URL.concat("character/"), "");
          if (i == result.getCharacters().size() - 1) {
            builder.append(id);
          } else {
            builder.append(id).append(",");
          }
        }
        binding.episodeMainLay.setOnClickListener(v -> {
          if (manager != null) {
            Fragment fragment = new CharacterFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.CHARACTER_ID, builder.toString());
            fragment.setArguments(bundle);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,
                R.animator.slide_in_right, R.animator.slide_out_left);
            transaction.replace(R.id.frameLayout, fragment, "CharacterFragment");
            transaction.addToBackStack("CharacterFragment");
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
