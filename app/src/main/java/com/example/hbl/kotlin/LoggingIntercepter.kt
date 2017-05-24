package com.example.hbl.kotlin


import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

/**
 * com.gomejr.myfangagent.core.network.interceptor.LoggingIntercepter

 * @author Just.T
 * *
 * @since 17/1/12
 */
class LoggingIntercepter : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        log("================================================Request================================================")
        log("url : " + request.url())
        log("method : " + request.method())
        log("time :" + tookMs + "ms")
        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        val requestHeaders = request.headers()
        run {
            var i = 0
            val count = requestHeaders.size()
            while (i < count) {
                val name = requestHeaders.name(i)
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    log(name + ": " + requestHeaders.value(i))
                }
                i++
            }
        }
        if (hasRequestBody) {
            if (requestBody!!.contentType() != null) {
                log("Content-Type: " + requestBody.contentType())
            }
            if (requestBody.contentLength() != -1L) {
                log("Content-Length: " + requestBody.contentLength())
            }
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            //编码设为UTF-8
            var charset = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            if (isPlaintext(buffer)) {
                log(buffer.readString(charset))
            }

        }
        log("================Response================")
        val headers = response.headers()
        var i = 0
        val count = headers.size()
        while (i < count) {
            log(headers.name(i) + ": " + headers.value(i))
            i++
        }
        val responseBody = response.body()
        val contentLength = responseBody.contentLength()


        if (bodyEncoded(response.headers())) {
            log("==================================================End==================================================")
        } else {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8)
                } catch (e: UnsupportedCharsetException) {
                    log("==================================================End==================================================")
                    return response
                }

            }

            if (!isPlaintext(buffer)) {
                log("==================================================End==================================================")
                return response
            }

            if (contentLength != 0L) {
                //获取Response的body的字符串 并打印
                log(buffer.clone().readString(charset))
            }
            log("==================================================End==================================================")
        }

        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    private fun log(log: String) {
//        Logger.e(TAG, log)
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
        private var TAG = "GomeNetwork"

        fun init(tag: String?) {
            if (tag != null && tag.length > 0)
                TAG = tag
        }

        @Throws(EOFException::class)
        internal fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false
            }

        }
    }
}
