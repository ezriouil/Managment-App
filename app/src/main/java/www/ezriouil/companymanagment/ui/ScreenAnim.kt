package www.ezriouil.companymanagment.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import www.ezriouil.companymanagment.R

class ScreenAnim : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_anim)
        supportActionBar?.hide()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            this@ScreenAnim.finish()
        }, 3000)
    }
}