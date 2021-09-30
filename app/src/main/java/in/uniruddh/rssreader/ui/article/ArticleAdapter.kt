package `in`.uniruddh.rssreader.ui.article

import `in`.uniruddh.rssreader.R
import `in`.uniruddh.rssreader.data.local.entity.Article
import `in`.uniruddh.rssreader.databinding.ArticleListItemBinding
import `in`.uniruddh.rssreader.utils.Utils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: abhilvare@tetravx.com
 */
class ArticleAdapter() :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    val onArticleClicked = MutableLiveData<String>()
    val onStarArticleClicked = MutableLiveData<Article>()
    val onShareArticleClicked = MutableLiveData<String>()

    private var articleList: ArrayList<Article> = arrayListOf()

    fun refreshList(list: ArrayList<Article>) {
        this.articleList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ArticleListItemBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding,
            {
                onArticleClicked.postValue(it)
            },
            {
                onStarArticleClicked.postValue(it)
            },
            {
                onShareArticleClicked.postValue(it)
            })
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.bind(position, article)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class ArticleViewHolder(
        private val binding: ArticleListItemBinding,
        private val onArticleClicked: (String) -> Unit,
        private val onStarArticleClicked: (Article) -> Unit,
        private val onShareArticleClicked: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, article: Article) {
            binding.title.text = article.title
            binding.feedTitle.text = article.feedTitle
            binding.author.text = " by ${article.author}"
            binding.dateTime.text = article.dateTime?.let { Utils.getRelativeTime(it) }

            if (article.starred) {
                binding.starBtn.setImageResource(R.drawable.ic_star)
            } else {
                binding.starBtn.setImageResource(R.drawable.ic_star_outline)
            }

            Glide.with(binding.image.context)
                .load(article.imageUrl)
                .placeholder(R.drawable.ic_broken_image)
                .into(binding.image)

            binding.rootView.setOnClickListener {
                article.url?.let(onArticleClicked)
            }

            binding.setStarClickListener {
                article.let(onStarArticleClicked)
            }

            binding.setShareClickListener {
                article.url?.let(onShareArticleClicked)
            }
        }
    }
}