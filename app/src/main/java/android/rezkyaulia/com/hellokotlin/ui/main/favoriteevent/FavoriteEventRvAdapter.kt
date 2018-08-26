package android.rezkyaulia.com.hellokotlin.ui.main.favoriteevent

import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.Util.TimeUtility
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteEvent
import android.rezkyaulia.com.hellokotlin.databinding.ListItemEventBinding
import android.rezkyaulia.com.hellokotlin.databinding.ListItemFavoriteeventBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */

class FavoriteEventRvAdapter(private val listItem: List<FavoriteEvent>, private val timeUtility: TimeUtility, private val clickListener : (FavoriteEvent) -> Unit) : RecyclerView.Adapter<FavoriteEventRvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_favoriteevent, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount() = listItem.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listItem[position],timeUtility, clickListener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        lateinit var listItemFavoriteeventBinding : ListItemFavoriteeventBinding

        init {
            listItemFavoriteeventBinding =  ListItemFavoriteeventBinding.bind(itemView)

        }
        fun bindItem(favoriteEvent: FavoriteEvent, timeUtility: TimeUtility, clickListener: (FavoriteEvent) -> Unit){


            listItemFavoriteeventBinding.setVariable(BR.event,favoriteEvent)
            listItemFavoriteeventBinding.executePendingBindings()
            listItemFavoriteeventBinding.root.setOnClickListener{
                clickListener(favoriteEvent)
            }
        }
    }


}