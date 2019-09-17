package com.guestlogix;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import com.guestlogix.di.component.AppComponent;
import com.guestlogix.di.component.DaggerAppComponent;
import com.guestlogix.di.module.app.ContextModule;
import com.guestlogix.utils.ConnectionDetector;
import timber.log.Timber;

public class MyApp extends Application {

  private AppComponent component;

  public static MyApp get(Activity activity) {
    return (MyApp) activity.getApplication();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    this.registerReceiver(ConnectionDetector.getDetector().new NetworkReceiver(), new IntentFilter(
        ConnectivityManager.CONNECTIVITY_ACTION));
    ConnectionDetector.getDetector().initConnectionState(this);

    Timber.plant(new Timber.DebugTree());
    component = DaggerAppComponent.builder().contextModule(new ContextModule(this)).build();
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    component = null;
  }

  public AppComponent getComponent() {
    return component;
  }

  private static MyApp get(Context context) {
    return (MyApp) context.getApplicationContext();
  }

  public static MyApp create(Context context) {
    return MyApp.get(context);
  }
}
