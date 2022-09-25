package com.example.rocketreserver

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rocketreserver.databinding.CategoryItemBinding


class CategoriesListAdapter(
    private val context:Context,
    private val category: List<GetCategoriesListQuery.Category>,
    private val mListener: OnItemClickListener?
) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
         return category.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val launch = category.get(position)
        holder.binding.site.text = launch.enName ?: ""

        val media = launch.image
        if (false) {
            Glide.with(context)
                .load(media)
                .into(holder.binding.ivCategories)
        } else {
            holder.binding.ivCategories.setImageResource(R.drawable.ic_placeholder)
        }

        holder.itemView.setOnClickListener {
            mListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick( position: Int)


    }
}