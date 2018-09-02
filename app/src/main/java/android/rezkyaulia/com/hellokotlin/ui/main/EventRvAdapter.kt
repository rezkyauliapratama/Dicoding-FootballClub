package android.rezkyaulia.com.hellokotlin.ui.main

import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.Util.TimeUtility
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.databinding.ListItemEventBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Rezky Aulia Pratama on 20/8/18.
 */
class EventRvAdapter(private val listItem: List<Event>, private val timeUtility: TimeUtility, private val clickListener : (String) -> Unit) : RecyclerView.Adapter<EventRvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listItem.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listItem[position],timeUtility, clickListener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var listItemEventBinding :ListItemEventBinding = ListItemEventBinding.bind(itemView)

        fun bindItem(event: Event,timeUtility: TimeUtility, clickListener: (String) -> Unit){

            val date = timeUtility.convertStringToDate(event.dateEvent!!)
            val strDate =  timeUtility.getUserFriendlyDate(date)

            listItemEventBinding.setVariable(BR.date,strDate)
            listItemEventBinding.setVariable(BR.event,event)
            listItemEventBinding.executePendingBindings()
            listItemEventBinding.root.setOnClickListener{
                clickListener(event.idEvent!!)
            }
        }
    }


}