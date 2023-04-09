package www.ezriouil.companymanagment.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.companymanagment.databinding.AddNewBinding
import www.ezriouil.companymanagment.models.Client

class AddNew : AppCompatActivity() {
    private lateinit var binding:AddNewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myRef = Firebase.database.getReference("USERS")
        val userName = binding.editTextClientUserName
        val price = binding.editTextClientPrice
        val phone = binding.editTextClientTel
        binding.buttonClientBtnAjouter.setOnClickListener {
            if (userName.text.toString().isNotBlank() && price.text.toString().isNotBlank() && phone.text.toString().isNotBlank()){
                val key = userName.text.toString()+" || "+phone.text.toString()
                val companyInfo = Client(key, userName.text.toString() , price.text.toString().toDouble() , 0.00 , phone.text.toString())
                myRef.child(key).setValue(companyInfo).addOnSuccessListener {
                    Toast.makeText(this,"Le client à étè ajouter", Toast.LENGTH_LONG).show()
                    clear()
                }
            }
            else Toast.makeText(this,"Empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun clear() {
        binding.editTextClientUserName.text?.clear()
        binding.editTextClientPrice.text?.clear()
        binding.editTextClientTel.text?.clear()
    }

}