package com.jerry.demo.organizer.ui

import com.jerry.demo.organizer.ui.category.CategoriesFragment

class MainActivity : GeneralActivity() {
    override fun getFragmentClass(): Class<*> = CategoriesFragment::class.java
    override fun showBackButton() = false
}
