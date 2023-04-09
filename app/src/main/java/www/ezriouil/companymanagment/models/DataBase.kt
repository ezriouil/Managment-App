package www.ezriouil.companymanagment.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.companymanagment.Constants

class DataBase
{
    private val database = Firebase.database

    fun reloadDataFromFireBase(): LiveData<MutableList<Client>> {
        val mutableLiveData = MutableLiveData<MutableList<Client>>()
        database.getReference(Constants.USERS).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = mutableListOf<Client>()
                for (item in snapshot.children) {
                    val itemOfData = item.getValue(Client::class.java)
                    data.add(itemOfData!!)
                }
                mutableLiveData.value = data
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return mutableLiveData
    }

    fun reloadDataOfPaiment(id:String):LiveData<MutableList<Paiment>>{
        val mutableLiveData = MutableLiveData<MutableList<Paiment>>()
        database.getReference(Constants.PAIMENTS).child(id).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = mutableListOf<Paiment>()
                for (item in snapshot.children){
                    val itemData = item.getValue(Paiment::class.java)
                    data.add(itemData!!)
                }
                mutableLiveData.value = data
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        return mutableLiveData
    }

    fun reloadDataOfOrders(id:String):LiveData<MutableList<Order>>{
        val mutableLiveData = MutableLiveData<MutableList<Order>>()
        database.getReference(Constants.ORDERS).child(id).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = mutableListOf<Order>()
                for (item in snapshot.children){
                    val itemData = item.getValue(Order::class.java)
                    data.add(itemData!!)
                }
                mutableLiveData.value = data
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        return mutableLiveData
    }


}