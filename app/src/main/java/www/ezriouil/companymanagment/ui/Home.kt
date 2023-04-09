package www.ezriouil.companymanagment.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.companymanagment.Constants
import www.ezriouil.companymanagment.R
import www.ezriouil.companymanagment.adapters.ClientRV
import www.ezriouil.companymanagment.adapters.Listener
import www.ezriouil.companymanagment.databinding.HomeBinding
import www.ezriouil.companymanagment.models.Client
import www.ezriouil.companymanagment.models.Order
import www.ezriouil.companymanagment.models.Paiment
import www.ezriouil.companymanagment.ui.activities.AddNew
import www.ezriouil.companymanagment.ui.activities.CommandHistory
import www.ezriouil.companymanagment.ui.activities.PaimentHistory
import www.ezriouil.companymanagment.viewModels.ClientViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class Home : AppCompatActivity() , Listener{
    private lateinit var clientRV:ClientRV
    private lateinit var binding: HomeBinding
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = HomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        shimmerEffect()
        observeData()
        binding.homeBtnAddNew.setOnClickListener {
            startActivity(Intent(this,AddNew::class.java))
        }
    }

    private fun shimmerEffect() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.homeRV.visibility = View.VISIBLE
        }, 5000)
    }

    private fun observeData(){
        val clientViewModel = ViewModelProvider(this)[ClientViewModel::class.java]
        clientViewModel.allData().observe(this){ listOfData ->
            clientRV = ClientRV(this,this)
            binding.homeRV.adapter = clientRV
            clientRV.updateDate(listOfData)

            binding.chipAll.setOnClickListener {
                clientRV.updateDate(listOfData)
                search(listOfData)
            }
            binding.chip10.setOnClickListener {
                val data = listOfData.filter { it.price!! > 10 }
                clientRV.updateDate(data)
                search(data)
            }
            binding.chip20.setOnClickListener {
                val data = listOfData.filter { it.price!! > 20 }
                clientRV.updateDate(data)
                search(data)
            }
            binding.chip30.setOnClickListener {
                val data = listOfData.filter { it.price!! > 30 }
                clientRV.updateDate(data)
                search(data)
            }



        }
    }

    private fun search(listOfData: List<Client>) {
        binding.mySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val newListOfClient = mutableListOf<Client>()
                for (item in listOfData) if (item.name!!.lowercase()
                        .contains(newText!!.lowercase())
                ) newListOfClient.add(item)
                if (newListOfClient.isEmpty()) Toast.makeText(
                    this@Home,
                    "empty",
                    Toast.LENGTH_SHORT
                ).show()
                else clientRV.updateDate(newListOfClient)
                return true
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    override fun sendOrder(client : Client) {
        val firebase = Firebase.database
        val firebaseDatabase  = firebase.getReference(Constants.ORDERS).child(client.id!!).push()
        val alertDialog = AlertDialog.Builder(this,R.style.bottomSheetStyle).create()
        val view = layoutInflater.inflate(R.layout.alert_order,null)
        val command = view.findViewById<AppCompatEditText>(R.id.editTextSizeOfCommand)
        val btnCommand = view.findViewById<Button>(R.id.btnCommand)
        val btnShowHistoryOfCommand = view.findViewById<Button>(R.id.btnShowHistoryOfCommand)
        btnCommand.setOnClickListener {
            if (command.text!!.isNotBlank()){
                val time = "${LocalDate.now().year}/${LocalDate.now().monthValue}/${LocalDate.now().dayOfMonth} ${LocalDateTime.now().hour+1}:${LocalDateTime.now().minute}:${LocalDateTime.now().second}"
                val totalOfItems = command.text.toString().toInt() * client.price!!
                val order = Order(firebaseDatabase.key!!,command.text.toString().toInt(),totalOfItems,time)
                firebaseDatabase.setValue(order).addOnSuccessListener {
                    val newTotal = client.total!! + totalOfItems
                    val updateClient = Client(client.id!!,client.name!!,client.price!!,newTotal,client.phone!!)
                    firebase.getReference(Constants.USERS).child(client.id!!).setValue(updateClient)
                    Toast.makeText(this,"Command à étè confirmer", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                }
            } else Toast.makeText(this,"Empty", Toast.LENGTH_SHORT).show()
        }
        btnShowHistoryOfCommand.setOnClickListener {
            val intent = Intent(this, CommandHistory::class.java)
            intent.putExtra(Constants.IDTOORDERS,client.id)
            startActivity(intent)
        }
        alertDialog.setView(view)
        alertDialog.show()
    }

    @SuppressLint("MissingInflatedId")
    override fun paidOrder(client: Client) {
        val firebase = Firebase.database
        val firebaseDatabase  = firebase.getReference(Constants.PAIMENTS).child(client.id!!).push()
        val alertDialog = AlertDialog.Builder(this,R.style.bottomSheetStyle).create()
        val view = layoutInflater.inflate(R.layout.alert_paid,null)
        val editTextpaiment = view.findViewById<AppCompatEditText>(R.id.editTextPaiment)
        val btnPaiment = view.findViewById<Button>(R.id.btnPaiment)
        val btnShowHistoryOfCommand = view.findViewById<Button>(R.id.btnShowHistoryOfPaiment)
        btnPaiment.setOnClickListener {
            if (editTextpaiment.text!!.isNotBlank()){
                val newTotal = client.total!! - editTextpaiment.text.toString().toDouble()
                if (newTotal > 0){
                    val time = "${LocalDate.now().year}/${LocalDate.now().monthValue}/${LocalDate.now().dayOfMonth} ${LocalDateTime.now().hour+1}:${LocalDateTime.now().minute}:${LocalDateTime.now().second}"
                    val paiment = Paiment(firebaseDatabase.key.toString() , editTextpaiment.text.toString().toDouble() , time)
                    firebaseDatabase.setValue(paiment).addOnSuccessListener {
                        val updateClient = Client(client.id!!,client.name!!,client.price!!,newTotal,client.phone!!)
                        firebase.getReference(Constants.USERS).child(client.id!!).setValue(updateClient)
                        Toast.makeText(this,"Paiment à étè bien", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                    }
                } else Toast.makeText(this, "SVP Vérifier votre montant", Toast.LENGTH_SHORT).show()

            } else Toast.makeText(this,"Empty", Toast.LENGTH_SHORT).show()
        }
        btnShowHistoryOfCommand.setOnClickListener {
            val intent = Intent(this, PaimentHistory::class.java)
            intent.putExtra(Constants.IDTOPAIMENT,client.id)
            startActivity(intent)
        }
        alertDialog.setView(view)
        alertDialog.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this@Home)
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("Exit")
        alertDialog.setPositiveButton("Oui"){ _ , _ -> this.finish() }
        alertDialog.setNegativeButton("Non"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }

}