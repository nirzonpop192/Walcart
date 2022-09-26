package com.example.rocketreserver

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rocketreserver.databinding.SubCategoryItemBinding


class SubCategoriesListAdapter(
    private val context:Context,
    private val subCategory: List<GetCategoriesListQuery.Parent1?>?
) :
    RecyclerView.Adapter<SubCategoriesListAdapter.ViewHolder>() {

    private var mExpandedPosition: Int=-1

    class ViewHolder(val binding: SubCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
         return subCategory?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SubCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val subCates = subCategory?.get(position)
        holder.binding.tvSubCategories.text = subCates?.enName ?: ""

        val isExpanded = position === mExpandedPosition
        holder.binding.llDetails.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.itemView.isActivated = isExpanded

        holder.itemView.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(position)
        }
    }


}