package com.jerry.demo.organizer.ui.fragment

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import com.jerry.demo.organizer.util.tintAllIcons

abstract class BaseFragment : Fragment(), LifecycleRegistryOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return this.lifecycleRegistry
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(getLayoutResourceId(), container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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
        menu?.tintAllIcons(activity, android.R.color.white)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    protected fun onPostCreateView() {
    }
}
