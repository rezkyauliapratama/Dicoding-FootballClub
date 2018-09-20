package android.rezkyaulia.com.hellokotlin.ui.main.team.favoriteteam

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteEvent
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteTeam
import android.rezkyaulia.com.hellokotlin.databinding.ListItemFavoriteeventBinding
import android.rezkyaulia.com.hellokotlin.databinding.ListItemFavoriteteamBinding
import android.rezkyaulia.com.hellokotlin.databinding.ListItemTeamBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */

class FavoriteTeamRvAdapter(private val lifecycleOwner: LifecycleOwner,private val favoriteTeamViewModel: FavoriteTeamViewModel , private val clickListener: (FavoriteTeam) -> Unit) : RecyclerView.Adapter<FavoriteTeamRvAdapter.ViewHolder>() {
    private  val listItem: MutableList<FavoriteTeam> = mutableListOf()

    init {
        favoriteTeamViewModel.favTeamResponseLD.observe(lifecycleOwner, Observer {
            listItem.clear()
            if (it != null){
                listItem.addAll(it)
            }
            notifyDataSetChanged()
        })
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_favoriteteam, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listItem.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listItem[position], clickListener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var listItemFavoriteteamBinding  = ListItemFavoriteteamBinding.bind(itemView)

        fun bindItem(team: FavoriteTeam, clickListener: (FavoriteTeam) -> Unit){


            listItemFavoriteteamBinding.setVariable(BR.item,team)
            listItemFavoriteteamBinding.executePendingBindings()
            listItemFavoriteteamBinding.root.setOnClickListener{
                clickListener(team)
            }
        }
    }


}