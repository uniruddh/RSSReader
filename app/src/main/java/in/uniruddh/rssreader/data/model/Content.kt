package `in`.uniruddh.rssreader.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@JsonClass(generateAdapter = true)
data class Content(
    @Json(name = "content") val content: String? = ""
)
