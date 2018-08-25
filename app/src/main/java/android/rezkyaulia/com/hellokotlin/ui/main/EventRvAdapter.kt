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
class EventRvAdapter(private val listItem: List<Event>, private val timeUtility: TimeUtility, private val clickListener : (Event) -> Unit) : RecyclerView.Adapter<EventRvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_event, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount() = listItem.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listItem[position],timeUtility, clickListener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var listItemEventBinding :ListItemEventBinding

        init {
            listItemEventBinding =  ListItemEventBinding.bind(itemView)

        }
        fun bindItem(event: Event,timeUtility: TimeUtility, clickListener: (Event) -> Unit){

                if (event?.dateEvent != null){
                    val date = timeUtility.convertStringToDate(event!!.dateEvent!!)
                    val strDate = date?.let { timeUtility.getUserFriendlyDate(it) }
                    event?.dateEvent = strDate
                }


            listItemEventBinding.setVariable(BR.event,event)
            listItemEventBinding.executePendingBindings()
            listItemEventBinding.root.setOnClickListener{
                clickListener(event)
            }
        }
    }


}