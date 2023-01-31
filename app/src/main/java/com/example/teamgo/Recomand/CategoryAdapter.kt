package com.example.teamgo.Recomand

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamgo.R

class CategoryAdapter( private val projectlist: List<category>) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    class MyViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(project: category) {
            val txtName = itemView.findViewById<TextView>(R.id.Pj_Cate_tv)
            val imgMore = itemView.findViewById<ImageButton>(R.id.More_btn)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)

            txtName.text = project.cate

            // 우측 버튼을 누르면 아래로 뷰 확장
            imgMore.setOnClickListener {
                val show = toggleLayout(!project.isExpanded, it, layoutExpand)
                project.isExpanded = show
            }
        }
        private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
            ToggleAnimation.toggleArrow(view, isExpanded)
            if (isExpanded) {
                ToggleAnimation.expand(layoutExpand)
            } else {
                ToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recommand_cate, parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(projectlist[position])
    }
    override fun getItemCount(): Int {
        return projectlist.size
    }
}