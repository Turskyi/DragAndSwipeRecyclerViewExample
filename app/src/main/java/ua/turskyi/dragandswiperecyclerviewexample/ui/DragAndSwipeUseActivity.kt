package ua.turskyi.dragandswiperecyclerviewexample.ui

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_universal_recycler.*
import ua.turskyi.dragandswiperecyclerviewexample.R
import ua.turskyi.dragandswiperecyclerviewexample.ui.adapter.DragAndSwipeAdapter
import ua.turskyi.dragandswiperecyclerviewexample.utils.Tips
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_universal_recycler) {
    companion object {
        const val TAG = "DRAG_LOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mAdapter = initView()
        initListeners(mAdapter)
    }

    private fun initListeners(mAdapter: DragAndSwipeAdapter) {
        val listener: OnItemDragListener = object : OnItemDragListener {
            override fun onItemDragStart(viewHolder: ViewHolder, pos: Int) {
                Log.d(TAG, "drag start")
                val holder = viewHolder as BaseViewHolder
                val startColor = Color.WHITE
                val endColor = Color.rgb(245, 245, 245)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val valueAnimator = ValueAnimator.ofArgb(startColor, endColor)
                    valueAnimator.addUpdateListener { animation ->
                        holder.itemView.setBackgroundColor(animation.animatedValue as Int)
                    }
                    valueAnimator.duration = 300
                    valueAnimator.start()
                }
            }

            override fun onItemDragMoving(
                source: ViewHolder,
                from: Int,
                target: ViewHolder,
                to: Int
            ) {
                Log.d(
                    TAG,
                    "move from: " + source.adapterPosition + " to: " + target.adapterPosition
                )
            }

            override fun onItemDragEnd(viewHolder: ViewHolder, pos: Int) {
                Log.d(TAG, "drag end")
                val holder = viewHolder as BaseViewHolder
                val startColor = Color.rgb(245, 245, 245)
                val endColor = Color.WHITE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val valueAnimator = ValueAnimator.ofArgb(startColor, endColor)
                    valueAnimator.addUpdateListener { animation ->
                        holder.itemView.setBackgroundColor(
                            animation.animatedValue as Int
                        )
                    }
                    valueAnimator.duration = 300
                    valueAnimator.start()
                }
            }
        }

        val onItemSwipeListener: OnItemSwipeListener = object : OnItemSwipeListener {
            override fun onItemSwipeStart(viewHolder: ViewHolder, pos: Int) {
                Log.d(TAG, "view swiped start: $pos")
                val holder = viewHolder as BaseViewHolder
            }

            override fun clearView(viewHolder: ViewHolder, pos: Int) {
                Log.d(TAG, "View reset: $pos")
                val holder = viewHolder as BaseViewHolder
            }

            override fun onItemSwiped(viewHolder: ViewHolder, pos: Int) {
                Log.d(TAG, "View Swiped: $pos")
            }

            override fun onItemSwipeMoving(
                canvas: Canvas,
                viewHolder: ViewHolder,
                dX: Float,
                dY: Float,
                isCurrentlyActive: Boolean
            ) {
                canvas.drawColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.color_light_blue
                    )
                )
            }
        }

        mAdapter.draggableModule.setOnItemDragListener(listener)
        mAdapter.draggableModule.setOnItemSwipeListener(onItemSwipeListener)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            Tips.show("position：$position")
        }
    }

    private fun initView(): DragAndSwipeAdapter {
        title = "Drag And Swipe"
        rv.layoutManager = LinearLayoutManager(this)
        val mAdapter = DragAndSwipeAdapter()
        mAdapter.setList(generateData(50))
        mAdapter.draggableModule.isSwipeEnabled = true
        mAdapter.draggableModule.isDragEnabled = true
        mAdapter.draggableModule.itemTouchHelperCallback
            .setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
        mAdapter.draggableModule.itemTouchHelperCallback.setDragMoveFlags(
            ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN
        )
        rv.adapter = mAdapter
        return mAdapter
    }

    private fun generateData(size: Int): MutableList<String> {
        val data = ArrayList<String>(size)
        for (i in 0 until size) {
            data.add("item $i")
        }
        return data
    }
}