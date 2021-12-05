package  com.one1xbet.onlineappof1xbet

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_news.view.*


class NewsListAdapter(val items : ArrayList<NewsData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_news, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = items[position].title
     //   holder.tvDesc.text = items[position].desc
        Glide
            .with(context)
            .load("https://flipnov.com/data/"  + items[position].image)
            .into(holder.ivImage)

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, NewsDetailActivity::class.java).putExtra("data", items[position]))
        }

    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val tvTitle = view.title
    val tvDesc = view.desc
    val ivImage = view.ivNews
}