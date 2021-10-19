package com.talhaoz.martianstalker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talhaoz.martianstalker.R
import com.talhaoz.martianstalker.adapter.RecyclerViewAdapter
import com.talhaoz.martianstalker.util.Constants
import com.talhaoz.martianstalker.util.Constants.Companion.SPIRIT_ROVER
import com.talhaoz.martianstalker.viewmodel.MissionViewModel
import kotlinx.android.synthetic.main.spirit_fragment.*


class SpiritFragment : Fragment(R.layout.spirit_fragment) {

    lateinit var missionAdapter: RecyclerViewAdapter
    private lateinit var viewModel: MissionViewModel
    var isRefresh : Boolean = false
    var oldSelectedItem : MenuItem? = null

    val TAG = "SpiritFragment"

    override fun onStart() {
        super.onStart()
        val sharedPreferences: SharedPreferences =   androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("menuIdSpi",0).apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.spirit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MissionViewModel::class.java)
        viewModel.getPhotos(SPIRIT_ROVER,null,false)


        missionAdapter = RecyclerViewAdapter()
        spiritRecyclerView.adapter = missionAdapter
        spiritRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        observeLiveData()




        spiritRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                    if(!viewModel.getPhotos(SPIRIT_ROVER,null,false)) {
                        spiritProgressBar.visibility = View.VISIBLE

                    }

                }
                else
                    spiritProgressBar.visibility = View.GONE
            }
        })


    }


    private fun observeLiveData(){
        viewModel.missionList.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                missionAdapter.addAll(it,isRefresh)
                if(isRefresh)
                    isRefresh=false
            }

        })

        viewModel.dataError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it) {
                    Toast.makeText(context,"Resimleri yüklerken bir sorun oluştu!",Toast.LENGTH_LONG).show()
                } else {
                    //dataError.visibility = View.GONE
                }
            }

        })
        viewModel.dataLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it) {
                    spiritProgressBar.visibility = View.VISIBLE
                } else {
                    spiritProgressBar.visibility = View.GONE
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val sharedPreferences: SharedPreferences =   androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

        var id =sharedPreferences.getInt("menuIdSpi",0)

        menu.forEach { item ->
            if(item.itemId == id)
                oldSelectedItem=item
        }
        if(id==0)
            oldSelectedItem=menu.getItem(0)

        oldSelectedItem?.isChecked =true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPreferences: SharedPreferences =   androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()

        editor.putInt("menuIdSpi",item.itemId).apply()

        oldSelectedItem?.isChecked = false
        item.isChecked = true
        oldSelectedItem = item
        var cameraName: String? = item.title.toString()
        if (cameraName.equals("ALL"))
            cameraName = null
        isRefresh = true
        viewModel.getPhotos(SPIRIT_ROVER,cameraName,true)
        return true
    }

}