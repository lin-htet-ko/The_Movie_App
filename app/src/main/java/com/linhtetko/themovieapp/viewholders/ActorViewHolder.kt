package com.linhtetko.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linhtetko.themovieapp.data.vos.ActorVO
import com.linhtetko.themovieapp.utils.BASE_URL
import com.linhtetko.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_actor.view.*

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(actorVO: ActorVO) {
        Glide.with(itemView.context).load("$IMAGE_BASE_URL${actorVO.profile_path}")
            .into(itemView.ivBestActor)
        itemView.tvActorName.text = actorVO.name
    }
}