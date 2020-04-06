package com.bigbang.googlebooksfirebase.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bigbang.googlebooksfirebase.R

class AnimationActivity : AppCompatActivity() {
    private lateinit var animationBackButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        animationBackButton = findViewById(R.id.back_button)
        animationBackButton.setOnClickListener(View.OnClickListener { finish() })
    }
}