package com.example.hbl.kotlin.network.interceptor

import com.example.hbl.library.extensions.log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * Created by hbl on 2017/5/22.
 */
class LoggerInterceptor : Interceptor {
    val UTF8 = charset("UTF-8")
    val TAG = "GomeNetWork"
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            throw e
        }
        val tookNs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        log("================================================Request================================================")
        log("url : ${request.url()}")
        log("method : ${request.method()}")
        log("time : $tookNs ms")
        val requestBody = request.body()
        val hasRequestBody = request.body() != null
        val headers = request.headers()
        run {
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    log(name + ": " + headers.value(i))
                }
                i++
            }
        }
        if (hasRequestBody) {
            requestBody!!.contentType()?.log("Content-Type:  ${requestBody.contentType()}")
            if (requestBody.contentLength() != -1L) {
                log("Content-Length: ${requestBody.contentLength()}")
            }
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val contentType = requestBody.contentType()
            var charset = requestBody.contentType()?.charset(UTF8) ?: UTF8
            if (isPlaintext(buffer)) log(buffer.readString(charset))
        }
        log("================================================Response================================================")
        val headers1 = response.headers()
        run {
            var i = 0
            val count = headers1.size()
            while (i < count) {
                val name = headers1.name(i)
                log("$name :  ${headers1.value(i)}")
                i++
            }
        }
        val body = response.body()
        if (bodyEncode(headers1)) {
            log("====================================================End====================================================")
        } else {
            val source = body.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer()
            val contentType = body.contentType()
            val charset: Charset = contentType?.charset(UTF8) ?: UTF8
            if (!isPlaintext(buffer)) {
                log("====================================================End====================================================")
                return response
            }
            if (body.contentLength() != 0L) log(buffer.clone().readString(charset))
        }
        log("====================================================End====================================================")
        return response

    }

    fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val l = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, l)
            for (i in 0..15) {
                if (prefix.exhausted()) break
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) return false
            }
            return true
        } catch (e: EOFException) {
            return false
        }
    }

    private fun bodyEncode(heads: Headers): Boolean {
        val get = heads.get("Content-Encoding")
        return get != null && !get.equals("identity", ignoreCase = true)
    }

}