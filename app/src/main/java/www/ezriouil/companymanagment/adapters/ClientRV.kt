package www.ezriouil.companymanagment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.companymanagment.R
import www.ezriouil.companymanagment.databinding.ItemRvClientBinding
import www.ezriouil.companymanagment.models.Client

class ClientRV(context: Context,private val listener: Listener) : RecyclerView.Adapter<ClientRV.VHClient>(){

    private lateinit var list:List<Client>

    class VHClient(val binding: ItemRvClientBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHClient {
        val myView = ItemRvClientBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VHClient(myView)
    }
    private val newCommand = loadAnimation(context,R.anim.new_command)
    private val paid = loadAnimation(context,R.anim.paid)
    private val total = loadAnimation(context,R.anim.total)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VHClient, position: Int) {
        list[holder.adapterPosition].let {
            holder.binding.itemRVUserName.text = "   - Nom Et Prénom : ${it.name}"
            holder.binding.itemRVPrice.text = "   - Prix : ${it.price}"
            holder.binding.itemRVPhone.text = "   - Tel : ${it.phone}"
            holder.binding.itemRVTotal.text = "TOTALE\n${it.total.toString()} DH"
        }
        holder.binding.myCardView.setOnClickListener {
            if (holder.binding.itemRVNewOrder.visibility == View.GONE && holder.binding.itemRVPaid.visibility == View.GONE && holder.binding.itemRVTotal.visibility == View.GONE){
                holder.binding.itemRVNewOrder.startAnimation(newCommand)
                holder.binding.itemRVPaid.startAnimation(paid)
                holder.binding.itemRVTotal.startAnimation(total)
                holder.binding.itemRVNewOrder.visibility =View.VISIBLE
                holder.binding.itemRVPaid.visibility =View.VISIBLE
                holder.binding.itemRVTotal.visibility = View.VISIBLE
            } else {
                holder.binding.itemRVNewOrder.visibility =View.GONE
                holder.binding.itemRVPaid.visibility =View.GONE
                holder.binding.itemRVTotal.visibility = View.GONE
            }

        }
        holder.binding.itemRVNewOrder.setOnClickListener {
            listener.sendOrder(list[holder.adapterPosition])
        }
        holder.binding.itemRVPaid.setOnClickListener { listener.paidOrder(list[holder.adapterPosition]) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDate(newList:List<Client>){
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}

interface Listener
{
    fun sendOrder(client: Client)
    fun paidOrder(client: Client)
}