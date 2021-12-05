package com.one1xbet.onlineappof1xbet

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import org.json.JSONException
import org.json.JSONObject

class Utils(val activity: Activity) {
    //private var utmCampaign: String = ""
    //var param = ""
    var turnPwa = ""
    var pwaUrl = ""
    var linkAction = ""
    //private var referralUrl = ""
    var isOpened = false
    var adset : String = ""
    fun getPWAUrl(adsetParam : String) {
        adset = adsetParam
        AndroidNetworking.get(activity.getString(R.string.url)) //.addPathParameter("pageNumber", "0")
            //.addQueryParameter("limit", "3")
            //.addHeaders("token", "1234")
            //.setTag("test")
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(
                object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        println(response)
                        try {
                            val jsonObject = JSONObject(response).optJSONObject("app")
                            turnPwa = jsonObject.optString("turnPwa")
                            pwaUrl = jsonObject.optString("pwaUrl")
                            linkAction = jsonObject.optString("linkAction")
                            if (!turnPwa.equals("off", ignoreCase = true) && isOpened == false) {
                                openUrl(linkAction)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(anError: ANError) {
                        println(anError.message)
                    }
                })
    }

    fun openUrl(action:String) {
        activity.runOnUiThread(Runnable { //tvRefer.setVisibility(View.VISIBLE);
            Log.d("UI thread", "I am the UI thread")
            if (pwaUrl.length == 0) {
                return@Runnable
            }
            Log.e("TESTTTTT", "$pwaUrl$adset")
            if (!turnPwa.equals("off", ignoreCase = true) && isOpened == false) {
                isOpened = true
                Toast.makeText(
                    activity,
                    "$pwaUrl$adset",
                    Toast.LENGTH_LONG
                ).show()
                if (action == "web") {
                    val intent = Intent(activity, BrowserViewActivity::class.java)
                    intent.putExtra("URL", "$pwaUrl$adset")
                    activity.startActivity(intent)
                }else{
                    val winIntent = Intent(Intent.ACTION_VIEW, Uri.parse("$pwaUrl$adset"))
                    winIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    winIntent.setPackage("com.android.chrome")
                    try {
                        activity.startActivity(winIntent)
                    } catch (e: ActivityNotFoundException) {
                        winIntent.setPackage(null)
                        activity.startActivity(winIntent)
                    }
                }
            }
            isOpened = true
        })
    }

}