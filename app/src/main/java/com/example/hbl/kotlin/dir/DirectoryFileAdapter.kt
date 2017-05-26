package com.example.hbl.kotlin.dir

import android.content.Context
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hbl.kotlin.R
import com.example.hbl.kotlin.extensions.select

class DirectoryFileAdapter(context: Context) : RecyclerDirectoryBaseAdapter<FileNode>(context) {

    interface OnDirectoryClickListener {
        fun onDirectoryClick(node: FileNode)
    }

    interface OnNodeSelectListener {
        fun onNodeSelected(node: FileNode)
    }

    private var mItemClickListener: OnDirectoryClickListener? = null
    private var mNodeSelectListener: OnNodeSelectListener? = null

    fun setItemClickListener(itemClickListener: OnDirectoryClickListener) {
        mItemClickListener = itemClickListener
    }

    fun setNodeSelectListener(nodeSelectListener: OnNodeSelectListener) {
        mNodeSelectListener = nodeSelectListener
    }

    override fun bindView(var1: FileNode, var2: Int, var3: RecyclerView.ViewHolder) {
        if (var3 is DirectoryFileViewHolder) {
            with(var3) {
                bind(var1)
                itemView.setOnClickListener { mItemClickListener!!.onDirectoryClick(var1) }
                mSelectBtn.setOnClickListener { mNodeSelectListener!!.onNodeSelected(var1) }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.list_item_directory_chooser, parent, false)
        return DirectoryFileViewHolder(view)
    }

    private inner class DirectoryFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         val mTextName: TextView
         val mSelectBtn: View

        init {
            mTextName = itemView.findViewById(R.id.text_directory_name) as TextView
            mSelectBtn = itemView.findViewById(R.id.btn_directory_select)
        }

        fun bind(nod: FileNode) {
            mTextName.text = nod.name
            val drawableId = select(nod.isFolder, R.drawable.ic_directory_path, R.drawable.ic_directory_file)
            val drawable = getDrawable(itemView.context, drawableId)
            mTextName.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }

    }

}
