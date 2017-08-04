package com.jerry.demo.organizer.ui.activity

import com.jerry.demo.organizer.ui.category.CategoriesFragment

class MainActivity : GeneralActivity() {
    override fun getFragmentClass(): Class<*> {
        return CategoriesFragment::class.java
    }
}
