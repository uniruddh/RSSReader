package `in`.uniruddh.rssreader.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@Entity
class Article {
    @PrimaryKey(autoGenerate = true)
    var aid: Int = 0

    @ColumnInfo(name = "article_title")
    var title: String? = ""

    @ColumnInfo(name = "article_feed")
    var feedTitle: String? = ""

    @ColumnInfo(name = "article_url")
    var url: String? = ""

    @ColumnInfo(name = "article_image_url")
    var imageUrl: String? = ""

    @ColumnInfo(name = "article_author")
    var author: String? = ""

    @ColumnInfo(name = "article_date_time")
    var dateTime: String? = ""

    @ColumnInfo(name = "article_tags")
    var tags: String? = ""

    @ColumnInfo(name = "article_starred")
    var starred: Boolean = false
}
