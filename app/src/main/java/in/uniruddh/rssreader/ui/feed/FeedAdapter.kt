package `in`.uniruddh.rssreader.ui.feed

import `in`.uniruddh.rssreader.data.local.entity.Feed
import `in`.uniruddh.rssreader.databinding.FeedListItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: abhilvare@tetravx.com
 */
class FeedAdapter() :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    private var feedList: ArrayList<Feed> = arrayListOf()

    fun refreshList(list: ArrayList<Feed>) {
        this.feedList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeedListItemBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = feedList[position]
        holder.binding.title.text = feed.feedTitle
        holder.binding.url.text = feed.feedUrl
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    inner class FeedViewHolder(val binding: FeedListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}