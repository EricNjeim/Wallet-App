package com.example.eric_summer2023

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.eric_summer2023.databinding.ActivityRetroBinding

import org.json.JSONException
import org.json.JSONObject
import java.util.Locale


class Retro: AppCompatActivity() {
    // creating variable for recycler view,
    // adapter, array list, progress bar
    private lateinit var binding: ActivityRetroBinding
    lateinit var currencyRV: RecyclerView
    lateinit var searchEdt: EditText
    lateinit var currencyList: ArrayList<Currency>
    lateinit var currencyRVAdapter: CurrencyRVAdapter
     lateinit var loadingPB: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRetroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currencyList = ArrayList<Currency>()
        searchEdt = binding.idEdtCurrency
        loadingPB = binding.idPBLoading
        currencyRV = binding.idRVcurrency

        currencyRVAdapter = CurrencyRVAdapter(currencyList, this)
        currencyRV.layoutManager = LinearLayoutManager(this)
        currencyRV.adapter = currencyRVAdapter
        getData()


        searchEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    private fun filter(filter: String) {
        val filteredlist: ArrayList<Currency> = ArrayList<Currency>()

        for (item in currencyList) {
            if (item.getName().lowercase().contains(filter.lowercase(Locale.getDefault()))) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No currency found..", Toast.LENGTH_SHORT).show()
        } else {

            currencyRVAdapter.filterList(filteredlist)
        }
    }
        private fun getData() {
            val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
            val queue = Volley.newRequestQueue(this)
            // making a json object request to fetch data from API.
            val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener<JSONObject> { response ->
                    loadingPB.visibility = View.GONE
                    try {
                        // extracting data from json.
                        val dataArray = response.getJSONArray("data")
                        for (i in 0 until dataArray.length()) {
                            val dataObj = dataArray.getJSONObject(i)
                            val symbol = dataObj.getString("symbol")
                            val name = dataObj.getString("name")
                            val quote = dataObj.getJSONObject("quote")
                            val USD = quote.getJSONObject("USD")
                            val price = USD.getDouble("price")
                            currencyList.add(Currency(name, symbol, price))
                        }
                        // notifying adapter on data change.
                        currencyRVAdapter.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        // handling json exception.
                        e.printStackTrace()
                        Toast.makeText(
                            this,
                            "Something went wrong. Please try again later",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                Response.ErrorListener { // displaying error response when received any error.
                    Toast.makeText(
                        this,
                        "Something went Wrong. Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String,String>()
                    headers["X-CMC_PRO_API_KEY"] = "fde5bc4f-3b34-4601-80f5-165af70e4791"
                    // at last returning headers
                    return headers
                }
            }

            queue.add(jsonObjectRequest)
        }
}