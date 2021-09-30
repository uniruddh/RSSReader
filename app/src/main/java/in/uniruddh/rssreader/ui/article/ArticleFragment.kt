package `in`.uniruddh.rssreader.ui.article

import `in`.uniruddh.rssreader.R
import `in`.uniruddh.rssreader.data.remote.ArticleSyncWorker
import `in`.uniruddh.rssreader.databinding.ArticleFragmentBinding
import `in`.uniruddh.rssreader.ui.ArticleDetailActivity
import `in`.uniruddh.rssreader.ui.generic.GenericFragment
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@AndroidEntryPoint
class ArticleFragment : GenericFragment<ArticleFragmentBinding>() {

    val viewModel: ArticleViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.article_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getArticles()
        }

        setupAdapter()
        setupWorker()
    }

    private fun setupAdapter() {
        val articleAdapter = ArticleAdapter()
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articleRecyclerView.adapter = articleAdapter
        binding.articleRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        articleAdapter.onArticleClicked.observe(viewLifecycleOwner, {
            val intent = Intent()
            intent.setClass(requireContext(), ArticleDetailActivity::class.java)
            intent.putExtra("ArticleUrl", it)
            startActivity(intent)
        })

        articleAdapter.onStarArticleClicked.observe(viewLifecycleOwner, {
            viewModel.starArticle(it)
        })

        articleAdapter.onShareArticleClicked.observe(viewLifecycleOwner, {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, it)
            startActivity(Intent.createChooser(intent, "Share Article"))
        })

        viewModel.articleList.observe(viewLifecycleOwner, {
            articleAdapter.refreshList(it)
            binding.swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun setupWorker() {
        val workManager = WorkManager.getInstance(requireContext())
        val workRequest = OneTimeWorkRequest.Builder(ArticleSyncWorker::class.java).build()
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(viewLifecycleOwner, {
            if (it.state.isFinished) {
                viewModel.getArticles()
            }
        })
    }
}