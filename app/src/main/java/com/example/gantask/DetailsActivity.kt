package com.example.gantask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_breaking_bad_details.*
import model.BreakingBadModelItem
import utils.Metrics

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breaking_bad_details)
        val model: BreakingBadModelItem? = intent.getParcelableExtra("DETAILS")
        toolbar.setNavigationOnClickListener { finish() }
        Picasso.get()
            .load(model?.img)
            .resize(Metrics.convertDpToPixel(200F, this).toInt(),Metrics.convertDpToPixel(250f,this).toInt())
            .centerInside()
            .into(imageView)
        textViewName.text = getString(R.string.name, model?.name)
        textViewStatus.text = getString(R.string.status, model?.status)
        textViewNickname.text =  getString(R.string.nickname, model?.nickname)
        for (i in model?.occupation!!){
            textViewOccupation.text =  getString(R.string.occupation, model.occupation.joinToString())
        }
        if(model.appearance != null)
        for (i in model.appearance){
            textViewSeason.text = getString(R.string.appereance, model.appearance.joinToString())
        }
    }
}