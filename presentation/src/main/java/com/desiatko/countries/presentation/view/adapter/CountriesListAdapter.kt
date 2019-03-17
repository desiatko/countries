package com.desiatko.countries.presentation.view.adapter

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desiatko.countries.domain.repository.model.CountryShortInfo
import com.desiatko.countries.presentation.util.id
import com.desiatko.countries.presentation.R
import com.desiatko.countries.presentation.imageloader.ImageLoader
import kotlin.properties.Delegates

class Holder(view: View) : RecyclerView.ViewHolder(view) {
    val flag: ImageView by id(R.id.country_list_item_flag)
    val name: TextView by id(R.id.country_list_item_name)
    val population: TextView by id(R.id.country_list_item_population)
    var loading: ImageLoader.Cancelable? = null
}

class CountriesListAdapter(
    private val resources: Resources,
    private val items: List<CountryShortInfo>,
    private val imageLoader: ImageLoader,
    private val onClick: (CountryShortInfo) -> Unit
) : RecyclerView.Adapter<Holder>() {

    var selection: Int by Delegates.observable(-1) { _, from, to ->
        onSelectionChanged(from, to)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)).apply {
            flag.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val country = items[position]
        val context = holder.itemView.context
        with(holder) {
            itemView.setOnClickListener {
                selection = adapterPosition
                onClick(country)
            }
            itemView.setBackgroundColor(if (position == selection) resources.getColor(R.color.colorListSelection, null) else Color.TRANSPARENT)
            name.text = context.getString(R.string.country_name_format, country.name, country.localName)
            population.text = formatPopulation(country.population)
            loading = imageLoader.loadImage(country.flag, flag)
        }
    }

    override fun onViewRecycled(holder: Holder) {
        super.onViewRecycled(holder)
        holder.loading?.cancel()
    }

    private fun onSelectionChanged(from: Int, to: Int) {
        notifyItemChanged(from)
        notifyItemChanged(to)
    }

    private fun formatPopulation(population: Int): String {
        var level = 0
        var p = population.toFloat()
        while (p > 1000 && level < 2) {
            p /= 1000
            level++
        }
        val postfix = when (level) {
            0 -> ""
            1 -> "k"
            2 -> "m"
            else -> throw AssertionError("can't happen")
        }
        return if (level == 0) population.toString() else "%.2f$postfix".format(p)
    }
}