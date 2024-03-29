package `in`.uniruddh.rssreader.ui.generic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  25/September/2021
 * @Email: abhilvare@tetravx.com
 */
abstract class GenericFragment<T : ViewDataBinding> : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}