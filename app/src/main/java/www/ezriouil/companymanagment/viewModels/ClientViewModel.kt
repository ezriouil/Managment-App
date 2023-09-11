package www.ezriouil.companymanagment.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import www.ezriouil.companymanagment.models.Client
import www.ezriouil.companymanagment.models.DataBase
import www.ezriouil.companymanagment.models.Order
import www.ezriouil.companymanagment.models.Paiment

class ClientViewModel : ViewModel() {

    private val dataBase = DataBase()

    fun allData(): LiveData<MutableList<Client>> {
        val mutableLiveData = MutableLiveData<MutableList<Client>>()
        dataBase.reloadDataFromFireBase().observeForever { myData ->
            mutableLiveData.value = myData
        }
        return mutableLiveData
    }

    fun observeData(id: String): LiveData<MutableList<Paiment>> {
        val mutableLiveData = MutableLiveData<MutableList<Paiment>>()
        dataBase.reloadDataOfPaiment(id).observeForever {
            mutableLiveData.value = it
        }
        return mutableLiveData
    }

    fun observeDataOfOrders(id: String): LiveData<MutableList<Order>> {
        val mutableLiveData = MutableLiveData<MutableList<Order>>()
        dataBase.reloadDataOfOrders(id).observeForever {
            mutableLiveData.value = it
        }
        return mutableLiveData
    }

}