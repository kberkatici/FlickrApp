package com.example.kotlinfirebaseflickr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfirebaseflickr.R
import com.example.kotlinfirebaseflickr.model.FlickrModel

class RecyclerViewAdapter
    (private val flickrList : ArrayList<FlickrModel>, private val listener: Listener): RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(flickrModel: FlickrModel)
    }

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(flickrModel: FlickrModel, listener: Listener) {

            itemView.setOnClickListener{
                listener.onItemClick(flickrModel)
            }
            itemView.recycleEmailText.text = flickrModel.owner
            itemView.recycleCommentText.text =flickrModel.title

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(flickrList[position],listener)
    }

    override fun getItemCount(): Int {
        return flickrList.count()
    }
}