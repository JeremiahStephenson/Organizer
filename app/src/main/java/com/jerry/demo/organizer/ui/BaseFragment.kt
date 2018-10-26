package com.jerry.demo.organizer.ui


import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.util.tintAllIcons
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    lateinit var viewProviderFactory: ViewModelProvider.Factory

    init {
        Injector.get().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(getLayoutResourceId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPostCreateView()
    }

    protected open fun setTitle(title: String?) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    protected open fun setTitle(@StringRes titleResId: Int) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(titleResId)
    }

    protected open fun setSubTitle(title: String?) {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = title
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        // tint all toolbar icons white
        activity?.let {
            menu?.tintAllIcons(it, android.R.color.white)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    protected fun onPostCreateView() { }

    inline fun <reified VM : ViewModel> viewModelProvider() = lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewProviderFactory).get(VM::class.java)
    }
}
