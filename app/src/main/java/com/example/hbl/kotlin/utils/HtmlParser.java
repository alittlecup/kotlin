package com.example.hbl.kotlin.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class HtmlParser {
    public static String buildHtmlContent(Context context, String fileTypeName, String FileCode
            , String fileName) {
        for (; ; ) {
            try {
                InputStream inputStream = context.getAssets().open("code.html");
                byte[] localObject = new byte[inputStream.available()];
                inputStream.read( localObject);
                inputStream.close();
                String htmlCode = new String( localObject);
                String temp = htmlCode
                        .replace("!!FILE_BODY!!"
                                , FileCode)
                        .replace("!!EXT!!", "\'"+fileTypeName+"\'");
                return temp;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {

            }
        }
    }
}
