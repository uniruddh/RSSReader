package `in`.uniruddh.rssreader.ui.article

import `in`.uniruddh.rssreader.data.local.entity.Article
import `in`.uniruddh.rssreader.data.repository.ArticleRepository
import `in`.uniruddh.rssreader.utils.Utils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val workManager: WorkManager
) : ViewModel() {

    val articleList = MutableLiveData<List<Article>>()
    val starredArticleList = MutableLiveData<List<Article>>()

    init {
        syncArticles()
        getArticles()
        getStarredArticles()
    }

    private fun syncArticles() {
        workManager.enqueue(Utils.getArticleSyncRequest())
    }

    fun getArticles() {
        viewModelScope.launch {
            articleRepository.getArticles().collect {
                articleList.postValue(it)
            }
        }
    }

    fun getStarredArticles() {
        viewModelScope.launch {
            articleRepository.getStarredArticles().collect {
                starredArticleList.postValue(it)
            }
        }
    }

    fun starArticle(article: Article) {
        viewModelScope.launch {
            article.starred = !article.starred
            articleRepository.updateArticle(article)
            getArticles()
        }
    }
}