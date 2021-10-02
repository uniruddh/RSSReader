package `in`.uniruddh.rssreader.di

import `in`.uniruddh.rssreader.BuildConfig
import `in`.uniruddh.rssreader.data.local.MainDatabase
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        MainDatabase::class.java,
        provideDatabaseName()
    ).build()

    @Singleton
    @Provides
    fun provideDatabaseName() = "rss-reader"

    @Singleton
    @Provides
    fun provideFeedDao(database: MainDatabase) = database.feedDao()

    @Singleton
    @Provides
    fun provideArticleDao(database: MainDatabase) = database.articleDao()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}