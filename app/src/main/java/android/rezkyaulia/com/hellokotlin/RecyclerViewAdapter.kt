package android.rezkyaulia.com.hellokotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ListItemUi
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.AnkoContext
import org.w3c.dom.Text

class RecyclerViewAdapter(private val context: Context, private val items: List<Item>, private val listener: (Item) -> Unit)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemUi().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position],listener)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName : TextView
        val imageClub : ImageView

        init {
            textName = itemView.findViewById(ListItemUi.name)
            imageClub = itemView.findViewById(ListItemUi.image)
        }
        fun bindItem(item: Item, listener: (Item) -> Unit){
            textName.text = item.name
            Glide.with(itemView.context).load(item.image).into(imageClub)

            itemView.setOnClickListener { listener(item) }
        }
    }

}