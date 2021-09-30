package `in`.uniruddh.rssreader.data.local

import `in`.uniruddh.rssreader.data.local.dao.ArticleDao
import `in`.uniruddh.rssreader.data.local.dao.FeedDao
import `in`.uniruddh.rssreader.data.local.entity.Article
import `in`.uniruddh.rssreader.data.local.entity.Feed
import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@Database(entities = [Feed::class, Article::class], version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun feedDao(): FeedDao
    abstract fun articleDao(): ArticleDao
}