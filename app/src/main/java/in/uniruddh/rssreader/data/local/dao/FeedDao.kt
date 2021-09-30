package `in`.uniruddh.rssreader.data.local.dao

import `in`.uniruddh.rssreader.data.local.entity.Feed
import androidx.room.*

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg feed: Feed)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(feed: Feed)

    @Delete
    fun delete(feed: Feed)

    @Query("SELECT * FROM feed")
    fun getFeeds(): Array<Feed>
}