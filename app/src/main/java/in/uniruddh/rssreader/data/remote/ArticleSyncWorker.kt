package `in`.uniruddh.rssreader.data.remote

import `in`.uniruddh.rssreader.data.repository.ArticleRepository
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: abhilvare@tetravx.com
 */
@HiltWorker
class ArticleSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var articleRepository: ArticleRepository

    override suspend fun doWork(): Result {
        return try {
            articleRepository.downloadArticles()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}