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
import com.talhaoz.martianstalker.util.Constants.Companion.CURIOSITY_ROVER
import com.talhaoz.martianstalker.viewmodel.MissionViewModel
import kotlinx.android.synthetic.main.curiosity_fragment.*



class CuriosityFragment : Fragment(R.layout.curiosity_fragment) {

    lateinit var missionAdapter: RecyclerViewAdapter
    private lateinit var viewModel: MissionViewModel
    var isRefresh : Boolean = false
    var oldSelectedItem : MenuItem? = null

    val TAG = "CuriosityFragment"

    override fun onStart() {
        super.onStart()
        // on tab change we loose the last filtered camera selection , so store it in SP . But make it zero on first start
        val sharedPreferences: SharedPreferences =   androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("menuIdCur",0).apply()
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
        return inflater.inflate(R.layout.curiosity_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MissionViewModel::class.java)
        viewModel.getPhotos(CURIOSITY_ROVER,null,false) // call for api

        missionAdapter = RecyclerViewAdapter()
        curiosityRecyclerView.adapter = missionAdapter
        curiosityRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        observeLiveData()




        curiosityRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(!viewModel.getPhotos(CURIOSITY_ROVER,null,false)) {
                        curiosityProgressBar.visibility = View.VISIBLE
                        /*viewModel.missionList.observe(this@MainActivity, Observer {
                            missionAdapter.addAll(it)
                        }) */
                    }
                    //missionAdapter.addAll(viewModel.getPhotos())
                }
                else
                    curiosityProgressBar.visibility = View.GONE
            }
        })


    }

    // observe the live data to make the list update once data is fetched
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
                    Toast.makeText(context,"Error while loading photos!",Toast.LENGTH_LONG).show()
                } else {
                    //dataError.visibility = View.GONE
                }
            }

        })
        viewModel.dataLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it) {
                    curiosityProgressBar.visibility = View.VISIBLE // observe is data loading
                } else {
                    curiosityProgressBar.visibility = View.GONE
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val sharedPreferences: SharedPreferences =   androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        // get the id of last item to uncheck it later
        var id =sharedPreferences.getInt("menuIdCur",0)

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
        // on tab change we loose the last filtered camera selection , so store it in SP
        editor.putInt("menuIdCur",item.itemId).apply()

            oldSelectedItem?.isChecked = false
            item.isChecked = true
            oldSelectedItem = item


            var cameraName: String? = item.title.toString()
            if (cameraName.equals("ALL")) // as query should like that
                cameraName = null
            isRefresh = true
            viewModel.getPhotos(CURIOSITY_ROVER, cameraName, true)

        return true
    }


}