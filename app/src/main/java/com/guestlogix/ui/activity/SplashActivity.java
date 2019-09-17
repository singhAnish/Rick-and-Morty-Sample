package com.guestlogix.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.guestlogix.R;
import com.guestlogix.utils.ConnectionDetector;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    final Handler handler = new Handler();
    handler.postDelayed(this :: init, 2000);
  }

  private void init() {
    if (ConnectionDetector.isConnected()) {
      startActivity(new Intent(SplashActivity.this, MainActivity.class));
      finish();
    } else {
      Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(),
          R.string.noInternet, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, v -> init());
      snackbar.setActionTextColor(Color.RED);
      View sbView = snackbar.getView();
      TextView textView = sbView.findViewById(R.id.snackbar_text);
      textView.setTextColor(Color.WHITE);
      snackbar.show();
    }
  }
}
