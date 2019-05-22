package net.natura.minegocionatura.view.shareables.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.natura.minegocionatura.R
import net.natura.minegocionatura.model.prismic.Body
import net.natura.minegocionatura.model.prismic.Result
import net.natura.minegocionatura.model.prismic.Story
import net.natura.minegocionatura.view.shareable.EditShareableActivity
import net.natura.minegocionatura.view.shareable.ShareableActivity

class ShareablesAdapter2 : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var context: Context? = null
    private var stories: ArrayList<Result>? = ArrayList()

    companion object {
        const val EDITABLE_CATEGORY = "Editables"
    }

    constructor (context: Context) {
        this.context = context
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_shareable_preview, viewGroup, false))
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val story: Story = stories!![i].story
        story.id = stories!![i].storyId
        bindShareable(viewHolder as ViewHolder, story)
    }

    override fun getItemCount(): Int {
        return stories!!.size
    }

    fun addStories(stories: List<Result>) {
        this.stories!!.addAll(stories)
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var llShareableContainer: CardView? = itemView.findViewById(R.id.llShareableContainer)
        var ivShareable: ImageView? = itemView.findViewById(R.id.ivShareable)
        var tvTitle: TextView? = itemView.findViewById(R.id.tvTitle)
        var vPlayButton: ImageView? = itemView.findViewById(R.id.vPlayButton)

    }

    private fun bindShareable(viewHolder: ViewHolder, shareable: Story) {
        val context = viewHolder.itemView.context

        viewHolder.tvTitle?.text = shareable.title

        Picasso
                .get()
                .load(shareable.thumbnail.imageUrl)
                .fit()
                .into(viewHolder.ivShareable)

        Picasso.get().isLoggingEnabled = true

        if (shareable.body[0].sliceType == Body.TYPE_VIDEO) {
            viewHolder.vPlayButton?.apply {
                visibility = View.VISIBLE
                isClickable = true
                setOnClickListener { ShareableActivity.startShareableActivity(context, shareable) }
            }
        } else {
            viewHolder.vPlayButton!!.visibility = View.GONE
        }

        viewHolder.llShareableContainer?.setOnClickListener {
            if (shareable.category == EDITABLE_CATEGORY && shareable.body.size == 1 && shareable.body[0].sliceType == Body.TYPE_IMAGE) {
                EditShareableActivity.startActivity(context, shareable)
            } else {
                ShareableActivity.startShareableActivity(context, shareable)
            }
        }
    }
}