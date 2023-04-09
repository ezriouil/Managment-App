package www.ezriouil.companymanagment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import www.ezriouil.companymanagment.R

class ScreenAnim : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_anim)
        supportActionBar?.hide()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
            this@ScreenAnim.finish()
        },6500)
    }
}