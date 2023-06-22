package rs.raf.vezbe11.presentation.view.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.vezbe11.R
import rs.raf.vezbe11.presentation.view.fragments.PersonalMealsFragment
import rs.raf.vezbe11.presentation.view.fragments.PersonalPlanFragment
import rs.raf.vezbe11.presentation.view.fragments.PersonalStatsFragment

class TabbedPageAdapter (
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

//    like static in java
    companion object {
        private const val ITEM_COUNT = 3
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> PersonalMealsFragment()
            FRAGMENT_2 -> PersonalPlanFragment()
            FRAGMENT_3 -> PersonalStatsFragment()
            else -> PersonalMealsFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> "Meals"
            FRAGMENT_2 -> "Plan"
            FRAGMENT_3 -> "Stats"
            else -> "Meals"
        }
    }
}