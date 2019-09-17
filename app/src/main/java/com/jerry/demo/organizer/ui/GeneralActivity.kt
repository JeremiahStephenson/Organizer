package com.jerry.demo.organizer.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import com.jerry.demo.organizer.R
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Bundle
import me.eugeniomarletti.extras.intent.base.Serializable

/**
 * General use activity that only displays a fragment
 */
open class GeneralActivity : BaseActivity() {
    // get the fragment class from the intent extras
    protected open fun getFragmentClass(): Class<*> {
        intent.options {
            return it.fragmentClass!!
        }
    }

    // get the fragment bundle arguments from the intent extras
    protected open fun getFragmentArgs(): Bundle? {
        intent.options {
            return it.fragmentBundle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        setSupportActionBar(mainToolbar)

        // show the back button
        supportActionBar?.let {
            it.setHomeButtonEnabled(showBackButton())
            it.setDisplayHomeAsUpEnabled(showBackButton())
        }

        // only setup the fragment when the activity is created initially
        if (savedInstanceState == null) {
            displayFragment(this, getFragmentClass(), getFragmentArgs())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun showBackButton() = true

    // displays fragment and passes in bundle arguments
    private fun displayFragment(activity: FragmentActivity, fragmentClass: Class<*>, args: Bundle? = null) {
        activity.supportFragmentManager.beginTransaction().apply {
            val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(fragmentClass::class.java.classLoader!!, fragmentClass.name)
            fragment.arguments = args
            replace(R.id.fragment_pos1, fragment, fragmentClass.name)
            commit()
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, GeneralActivity::class)

    object IntentOptions {
        var Intent.fragmentClass by IntentExtra.Serializable<Class<*>>()
        var Intent.fragmentBundle by IntentExtra.Bundle()
    }
}