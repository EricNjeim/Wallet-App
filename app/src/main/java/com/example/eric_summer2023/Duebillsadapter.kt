package com.example.eric_summer2023


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Duebillsadapter(val bills:ArrayList<ArrayList<String>>): RecyclerView.Adapter<MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val listitem=layoutInflater.inflate(R.layout.duebillslayoutfile,parent,false)
        return MyHolder(listitem)
    }
    override fun getItemCount(): Int {
        return bills.size
    }
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(bills,position)
    }
}
class MyHolder(val view: View): RecyclerView.ViewHolder(view){
    val tag=view.findViewById<TextView>(R.id.TAG)
    val value=view.findViewById<TextView>(R.id.Value)
    val date=view.findViewById<TextView>(R.id.Date)
    fun bind(trans: ArrayList<ArrayList<String>>, pos: Int) {
        if (pos >= 0 && pos < trans.size) {
            val item = trans[pos]
            tag.text = item[0]
            value.text = item[1]
            date.text = item[2]

        }
    }

}