package com.example.gantask
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_grid.view.*
import model.BreakingBadModelItem
import utils.Metrics
import java.util.*
import kotlin.collections.ArrayList


class MainAdapter(private var characters: ArrayList<BreakingBadModelItem>, context: Context) : RecyclerView.Adapter<MainAdapter.DataViewHolder>(), Filterable {

    var characterFilterList = ArrayList<BreakingBadModelItem>()
    var mContext: Context

    init {
        characterFilterList = characters
        mContext = context
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: BreakingBadModelItem) {
            itemView.apply {
                textCharacterName.setText(character.name)
                Picasso.get()
                    .load(character.img)
                    .resize(Metrics.convertDpToPixel(200f, context).toInt(),Metrics.convertDpToPixel(250f,context).toInt())
                    .centerInside()
                    .into(imageCharacter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_grid,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = characterFilterList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(characterFilterList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("DETAILS", characterFilterList[position])
            mContext.startActivity(intent)
        }
    }

    fun addChars(chars: List<BreakingBadModelItem>) {
        this.characterFilterList.apply {
            clear()
            addAll(chars)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    characterFilterList = characters
                } else {
                    val resultList = ArrayList<BreakingBadModelItem>()
                    for (row in characters) {
                        if(!charSearch.isDigitsOnly()){
                            if (row.name.toLowerCase(Locale.ROOT).contains(
                                    charSearch.toLowerCase(
                                        Locale.ROOT
                                    )
                                )) { resultList.add(row) }
                        }else{
                            if(row.appearance != null && charSearch.length == 1)
                            for (i in row.appearance){
                                if(charSearch.contains(i.toString())){ resultList.add(row) }
                            }
                        }
                    }
                    characterFilterList = resultList
                    Log.d("charfiltersize: ", characterFilterList.size.toString())
                }
                val filterResults = FilterResults()
                filterResults.values = characterFilterList
                return filterResults
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                for (i in characterFilterList){
                    Log.d("names: ", i.name)
                }
                characterFilterList = results?.values as ArrayList<BreakingBadModelItem>
                notifyDataSetChanged()
            }
        }
    }
}