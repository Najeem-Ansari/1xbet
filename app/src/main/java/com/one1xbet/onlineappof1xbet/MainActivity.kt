package com.one1xbet.onlineappof1xbet

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    var util = Utils(this)
    private var newsList: ArrayList<NewsData>? = null

    var adset = ""

    override fun onStart() {
        super.onStart()
        val sharedpreferences = getSharedPreferences("adset", Context.MODE_PRIVATE)
        adset = sharedpreferences.getString("adset", adset)!!
        val data = intent.data
        if (data != null) {
            adset = data.toString().split("_".toRegex()).toTypedArray()[1]
        }
        sharedpreferences.edit().putString("adset", adset).apply()
        util.getPWAUrl(adset)

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val extras = intent.extras
        if (extras != null) {
            if (extras.containsKey("title")) {
                val sharedpreferences = getSharedPreferences("adset", Context.MODE_PRIVATE)
                adset = sharedpreferences.getString("adset", adset)!!
                util.getPWAUrl(adset)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        getNewsData()
    }

    private fun getNewsData() {

        AndroidNetworking.get("https://flipnov.com/data/1xbet.json") //.addPathParameter("pageNumber", "0")
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(
                object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        println(response)
                        try {
                            var jsonArray = JSONArray(response)

                            val listType: Type = object : TypeToken<List<NewsData?>?>() {}.type

                            newsList = Gson().fromJson(jsonArray.toString(), listType)
                            rvNewsData.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                            rvNewsData.adapter = NewsListAdapter(newsList!!, this@MainActivity)


                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(anError: ANError) {
                        System.out.println(anError.message)
                    }
                })
    }
}