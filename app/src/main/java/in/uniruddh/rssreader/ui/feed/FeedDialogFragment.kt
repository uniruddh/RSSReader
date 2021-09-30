package `in`.uniruddh.rssreader.ui.feed

import `in`.uniruddh.rssreader.data.local.entity.Feed
import `in`.uniruddh.rssreader.data.remote.ArticleSyncWorker
import `in`.uniruddh.rssreader.databinding.FeedAddDialogBinding
import `in`.uniruddh.rssreader.utils.toastShort
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: abhilvare@tetravx.com
 */
@AndroidEntryPoint
class FeedDialogFragment : DialogFragment() {
    companion object {
        const val TAG = "FeedDialogFragment"
    }

    lateinit var binding: FeedAddDialogBinding
    private val viewModel by activityViewModels<FeedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FeedAddDialogBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setCancelClickListener {
            dismiss()
        }

        binding.setSaveClickListener {
            val feedTitle = binding.title.text.toString()
            val feedUrl = binding.url.text.toString()
            if (feedTitle.isNullOrBlank()) {
                context?.toastShort("Please enter feed title")
            } else if (!Patterns.WEB_URL.matcher(feedUrl).matches()) {
                context?.toastShort("Please enter valid URL")
            } else {
                val feed = Feed()
                feed.feedTitle = feedTitle
                feed.feedUrl = feedUrl
                viewModel.insertFeed(feed)
                dismiss()

                val worker = OneTimeWorkRequest.Builder(ArticleSyncWorker::class.java)
                WorkManager.getInstance(requireContext()).enqueue(worker.build())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}