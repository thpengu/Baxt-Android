package uz.karsoft.baxt.extensions.base

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.karsoft.baxt.extensions.textFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    var models: List<T> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @SuppressLint("NotifyDataSetChanged")
    fun update() {
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = models.size

    fun textFormat(t: String): String{
        var text = t
        text = text.reversed()
        text = text.subSequence(0, text.length)
            .chunked(3)
            .joinToString(" ")
        return text.reversed()
    }
    @SuppressLint("SimpleDateFormat")
    fun convertLongString(l: Long): String{
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(l))
    }

    @SuppressLint("SetTextI18n")
    fun startAnimationCounter(view: View, start: Long, end: Long, textView: TextView) {
        val animator = ValueAnimator.ofFloat(start.toFloat(), end.toFloat())
        animator.addUpdateListener {
            textView.text = (it.animatedValue as Float).toLong()
                .toString().textFormat
        }
        animator.duration = 1000
        animator.start()
    }
}