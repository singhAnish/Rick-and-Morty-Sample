package com.guestlogix.utils;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import com.guestlogix.R;
import com.guestlogix.databinding.ActivityBaseBinding;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_base);

        getLayoutInflater().inflate(getContentView(), binding.baseFrameLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setNavigationOnClickListener( v -> {
            onBackPressed();
        });

        onViewReady(savedInstanceState, getIntent());
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent){
        //This should be implemented by child class
    }

    protected abstract int getContentView();

}
