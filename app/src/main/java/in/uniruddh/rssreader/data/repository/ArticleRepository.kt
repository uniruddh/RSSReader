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
            feed.feedUrl.let { feedUrl ->
                try {
                    val request = Request.Builder()
                        .url(feedUrl)
                        .build()

                    val response = okHttpClient.newCall(request).execute()

                    if (response.isSuccessful && response.body != null) {
                        val xmlToJson = XmlToJson.Builder(response.body!!.string()).build().toJson()
                        xmlToJson?.let { jsonObject ->
                            val rss = jsonObject.getJSONObject("rss")
                            val channel = rss.getJSONObject("channel")
                            val item = channel.getJSONArray("item")
                            val channelItmes = jsonAdapter.fromJson(item.toString())
                            channelItmes?.forEach { items ->

                                items.title?.let { title ->
                                    if (!articleDao.isExist(title)) {
                                        val article = Article()
                                        article.title = title
                                        article.url = items.link
                                        article.feedTitle = feed.feedTitle
                                        article.author = items.creator
                                        article.dateTime = items.updated
                                        article.imageUrl = items.encoded?.substringAfter("src=\"")
                                            ?.substringBefore("\"")

                                        insertArticle(article)
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {

                }
            }
        }
    }

    suspend fun getArticles() = withContext(Dispatchers.IO) {
        articleDao.getArticles()
    }

    suspend fun getStarredArticles() = withContext(Dispatchers.IO) {
        articleDao.getStarredArticles()
    }

    suspend fun insertArticle(article: Article) = withContext(Dispatchers.IO) {
        articleDao.insert(article)
    }

    suspend fun updateArticle(article: Article) = withContext(Dispatchers.IO) {
        articleDao.update(article)
    }
}