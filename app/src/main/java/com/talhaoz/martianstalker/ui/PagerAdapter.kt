package com.talhaoz.martianstalker.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.talhaoz.martianstalker.ui.fragments.*


const val TAB_COUNT = 3

class PagerAdapter (fragManager : FragmentManager) : FragmentPagerAdapter(fragManager)
{


    override fun getItem(position: Int): Fragment {
        return when(position) {

            0 -> {
                CuriosityFragment()
            }
            1 -> {
                OpportunityFragment()
            }
            else -> { return SpiritFragment()
            }
        }
    }

    override fun getCount(): Int {
        return TAB_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Curiosity"
            1 -> "Opportunity"
            else -> { return "Spirit"}

        }
    }


}