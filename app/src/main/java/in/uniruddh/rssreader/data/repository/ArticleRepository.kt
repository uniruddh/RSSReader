package `in`.uniruddh.rssreader.data.repository

import `in`.uniruddh.rssreader.data.local.dao.ArticleDao
import `in`.uniruddh.rssreader.data.local.dao.FeedDao
import `in`.uniruddh.rssreader.data.local.entity.Article
import `in`.uniruddh.rssreader.data.model.ChannelItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class ArticleRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val feedDao: FeedDao,
    private val okHttpClient: OkHttpClient,
    private val moshi: Moshi
) {

    private val listData: ParameterizedType =
        Types.newParameterizedType(List::class.java, ChannelItem::class.java)
    private var jsonAdapter: JsonAdapter<List<ChannelItem>> = moshi.adapter(listData)

    suspend fun downloadArticles() = withContext(Dispatchers.IO) {

        val feeds = feedDao.getFeeds()
        feeds.forEach { feed ->
            try {
                val request = Request.Builder()
                    .url(feed.feedUrl)
                    .build()

                val response = okHttpClient.newCall(request).execute()

                if (response.isSuccessful && response.body != null) {
                    val xmlToJson = XmlToJson.Builder(response.body!!.string()).build().toJson()
                    xmlToJson?.let { jsonObject ->
                        val rss = jsonObject.getJSONObject("rss")
                        val channel = rss.getJSONObject("channel")
                        val item = channel.getJSONArray("item")
                        val channelItems = jsonAdapter.fromJson(item.toString())
                        channelItems?.forEach { items ->

                            items.title?.let { itemTitle ->
                                if (!articleDao.isExist(itemTitle)) {
                                    val imgUrl = items.encoded?.substringAfter("src=\"")
                                        ?.substringBefore("\"")

                                    val article = Article().apply {
                                        title = itemTitle
                                        url = items.link
                                        feedTitle = feed.feedTitle
                                        author = items.creator
                                        dateTime = items.updated
                                        imageUrl = imgUrl
                                    }
                                    articleDao.insert(article)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun getArticles() = articleDao.getArticles()

    fun getStarredArticles() = articleDao.getStarredArticles()

    suspend fun updateArticle(article: Article) = withContext(Dispatchers.IO) {
        articleDao.update(article)
    }
}