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
class Feed {
    @PrimaryKey(autoGenerate = true)
    var fid: Int = 0

    @ColumnInfo(name = "feed_title")
    var feedTitle: String = ""

    @ColumnInfo(name = "feed_url")
    var feedUrl: String = ""
}
