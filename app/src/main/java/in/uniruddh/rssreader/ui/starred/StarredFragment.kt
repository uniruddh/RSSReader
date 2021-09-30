package `in`.uniruddh.rssreader.ui.starred

import `in`.uniruddh.rssreader.R
import `in`.uniruddh.rssreader.databinding.StarredFragmentBinding
import `in`.uniruddh.rssreader.ui.ArticleDetailActivity
import `in`.uniruddh.rssreader.ui.generic.GenericFragment
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@AndroidEntryPoint
class StarredFragment : GenericFragment<StarredFragmentBinding>() {

    private val viewModel: StarredViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.starred_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupAdapter()
    }

    private fun setupAdapter() {
        val articleAdapter = StarredAdapter()
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

        viewModel.starredArticleList.observe(viewLifecycleOwner, {
            articleAdapter.refreshList(it)
        })
    }

}