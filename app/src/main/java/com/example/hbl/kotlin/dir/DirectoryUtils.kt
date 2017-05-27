package com.example.hbl.kotlin.dir

import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import java.io.File
import java.util.*

object DirectoryUtils {
    private val EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE"

    fun hasSDCard(): Boolean {
        return Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
    }

    fun getRootNode(context: Context): FileNode? {
        if (hasSDCard() && hasExternalStoragePermission(context)) {
            return getFileDirectory(Environment.getExternalStorageDirectory())
        }
        return null
    }

    fun getFileDirectory( file: File): FileNode {
        val fileNod = FileNode()
        fileNod.name = file.name
        fileNod.absolutePath = file.absolutePath
        if (file.isDirectory) {
            fileNod.isFolder = true
            fileNod.childNodes = arrayListOf<FileNode>()
            for (childFile in file.listFiles()) {
                if (childFile!!.name.startsWith(".") || childFile.name.startsWith("_"))
                    continue
                val node = FileNode()
                node.name = childFile.name
                if (childFile.isDirectory) {
                    node.isFolder = true
                }
                node.absolutePath = childFile.absolutePath
                fileNod.childNodes!!.add(node)
            }
            if (!fileNod.childNodes!!.isEmpty()) {
                Collections.sort(fileNod.childNodes,{
                    o1, o2 ->
                    if(o1.isFolder&&!o2.isFolder){
                        return@sort -1
                    }
                    if(!o1.isFolder&&o2.isFolder){
                        return@sort 1
                    }
                    return@sort o1.name?.compareTo(o2.name!!)!!
                })
            }
        }
        return fileNod
    }

    fun hasExternalStoragePermission(context: Context): Boolean {
        val perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION)
        return perm == PackageManager.PERMISSION_GRANTED
    }
}