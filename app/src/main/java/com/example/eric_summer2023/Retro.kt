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
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.eric_summer2023.databinding.ActivityRetroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import org.json.JSONException
import org.json.JSONObject
import java.util.Locale
class Retro: AppCompatActivity() {
    private lateinit var binding: ActivityRetroBinding
    lateinit var currencyRV: RecyclerView
    lateinit var searchEdt: EditText
    lateinit var currencyList: ArrayList<Currency>
    lateinit var currencyRVAdapter: CurrencyRVAdapter
    lateinit var loadingPB: ProgressBar
    private lateinit var queue:RequestQueue
    val scope = CoroutineScope(Job() + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRetroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currencyList = ArrayList()
        searchEdt = binding.idEdtCurrency
        loadingPB = binding.idPBLoading
        currencyRV = binding.idRVcurrency
        currencyRVAdapter = CurrencyRVAdapter(currencyList)
        currencyRV.layoutManager = LinearLayoutManager(this)
        currencyRV.adapter = currencyRVAdapter
        scope.launch {
            getData()}

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
    override fun onDestroy() {
        super.onDestroy()
        queue.cancelAll(null)
    }
    private fun getData() {
        val cache = DiskBasedCache(cacheDir, 1024 * 1024)
        val network = BasicNetwork(HurlStack())
        queue = RequestQueue(cache, network).apply {
            start()
        }
        val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                runOnUiThread {
                    // UI-related code should be wrapped in runOnUiThread
                    loadingPB.visibility = View.GONE
                    try {
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
                }
            },
            Response.ErrorListener { // displaying error response when received any error.
                runOnUiThread {
                    // UI-related code should be wrapped in runOnUiThread
                    Toast.makeText(
                        this,
                        "Something went Wrong. Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["X-CMC_PRO_API_KEY"] = "fde5bc4f-3b34-4601-80f5-165af70e4791"
                // https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=fde5bc4f-3b34-4601-80f5-165af70e4791
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }

}
