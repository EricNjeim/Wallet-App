package com.example.eric_summer2023

import android.content.ContentValues
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdapter(val trans:ArrayList<ArrayList<String>>): RecyclerView.Adapter<MyviewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val listitem=layoutInflater.inflate(R.layout.list_item,parent,false)
        return MyviewHolder(listitem)
    }
    override fun getItemCount(): Int {
        return trans.size
    }
    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(trans,position)
    }
}
class MyviewHolder(val view: View): RecyclerView.ViewHolder(view){
    val tag=view.findViewById<TextView>(R.id.TAG)
    val value=view.findViewById<TextView>(R.id.Value)
    val date=view.findViewById<TextView>(R.id.Date)
    fun bind(trans: ArrayList<ArrayList<String>>, pos: Int) {
        if (pos >= 0 && pos < trans.size) {
            val item = trans[pos]
            tag.text = item[0]
            value.text = item[1]
            if(tag.text.toString()=="Deposit"){
                date.text = "+"+item[2]+"$"
            }
          else{
                date.text = "-"+item[2]+"$"
          }
            if(tag.text.toString()=="Withdrawal") {
                tag.setTextColor(ContextCompat.getColor(view.context, R.color.redwithdrawal))
                value.setTextColor(ContextCompat.getColor(view.context, R.color.redwithdrawal))
                date.setTextColor(ContextCompat.getColor(view.context, R.color.redwithdrawal))
            }
            else{
                tag.setTextColor(ContextCompat.getColor(view.context,R.color.greendeposit))
                value.setTextColor(ContextCompat.getColor(view.context,R.color.greendeposit))
                date.setTextColor(ContextCompat.getColor(view.context,R.color.greendeposit))
            }
        }
    }

}