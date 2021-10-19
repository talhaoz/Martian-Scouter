package com.talhaoz.martianstalker.adapter

import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.squareup.picasso.Picasso
import com.talhaoz.martianstalker.R
import com.talhaoz.martianstalker.model.Photo
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlinx.android.synthetic.main.single_item_dialog.view.*


class RecyclerViewAdapter(
) : RecyclerView.Adapter<RecyclerViewAdapter.TabViewHolder>() {

    private var items: ArrayList<Photo> = arrayListOf()
    private var itemsBackup: ArrayList<Photo> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val myHolder = TabViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_layout,
                parent,
                false
            )
        )

        myHolder.itemView.setOnClickListener {
            val pos = myHolder.adapterPosition

            // create a dialog box to show clicked item data
            if (pos != NO_POSITION) {
                val dialog = AlertDialog.Builder(parent.context)
                val dialogView = LayoutInflater.from(parent.context).inflate(R.layout.single_item_dialog,null)
                dialog.setView(dialogView)
                dialog.setCancelable(false)
                dialog.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->  }
                dialog.show()

                Picasso.get().load(items[pos].img_src).into(dialogView.singleImage)
                dialogView.singleImage.setBackgroundColor(Color.parseColor("#203768"))
                dialogView.singlePhotoDate.text=items[pos].earth_date
                dialogView.singleRoverName.text=items[pos].rover.name
                dialogView.singleCameraName.text=items[pos].camera.full_name
                dialogView.singleMissionStatus.text=items[pos].rover.status
                dialogView.singleDepartureDate.text=items[pos].rover.launch_date
                dialogView.singleLandingDate.text=items[pos].rover.landing_date




            }
        }
        return myHolder
    }


    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {

        val item = items[position]

        Picasso.get().load(item.img_src).into(holder.itemView.photo_ImageView)


    }

    fun addAll(list: ArrayList<Photo>, isRefresh : Boolean) {

        if(isRefresh)
            items.clear()

        items.addAll(list)
        notifyDataSetChanged()


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun getItemCount(): Int {
        return items.size
    }


    inner class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
