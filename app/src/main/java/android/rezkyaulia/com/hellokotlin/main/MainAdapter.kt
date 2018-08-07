package android.rezkyaulia.com.hellokotlin.main

import android.rezkyaulia.com.hellokotlin.main.TeamUI.Ids.team_badge
import android.rezkyaulia.com.hellokotlin.main.TeamUI.Ids.team_name
import android.rezkyaulia.com.hellokotlin.data.Team
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */
class MainAdapter(private val teams: List<Team>)
    : RecyclerView.Adapter<TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position])

    }

}

class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val teamBadge: ImageView = itemView.find(team_badge)
    private val teamName: TextView = itemView.find(team_name)

    fun bindItem(teams: Team) {
        Picasso.get().load(teams.teamBadge).into(teamBadge)
        teamName.text = teams.teamName
    }

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = Ids.team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = Ids.team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }

    companion object Ids {
        val team_badge = 1
        val team_name = 2
    }

}