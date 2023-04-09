package www.ezriouil.companymanagment.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import www.ezriouil.companymanagment.Constants
import www.ezriouil.companymanagment.adapters.OrdersRV
import www.ezriouil.companymanagment.databinding.OrdersHistoryBinding
import www.ezriouil.companymanagment.viewModels.ClientViewModel

class CommandHistory : AppCompatActivity() {
    private lateinit var binding: OrdersHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrdersHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(Constants.IDTOORDERS)
        val clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        clientViewModel.observeDataOfOrders(id!!).observe(this) {
            val ordersRV = OrdersRV()
            binding.recyclerViewOrders.adapter = ordersRV
            ordersRV.updateDate(it)
        }
    }
}