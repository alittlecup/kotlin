package com.example.hbl.kotlin.dir

import java.io.Serializable

class FileNode : Serializable {

    var name: String? = null
    var absolutePath: String? = null
    var time: String? = null
    var type: String? = null
    var size: Long = 0
    var remark: String? = null
    var isFolder: Boolean = false
    var childNodes: ArrayList<FileNode>?=null




}
