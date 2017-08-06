package com.jerry.demo.organizer.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jerry.demo.organizer.R
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Bundle
import me.eugeniomarletti.extras.intent.base.Serializable

/**
 * General use activity that only displays a fragment
 */
open class GeneralActivity : AppCompatActivity() {
    // get the fragment class from the intent extras
    open protected fun getFragmentClass(): Class<*> {
        intent.options {
            return it.fragmentClass!!
        }
    }

    // get the fragment bundle arguments from the intent extras
    open protected fun getFragmentArgs(): Bundle? {
        intent.options {
            it.fragmentBundle?.let {
                return it
            }
            return null
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    open protected fun showBackButton(): Boolean {
        return true
    }

    // displays fragment and passes in bundle arguments
    private fun displayFragment(activity: FragmentActivity, fragmentClass: Class<*>, args: Bundle? = null) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_pos1,
                Fragment.instantiate(activity, fragmentClass.name, args), fragmentClass.name)
        transaction.commit()
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, GeneralActivity::class)

    object IntentOptions {
        var Intent.fragmentClass by IntentExtra.Serializable<Class<*>>()
        var Intent.fragmentBundle by IntentExtra.Bundle()
    }
}