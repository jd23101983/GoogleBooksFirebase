package com.bigbang.googlebooksfirebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bigbang.googlebooksfirebase.R;

public class AnimationActivity extends AppCompatActivity {

    private Button animationBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        animationBackButton = findViewById(R.id.back_button);
        animationBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { finish(); }
        });
    }
}
