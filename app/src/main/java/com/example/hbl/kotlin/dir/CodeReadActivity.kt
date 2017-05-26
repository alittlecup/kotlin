package com.example.hbl.kotlin.dir

import android.os.Bundle
import android.os.Environment
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.hbl.kotlin.R
import com.example.hbl.kotlin.mvp.MVPActivity
import kotlinx.android.synthetic.main.activity_codereader.*
import kotlinx.android.synthetic.main.view_chooser_recycler.*
import java.io.File
import java.util.*

class CodeReadActivity() : MVPActivity<CodeReadPresenter>(),
        CodeReadContract.View,
        DirectoryFileAdapter.OnDirectoryClickListener,
        DirectoryFileAdapter.OnNodeSelectListener {


    val PATH_STACK_NAME_START_OFFSET = "  "
    val PATH_STACK_NAME_END_OFFSET = ">"

    val mSelectedNodeStack = LinkedList<FileNode>()

    lateinit var mDirectoryFileAdapter :DirectoryFileAdapter
    var currentSelectedNode:FileNode? = null
    override val mPresenter = CodeReadPresenter(this)

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.select_file)
        mDirectoryFileAdapter= DirectoryFileAdapter(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setUpRecyclerView()
    }

    fun setUpRecyclerView() {
        mDirectoryFileAdapter.setItemClickListener(this)
        mDirectoryFileAdapter.setNodeSelectListener(this)
        view_recycler.adapter=mDirectoryFileAdapter
        view_recycler.addItemDecoration(DividerItemDecorationChooser(this))
        updateDataWithNode(DirectoryUtils.getFileDirectory(Environment.getExternalStorageDirectory()))
    }

    private fun updateDataWithNode(node: FileNode) {
        val selectNode = DirectoryUtils.getFileDirectory(File(node.absolutePath))
        if(currentSelectedNode!=null){
            pushToSelectedStack(currentSelectedNode!!)
        }
        currentSelectedNode=selectNode
        updateLinkedClick()
        updateContent(selectNode)
    }

    private fun updateContent(selectNode: FileNode) {
        val nods = selectNode.childNodes
        if (nods == null || nods.isEmpty()) {
            showEmpty()
        } else {
            mDirectoryFileAdapter.updateData(nods)
            showContent()
        }
    }

    private fun showContent() {
        animator_recycler_content.displayedChild=0
    }

    private fun showEmpty() {
        animator_recycler_content.displayedChild=1
    }

    private fun showProgress() {
        animator_recycler_content.displayedChild=2
    }

    private fun updateLinkedClick() {
        val sb = StringBuilder()
        val count = mSelectedNodeStack.size
        val nodeStartPos = IntArray(count + 1)
        val nodeEndPos = IntArray(count + 1)
        for (i in count - 1 downTo 0) {
            val node = mSelectedNodeStack[i]
            appendPath(sb, nodeStartPos, nodeEndPos, node, count - i - 1)
        }
        appendPath(sb, nodeStartPos, nodeEndPos, currentSelectedNode!!, count)
        val spannableString = SpannableString(sb.toString())
        for (i in 0..count - 1) {
            val finalI = i
            spannableString.setSpan(object : ColorClickableSpan() {
                override fun onClick(view: View) {
                    popToPosition(finalI)
                }
            }, nodeStartPos[i], nodeEndPos[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        text_chooser_path.text=spannableString
        text_chooser_path.movementMethod=LinkMovementMethod.getInstance()
        scrollView_path.post({ scrollView_path.fullScroll(View.FOCUS_RIGHT) })
    }

    private fun popToPosition(finalI: Int) {
        var node: FileNode? = null
        for (i in mSelectedNodeStack.size downTo finalI + 1) {
            node = mSelectedNodeStack.pop()
        }
        currentSelectedNode = node
        updateLinkedClick()
        updateContent(node!!)
    }

    private fun appendPath(sb: StringBuilder, nodeStartPos: IntArray, nodeEndPos: IntArray, node: FileNode, i: Int) {
        val size = node.name!!.length + PATH_STACK_NAME_START_OFFSET.length + PATH_STACK_NAME_END_OFFSET.length
        nodeStartPos[i] = sb.length
        nodeEndPos[i] = sb.length + size
        sb.append(PATH_STACK_NAME_START_OFFSET)
        sb.append(node.name)
        sb.append(PATH_STACK_NAME_END_OFFSET)
    }
    private fun pushToSelectedStack(it: FileNode) {
        mSelectedNodeStack.push(it)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_codereader
    }


    override fun onDirectoryClick(node: FileNode) {
        if (node.isFolder) {
            updateDataWithNode(node)
        }
    }

    override fun onNodeSelected(node: FileNode) {
        //TODO
//        val intent = intent
//        intent.putExtra(NavigatorChooser.EXTRA_FILE_NODE, node)
//        setResult(Activity.RESULT_OK, intent)
//        finish()
    }

    override fun onBackPressed() {
        if (mSelectedNodeStack.isEmpty()) {
            super.onBackPressed()
        } else {
            popSelectedStack()
        }
    }

    private fun popSelectedStack() {
        currentSelectedNode = mSelectedNodeStack.pop()
        updateLinkedClick()
        updateContent(currentSelectedNode!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
