package www.ezriouil.companymanagment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.companymanagment.R
import www.ezriouil.companymanagment.models.Paiment

class PaimentRV() : RecyclerView.Adapter<PaimentRV.VHPaiment>(){

    private var list = listOf<Paiment>()

    class VHPaiment(itemView: View): RecyclerView.ViewHolder(itemView){
        var montant: AppCompatTextView
        var date: AppCompatTextView

        init {
            montant = itemView.findViewById(R.id.itemRVMontantPaiment)
            date = itemView.findViewById(R.id.itemRVDatePaiment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHPaiment {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_paiment,parent,false)
        return VHPaiment(myView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VHPaiment, position: Int) {
        list[holder.adapterPosition].let {
            holder.montant.text =  "${it.montant} DH "
            holder.date.text =it.time
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDate(newList:List<Paiment>){
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}
