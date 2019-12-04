package com.sergiocasero.weather.ui.adapter

import android.view.View
import com.sergiocasero.weather.R
import com.sergiocasero.weather.domain.model.Forecast
import com.sergiocasero.weather.ui.extension.loadIcon
import kotlinx.android.synthetic.main.item_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter : RootAdapter<Forecast>() {

    override val itemLayoutId: Int = R.layout.item_forecast

    override fun viewHolder(view: View): RootViewHolder<Forecast> = ForecastViewHolder(view)

    class ForecastViewHolder(itemView: View) : RootViewHolder<Forecast>(itemView) {

        override fun bind(model: Forecast) {

            itemView.date.text = model.dt.toDate()
            itemView.temp.text = "${model.main.temp}ºC"

            if (model.weather.isNotEmpty()) {
                itemView.icon.loadIcon(model.weather[0].icon)
                itemView.status.text = model.weather[0].description
            }
        }
    }

}

private fun Int.toDate(): String {
    val date = Date((this * 1000).toLong())
    val dateFormat = SimpleDateFormat("MMM\nHH:mm", Locale.getDefault())
    return dateFormat.format(date)
}
