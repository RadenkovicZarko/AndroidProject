package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import rs.raf.vezbe11.R
import rs.raf.vezbe11.presentation.view.adapters.TabbedPageAdapter

class PersonalFragment: Fragment(R.layout.fragment_personal) {

    var viewPager: ViewPager? = null
    var tabLayout: TabLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initTabs()
    }

    private fun initTabs() {
        viewPager = view?.findViewById(R.id.viewPager)
        viewPager?.adapter = TabbedPageAdapter(childFragmentManager, requireContext())
        tabLayout = view?.findViewById(R.id.tabLayout)
        tabLayout?.setupWithViewPager(viewPager)
    }

}