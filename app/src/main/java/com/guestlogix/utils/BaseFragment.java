package com.guestlogix.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import com.guestlogix.R;

public abstract class BaseFragment extends Fragment {

  private Dialog mDialog;
  protected ViewDataBinding baseBinding;
  private View mRootView;
  protected Activity mActivity;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    mActivity = activity;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (mRootView != null) {
      return mRootView;
    }
    baseBinding = DataBindingUtil.inflate(inflater, getContentView(), container, false);
    onFragmentReady(savedInstanceState);
    mRootView = baseBinding.getRoot();
    return mRootView;
  }

  @CallSuper
  protected void onFragmentReady(Bundle savedInstanceState) {
    //This should be implemented by child class
  }

  protected abstract int getContentView();

  protected void showDialog() {
    if (mDialog == null) {
      mDialog = showProgressDialog(mActivity);
    }
    mDialog.show();
  }

  protected void dismissDialog() {
    if (mDialog != null && mDialog.isShowing()) {
      mDialog.dismiss();
    }
  }

  private Dialog showProgressDialog(Context context) {
    Dialog dialog = new Dialog(context);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.view_progress);
    dialog.setCanceledOnTouchOutside(false);
    if (dialog.getWindow() != null) {
      dialog.getWindow().getDecorView().setBackgroundResource(R.color.transparent);
      dialog.getWindow().setDimAmount(0.3f);
    }
    dialog.setCancelable(true);
    return dialog;
  }
}
