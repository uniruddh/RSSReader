package `in`.uniruddh.rssreader.ui.feed

import `in`.uniruddh.rssreader.data.local.entity.Feed
import `in`.uniruddh.rssreader.data.repository.FeedRepository
import `in`.uniruddh.rssreader.utils.Utils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val workManager: WorkManager
) : ViewModel() {

    val feedList = MutableLiveData<ArrayList<Feed>>()

    init {
        getFeeds()
        syncArticles()
    }

    fun syncArticles() {
        workManager.enqueue(Utils.getArticleSyncRequest())
    }

    private fun getFeeds() {
        viewModelScope.launch {
            val list = feedRepository.getFeeds().toCollection(ArrayList())
            feedList.postValue(list)
        }
    }

    fun insertFeed(feed: Feed) {
        viewModelScope.launch {
            feedRepository.insertFeed(feed)
            getFeeds()
        }
    }
}