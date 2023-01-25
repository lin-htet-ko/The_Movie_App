package com.linhtetko.themovieapp.viewpods

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.linhtetko.themovieapp.adapters.ActorAdapter
import com.linhtetko.themovieapp.data.vos.ActorVO
import kotlinx.android.synthetic.main.view_pod_actor_list.view.*

class ActorListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    lateinit var mActorAdapter: ActorAdapter

    override fun onFinishInflate() {
        setUpActorRecyclerView()
        super.onFinishInflate()
    }

    fun setUpViewPod(backgroundColor: Int, titleText: String, moreText: String){
        setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
        tvBestActors.text = titleText
        tvMoreActors.text = moreText
        tvMoreActors.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    fun setData(actors: List<ActorVO>){
        mActorAdapter.setData(actors)
    }

    private fun setUpActorRecyclerView() {
        mActorAdapter = ActorAdapter()
        rvActorList.adapter = mActorAdapter
    }
}