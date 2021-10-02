package `in`.uniruddh.rssreader.utils

import `in`.uniruddh.rssreader.data.remote.ArticleSyncWorker
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import java.text.SimpleDateFormat
import java.util.*


/**
 * @Author: Aniruddh Bhilvare
 * @Date:  26/September/2021
 * @Email: abhilvare@tetravx.com
 */
object Utils {

    private val dateFormatter =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getRelativeTime(dateTime: String): String {
        dateFormatter.parse(dateTime)?.let {
            var time = it.time
            if (time < 1000000000000L) {
                time *= 1000
            }

            val now = Calendar.getInstance().timeInMillis
            if (time > now || time <= 0) {
                return "in the future"
            }

            val diff = now - time
            return when {
                diff < MINUTE_MILLIS -> "moments ago"
                diff < 2 * MINUTE_MILLIS -> "a minute ago"
                diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
                diff < 2 * HOUR_MILLIS -> "an hour ago"
                diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
                diff < 48 * HOUR_MILLIS -> "yesterday"
                else -> "${diff / DAY_MILLIS} days ago"
            }
        }
        return ""
    }

    fun getArticleSyncRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(ArticleSyncWorker::class.java)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
    }
}