package com.example.hbl.kotlin.coderead

import android.text.TextUtils
import com.example.hbl.kotlin.andex.ext
import com.example.hbl.kotlin.dir.FileNode
import com.example.hbl.kotlin.extensions.log
import com.example.hbl.kotlin.extensions.select
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*

/**
 * Created by hbl on 2017/5/26.
 */
class HtmlParser {
    companion object {
        fun getFileCode(node: FileNode): String? {
            Observable.just(node)
                    .filter { !isBlackFile(ext(node.name!!)) }
                    .map {
                        var fileInputStream:FileInputStream?=null
                        try {
                            fileInputStream = FileInputStream(it.absolutePath)
                        } catch (e: FileNotFoundException) {
                            log(e.message!!)
                        }
//                        var finalStream= select(fileInputStream==nul)
                        val sb = StringBuilder()
                        val bufferedReader = BufferedReader(InputStreamReader(, "UTF-8"))
                        while (true) {
                            bufferedReader.readLine()?.let { sb.append(it).append("\n") } ?: break
                        }
                        bufferedReader.close()
                        TextUtils.htmlEncode(sb.toString())
                    }
                    .map


        }

        val FILE_BLACKLIST = arrayOf("hprof", "apk", "jar", "so")

        fun isBlackFile(name: String?): Boolean {
            if (name != null) return Arrays.asList(*FILE_BLACKLIST).contains(name.toLowerCase())
            return false
        }
    }
}