package com.example.hbl.kotlin.coderead;

import android.content.Context;
import android.support.v4.graphics.ColorUtils;

import com.example.hbl.kotlin.dir.FileNode;
import com.loopeer.codereader.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;

public class HtmlParser {
    public static String buildHtmlContent(Context context, String code, String filepatch
            , String fileName) {
        for (; ; ) {
            try {
                InputStream inputStream = context.getAssets().open("code.html");
                byte[] localObject = new byte[inputStream.available()];
                inputStream.read( localObject);
                inputStream.close();
                String localHtml = new String(localObject);
                jsFile = localHtml
                        .replace("!FONT_SIZE!"
                                , String.format("<style>.code .syntaxhighlighter { font-size: %.2fpx !important; }</style>"
                                        , new Object[]{Float.valueOf(PrefUtils.getPrefFontSize(context))}))
                        .replace("!FILENAME!"
                                , fileName)
                        .replace("!BRUSHJSFILE!", jsFile)
                        .replace("!SYNTAXHIGHLIGHTER!"
                                , localStringBuilder.toString())
                        .replace("!JS_FIX_HSCROLL!", temp);
                temp = "<link type='text/css' rel='stylesheet' href='style_menlo.css'/>";
                return jsFile
                        .replace("!STYLE_MENLO!", PrefUtils.getPrefMenlofont(context) ? temp : "")
                        .replace("!THEME!", PrefUtils.getPrefTheme(context))
                        .replace("!CODE!", paramString1)

                        .replace("!WINDOW_BACK_GROUND_COLOR!"
                                , ColorUtils.getColorString(context, R.color.code_read_background_color));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {

            }
        }
    }
    public static String getFileCode(final FileNode node){
        Observable.just(node)
                .map
        }

    }
}
