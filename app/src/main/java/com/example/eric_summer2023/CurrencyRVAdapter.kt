package com.example.eric_summer2023

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat


// on below line we are creating our adapter class
// in this class we are passing our array list
// and our View Holder class which we have created.
class CurrencyRVAdapter(var currencies: ArrayList<Currency>, context: Context) : RecyclerView.Adapter<CurrencyViewholder>() {
    //private var currencies: ArrayList<Currency>
    //private val context: Context

    //init {
      //  this.currencies= currencies
        //this.context = context
    //}

    // below is the method to filter our list.
    fun filterList(filterlist: ArrayList<Currency>) {
        // adding filtered list to our
        // array list and notifying data set changed
        currencies = filterlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewholder {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        //val view: View = LayoutInflater.from(context).inflate(R.layout.list_item2, parent, false)
            val LayoutInflater= LayoutInflater.from(parent.context)
            val listitem=LayoutInflater.inflate(R.layout.list_item2,parent,false)
        return CurrencyViewholder(listitem)
    }

    override fun onBindViewHolder(holder: CurrencyViewholder, position: Int) {
        // on below line we are setting data to our item of
        // recycler view and all its views.
       val modal: Currency = currencies[position]
        holder.nameTV.setText(modal.getName())
        holder.rateTV.text = "$ " + df2.format(modal.getPrice())
        holder.symbolTV.setText(modal.getSymbol())

    }

    override fun getItemCount(): Int {
        // on below line we are returning
        // the size of our array list.
        return currencies.size
    }



    companion object {
        private val df2 = DecimalFormat("#.##")
    }
}
// on below line we are creating our view holder class
// which will be used to initialize each view of our layout file.
 class CurrencyViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val symbolTV: TextView
     val rateTV: TextView
     val nameTV: TextView

    init {
        // on below line we are initializing all
        // our text views along with  its ids.
        symbolTV = itemView.findViewById(R.id.TAG)
        rateTV = itemView.findViewById(R.id.Date)
        nameTV = itemView.findViewById(R.id.Value)
    }
}