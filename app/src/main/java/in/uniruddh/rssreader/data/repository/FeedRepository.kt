package `in`.uniruddh.rssreader.data.repository

import `in`.uniruddh.rssreader.data.local.dao.FeedDao
import `in`.uniruddh.rssreader.data.local.entity.Feed
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class FeedRepository @Inject constructor(
    private val feedDao: FeedDao
) {

    suspend fun getFeeds() = withContext(Dispatchers.IO) {
        feedDao.getFeeds()
    }

    suspend fun insertFeed(feed: Feed) = withContext(Dispatchers.IO) {
        feedDao.insert(feed)
    }
}