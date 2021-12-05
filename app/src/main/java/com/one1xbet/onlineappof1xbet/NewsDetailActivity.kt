package com.one1xbet.onlineappof1xbet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_ozwin_news_detail.*

class NewsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ozwin_news_detail)
        setData()
    }

    private fun setData() {
        var data = intent.getSerializableExtra("data") as NewsData

        tvTitle.text = data.title
        desc.text = data.desc

        Glide
            .with(this)
            .load("https://flipnov.com/data/"  +data.image)
            .into(image)
    }
}