package `in`.uniruddh.rssreader.ui.starred

import `in`.uniruddh.rssreader.data.local.entity.Article
import `in`.uniruddh.rssreader.data.repository.ArticleRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class StarredViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    val starredArticleList = MutableLiveData<ArrayList<Article>>()

    init {
        getStarredArticles()
    }

    fun getStarredArticles() {
        viewModelScope.launch {
            val list = articleRepository.getStarredArticles().toCollection(ArrayList())
            starredArticleList.postValue(list)
        }
    }

    fun starArticle(article: Article) {
        viewModelScope.launch {
            article.starred = !article.starred
            articleRepository.updateArticle(article)
            getStarredArticles()
        }
    }
}