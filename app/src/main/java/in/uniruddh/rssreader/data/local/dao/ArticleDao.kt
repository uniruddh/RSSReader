package `in`.uniruddh.rssreader.data.local.dao

import `in`.uniruddh.rssreader.data.local.entity.Article
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg article: Article)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(article: Article)

    @Delete
    fun delete(article: Article)

    @Query("SELECT * FROM article ORDER BY article_date_time DESC")
    fun getArticles(): Flow<List<Article>>

    @Query("SELECT * FROM article WHERE article_starred= :isStarred ORDER BY article_date_time DESC")
    fun getStarredArticles(isStarred: Boolean = true): Flow<List<Article>>

    @Query("SELECT EXISTS(SELECT article_title FROM article WHERE article_title = :title)")
    fun isExist(title: String): Boolean
}