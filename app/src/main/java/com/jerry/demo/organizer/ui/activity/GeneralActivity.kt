package com.jerry.demo.organizer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import com.jerry.demo.organizer.R
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Bundle
import me.eugeniomarletti.extras.intent.base.Serializable

open class GeneralActivity : AppCompatActivity() {
    open protected fun getFragmentClass(): Class<*> {
        intent.options {
            return it.fragmentClass!!
        }
    }

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
        if (savedInstanceState == null) {
            displayFragment(this, getFragmentClass(), getFragmentArgs())
        }
    }

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