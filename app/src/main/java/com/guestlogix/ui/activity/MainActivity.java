package com.guestlogix.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.guestlogix.R;
import com.guestlogix.ui.fragment.EpisodeFragment;
import com.guestlogix.utils.BaseActivity;

public class MainActivity extends BaseActivity
    implements FragmentManager.OnBackStackChangedListener {

  private FragmentManager manager;
  private int count;

  @Override
  protected int getContentView() {
    return R.layout.frame_layout;
  }

  @Override
  protected void onViewReady(Bundle savedInstanceState, Intent intent) {
    super.onViewReady(savedInstanceState, intent);
    init();
  }

  private void init() {
    manager = getSupportFragmentManager();
    manager.addOnBackStackChangedListener(this);

    FragmentTransaction transaction = manager.beginTransaction();
    Fragment newFragment = new EpisodeFragment();
    transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,
        R.animator.slide_in_right, R.animator.slide_out_left);
    transaction.replace(R.id.frameLayout, newFragment, "EpisodeFragment");
    transaction.addToBackStack("EpisodeFragment");

    transaction.commit();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FrameLayout frame = findViewById(R.id.baseFrameLayout);
    getLayoutInflater().inflate(R.layout.frame_layout, frame);
  }

  @Override
  public void onBackStackChanged() {
    count = manager.getBackStackEntryCount();
  }

  @Override
  public void onBackPressed() {
    if (count == 1) {
      finish();
    } else {
      super.onBackPressed();
    }
  }
}