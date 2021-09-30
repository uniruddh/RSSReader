package `in`.uniruddh.rssreader.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@JsonClass(generateAdapter = true)
data class ChannelItem(
    @Json(name = "title") val title: String? = "",
    @Json(name = "link") val link: String? = "",
    @Json(name = "category") val category: List<Content>? = listOf(),
    @Json(name = "atom:updated") val updated: String? = "",
    @Json(name = "dc:creator") val creator: String? = "",
    @Json(name = "content:encoded") val encoded: String? = ""

)
