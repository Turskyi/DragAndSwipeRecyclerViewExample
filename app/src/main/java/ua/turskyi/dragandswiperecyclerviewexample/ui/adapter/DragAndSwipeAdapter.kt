package ua.turskyi.dragandswiperecyclerviewexample.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import ua.turskyi.dragandswiperecyclerviewexample.R

class DragAndSwipeAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_draggable_view,  mutableListOf()),
    DraggableModule {

    override fun convert(holder: BaseViewHolder, item: String) {
        when (holder.layoutPosition % 3) {
            0 -> holder.setImageResource(R.id.iv_head, R.mipmap.head_img0)
            1 -> holder.setImageResource(R.id.iv_head, R.mipmap.head_img1)
            2 -> holder.setImageResource(R.id.iv_head, R.mipmap.head_img2)
            else -> {
            }
        }
        holder.setText(R.id.tv, item)
    }
}