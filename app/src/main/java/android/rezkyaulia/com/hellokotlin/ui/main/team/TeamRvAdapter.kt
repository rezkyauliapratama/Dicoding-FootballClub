package android.rezkyaulia.com.hellokotlin.ui.main.team

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.data.model.Team
import android.rezkyaulia.com.hellokotlin.databinding.ListItemTeamBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TeamRvAdapter(lifecycleOwner: LifecycleOwner, teamViewModel: TeamViewModel, private val clickListener : (String) -> Unit) : RecyclerView.Adapter<TeamRvAdapter.ViewHolder>() {

    private val listItem : MutableList<Team> = mutableListOf()

    init {
        teamViewModel.teamsLD.observe(lifecycleOwner, Observer {
            listItem.clear()
            if (it != null){
                listItem.addAll(it)
            }
            notifyDataSetChanged()
        })
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_team, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listItem.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listItem[position], clickListener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var listItemTeamBinding = ListItemTeamBinding.bind(itemView)

        fun bindItem(team: Team, clickListener: (String) -> Unit){

            listItemTeamBinding.setVariable(BR.item,team)
            listItemTeamBinding.executePendingBindings()
            listItemTeamBinding.root.setOnClickListener{
                clickListener(team.teamId)
            }
        }
    }


}