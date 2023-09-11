package www.ezriouil.companymanagment.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import www.ezriouil.companymanagment.Constants
import www.ezriouil.companymanagment.adapters.PaimentRV
import www.ezriouil.companymanagment.databinding.PaimentHistoryBinding
import www.ezriouil.companymanagment.viewModels.ClientViewModel

class PaimentHistory : AppCompatActivity() {
    private lateinit var binding: PaimentHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PaimentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(Constants.IDTOPAIMENT)
        val clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        clientViewModel.observeData(id!!).observe(this) {
            val paimentRV = PaimentRV()
            binding.recyclerViewPaiment.adapter = paimentRV
            paimentRV.updateDate(it)
        }

    }
}