package `in`.uniruddh.rssreader.ui.feed

import `in`.uniruddh.rssreader.R
import `in`.uniruddh.rssreader.databinding.FeedFragmentBinding
import `in`.uniruddh.rssreader.ui.generic.GenericFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: uniruddh@gmail.com
 */
@AndroidEntryPoint
class FeedFragment : GenericFragment<FeedFragmentBinding>() {

    private val viewModel by activityViewModels<FeedViewModel>()

    override fun getLayoutId(): Int = R.layout.feed_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.setNewFeedClickListener {
            showAddFeedDialog()
        }

        val feedAdapter = FeedAdapter()
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.feedRecyclerView.adapter = feedAdapter
        viewModel.feedList.observe(viewLifecycleOwner, {
            feedAdapter.refreshList(it)
        })
    }

    private fun showAddFeedDialog() {
        FeedDialogFragment().show(requireActivity().supportFragmentManager, FeedDialogFragment.TAG)
    }

}