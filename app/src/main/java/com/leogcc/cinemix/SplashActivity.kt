package com.leogcc.cinemix

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val container = findViewById<View>(R.id.splashContainer)
        container.animate()
            .alpha(1f)
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(900)
            .withEndAction {
                container.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(400)
                    .withEndAction {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }.start()
            }.start()
    }
}