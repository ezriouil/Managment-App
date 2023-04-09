package www.ezriouil.companymanagment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.companymanagment.R
import www.ezriouil.companymanagment.models.Order

class OrdersRV() : RecyclerView.Adapter<OrdersRV.VHOrders>(){

    private var list = listOf<Order>()

    class VHOrders(itemView: View): RecyclerView.ViewHolder(itemView){
        var items : AppCompatTextView
        var totalItems : AppCompatTextView
        var date: AppCompatTextView

        init {
            items = itemView.findViewById(R.id.itemRVOrdersltem)
            totalItems = itemView.findViewById(R.id.itemRVOrdersTotalItems)
            date = itemView.findViewById(R.id.itemRVOrdersDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHOrders {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_orders,parent,false)
        return VHOrders(myView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VHOrders, position: Int) {
        list[holder.adapterPosition].let {
            holder.items.text =  it.item.toString()
            holder.totalItems.text = "${it.totalItems} DH "
            holder.date.text =it.time
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDate(newList:List<Order>){
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}
