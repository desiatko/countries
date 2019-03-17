package com.desiatko.countries.presentation.view.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.desiatko.countries.domain.repository.model.CountryDetailedInfo
import com.desiatko.countries.domain.repository.model.CountryShortInfo
import com.desiatko.countries.presentation.R
import com.desiatko.countries.presentation.contract.CountryDetails
import com.desiatko.countries.presentation.di.module.FragmentModule
import com.desiatko.countries.presentation.imageloader.ImageLoader
import com.desiatko.countries.presentation.util.component
import com.desiatko.countries.presentation.util.id
import javax.inject.Inject

class CountryDetailsFragment : LoadingContentFragment(), CountryDetails.View {

    private lateinit var constraintLayout: ConstraintLayout
    @Inject
    lateinit var presenter: CountryDetails.Presenter
    @Inject
    lateinit var imageLoader: ImageLoader
    lateinit var loading: ImageLoader.Cancelable

    override val contentLayoutResource: Int = R.layout.fragment_country_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component
            .countryDetailsComponent(FragmentModule(this))
            .inject(this)
    }

    override fun onContentCreated(content: View) {
        constraintLayout = content as ConstraintLayout
        presenter.start(this, arguments!!.getSerializable(KEY_COUNTRY_INFO) as CountryShortInfo)
    }

    override fun showCountryDetails(info: CountryDetailedInfo) {
        loading = imageLoader.loadImage(info.flag, constraintLayout.findViewById(R.id.country_details_flag))

        val container: ViewGroup by constraintLayout.id(R.id.country_details_items_container)
        ListItem.values().forEachIndexed { index, item ->
            val valueText = item.getValueText(info, activity!!)
            if (valueText != null) {
                val itemView = LayoutInflater.from(constraintLayout.context)
                    .inflate(R.layout.contry_details_list_item, container, false)
                itemView.setBackgroundColor(if (index % 2 == 1) resources.getColor(R.color.colorListSelection, null) else Color.TRANSPARENT)
                container.addView(itemView)

                val captionTextView: TextView by itemView.id(R.id.country_details_item_caption)
                captionTextView.setText(item.captionText)
                captionTextView.append(":")

                val valueTextView: TextView by itemView.id(R.id.country_details_item_value)
                valueTextView.text = valueText
            }
        }

    }

    override fun clear() {
        if (this::loading.isInitialized)
            loading.cancel()
    }

    private enum class ListItem(
        @StringRes val captionText: Int,
        val valueTextProvider: ((CountryDetailedInfo) -> CharSequence?)? = null,
        val contextualValueTextProvider: ((CountryDetailedInfo, Context) -> CharSequence?)? = null
    ) {

        NAME(
            captionText = R.string.name,
            valueTextProvider = CountryDetailedInfo::name
        ),
        LOCAL_NAME(
            captionText = R.string.local_name,
            valueTextProvider = CountryDetailedInfo::localName
        ),
        ALPHA(
            captionText = R.string.alpha_code,
            valueTextProvider = CountryDetailedInfo::alphaCode
        ),
        POPULATION(
            captionText = R.string.population,
            valueTextProvider = { it.population.toString() }
        ),
        LOCATION(
            captionText = R.string.location,
            contextualValueTextProvider = { item, context ->
                item.location?.let { loc ->
                    context.getString(R.string.location_value_format, loc.lat, loc.long)
                }
            }
        ),
        CAPITAL(
            captionText = R.string.capital,
            valueTextProvider = CountryDetailedInfo::capital
        ),
        REGION(
            captionText = R.string.region,
            valueTextProvider = CountryDetailedInfo::region
        ),
        SUBREGION(
            captionText = R.string.subregion,
            valueTextProvider = CountryDetailedInfo::subregion
        ),
        AREA(
            captionText = R.string.area,
            contextualValueTextProvider = { item, context ->
                item.area?.let { context.getString(R.string.area_value_format, it) }
            }
        ),
        CURRENCIES(
            captionText = R.string.currencies,
            valueTextProvider = { it.currencies?.joinToString(separator = ", ") }
        ),
        LANGUAGES(
            captionText = R.string.languages,
            valueTextProvider = { it.languages?.joinToString(separator = ", ") }
        );

        fun getValueText(info: CountryDetailedInfo, context: Context) =
            valueTextProvider?.invoke(info) ?: contextualValueTextProvider?.invoke(info, context)
    }


    companion object {
        const val KEY_COUNTRY_INFO = "country_info"

        fun create(info: CountryShortInfo): CountryDetailsFragment {
            val fragment = CountryDetailsFragment()
            fragment.arguments = Bundle().apply { putSerializable(KEY_COUNTRY_INFO, info) }
            return fragment
        }
    }
}