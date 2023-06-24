package rs.raf.vezbe11.presentation.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import rs.raf.vezbe11.R
import rs.raf.vezbe11.presentation.view.fragments.CategoryFragment
import rs.raf.vezbe11.presentation.view.fragments.FilterFragment
import rs.raf.vezbe11.presentation.view.fragments.ListOfMealsFragment
import rs.raf.vezbe11.presentation.view.fragments.PersonalFragment
import timber.log.Timber

class PagerAdapter: FragmentPagerAdapter {

        var ITEM_COUNT = 3

        var FRAGMENT_1 = 0
        var FRAGMENT_2 = 1
        var FRAGMENT_3 = 2

        private val fragments = mutableListOf<Fragment>()
        var fragment1 : Fragment = CategoryFragment(this)
        var fragment2 = FilterFragment()
        var fragment3 = PersonalFragment()
        constructor(fragmentManager: FragmentManager) : super(fragmentManager)

        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            return when(position) {
                FRAGMENT_1 -> fragment1
                FRAGMENT_2 -> fragment2
                FRAGMENT_3 -> fragment3
                else -> fragment1
            }
        }

        override fun getCount(): Int {
            return ITEM_COUNT
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                FRAGMENT_1 -> "First"
                FRAGMENT_2 -> "Second"
                FRAGMENT_3 -> "Third"
                else -> "First"
            }
        }




}