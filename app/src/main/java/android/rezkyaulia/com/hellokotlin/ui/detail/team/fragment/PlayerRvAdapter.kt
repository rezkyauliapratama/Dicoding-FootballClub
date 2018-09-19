package android.rezkyaulia.com.hellokotlin.ui.detail.team.fragment

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.data.model.Player
import android.rezkyaulia.com.hellokotlin.databinding.ListItemPlayerBinding
import android.rezkyaulia.com.hellokotlin.ui.detail.team.DetailTeamPlayerViewModel
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PlayerRvAdapter(lifecycleOwner: LifecycleOwner, detailTeamPlayerViewModel: DetailTeamPlayerViewModel, private val clickListener : (String) -> Unit) : RecyclerView.Adapter<PlayerRvAdapter.ViewHolder>() {

    val listItem : MutableList<Player> = mutableListOf()

    init {
        detailTeamPlayerViewModel.playersLD.observe(lifecycleOwner, Observer {
            listItem.clear()
            if (it != null){
                listItem.addAll(it)
            }
            notifyDataSetChanged()
        })
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_player, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listItem.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listItem[position], clickListener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val listItemPlayerBinding = ListItemPlayerBinding.bind(itemView)

        fun bindItem(player: Player, clickListener: (String) -> Unit){

            listItemPlayerBinding.setVariable(BR.item,player)
            listItemPlayerBinding.executePendingBindings()
            listItemPlayerBinding.root.setOnClickListener{
                clickListener(player.idPlayer)
            }
        }
    }


}