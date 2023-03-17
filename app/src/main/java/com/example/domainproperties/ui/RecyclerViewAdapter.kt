package com.example.domainproperties.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.example.domainproperties.R
import com.example.domainproperties.databinding.LayoutPropertyItemBinding
import com.example.domainproperties.model.SearchResult


//adapter to hold and display state in recylerview
class RecyclerViewAdapter(
    private val values: List<SearchResult> = emptyList()
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutPropertyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        with(holder) {
            when (item.listing_type) {
                holder.itemView.context.getString(
                    R.string.topspot
                ) -> {
                    price.text = holder.itemView.context.getString(R.string.s_price, item.price)
                    headline.text = item.headline
                    Glide.with(agencyLogo)
                        .load(item.advertiser.images.logo_url)
                        .into(agencyLogo)
                    if (item.bedroom_count != null) {
                        bedBathCarCount.text = holder.itemView.context.getString(
                            R.string.s_bed_s_bath_s_car,
                            item.bedroom_count.toString(),
                            item.bathroom_count.toString(),
                            item.car_space_count.toString()
                        )
                    }
                }
                holder.itemView.context.getString(R.string.project) -> {
                    headline.text = item.project.project_name
                    if (item.project.project_price_from != null) {
                        price.text = holder.itemView.context.getString(
                            R.string.s_price_from,
                            item.project.project_price_from.toString()
                        )
                    } else {
                        val priceFrom = item.project.child_listings.minBy { it.price }.price
                        price.text = priceFrom
                    }
                    Glide.with(agencyLogo)
                        .load(item.project.project_logo_image)
                        .into(agencyLogo)
                    val bedCount =
                        item.project.child_listings.minBy { it.bedroom_count }.bedroom_count.toInt()
                            .toString()
                    val bathCount =
                        item.project.child_listings.minBy { it.bathroom_count }.bathroom_count.toInt()
                            .toString()
                    val carCount =
                        item.project.child_listings.minBy { it.carspace_count }.carspace_count.toString()
                    bedBathCarCount.text = holder.itemView.context.getString(
                        R.string.from_s_bed_s_bath_s_car,
                        bedCount,
                        bathCount,
                        carCount
                    )
                }
            }
            Glide.with(propertyImage)
                .load(item.media[0].image_url)
                .into(propertyImage)
            address.text = item.address
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: LayoutPropertyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val price: AppCompatTextView = binding.price
        val headline: AppCompatTextView = binding.headline
        val bedBathCarCount: AppCompatTextView = binding.bedBathCar
        val propertyImage: AppCompatImageView = binding.propertyImage
        val agencyLogo: AppCompatImageView = binding.agencyLogo
        val address: AppCompatTextView = binding.address

    }

}