package com.example.roomdatabasekotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasekotlin.databinding.SingleItemBinding


class MyAdapter(private val user:List<User>,private val listener:OnItemClick):
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view  = SingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount()=user.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = user[position].name
        holder.email.text= user[position].email

    }

    inner class MyViewHolder(private val binding: SingleItemBinding)
        :RecyclerView.ViewHolder(binding.root), View.OnClickListener,View.OnLongClickListener {

        val name:TextView = binding.singleNameid
        val email:TextView = binding.singleEmailid
        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onLongItemClick(position)
            }
            return false
        }

    }

    interface OnItemClick{
        fun onItemClick(i:Int)
        fun onLongItemClick(i:Int)
    }
}