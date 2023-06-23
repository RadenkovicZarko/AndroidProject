package rs.raf.vezbe11.presentation.view.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.vezbe11.presentation.view.fragments.CategoryFragment
import rs.raf.vezbe11.presentation.view.fragments.FilterFragment
import rs.raf.vezbe11.presentation.view.fragments.PersonalFragment

class PagerAdapter: FragmentPagerAdapter {

        var ITEM_COUNT = 3

        var FRAGMENT_1 = 0
        var FRAGMENT_2 = 1
        var FRAGMENT_3 = 2

        constructor(fragmentManager: FragmentManager) : super(fragmentManager)

        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            return when(position) {
                FRAGMENT_1 -> CategoryFragment()
                FRAGMENT_2 -> FilterFragment()
                FRAGMENT_3 -> PersonalFragment()
                else -> CategoryFragment()
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