package com.downtail.wanandroid.ui.browser;

import android.content.Intent;
import android.webkit.JavascriptInterface;

public class SonicJavaScriptInterface {

    public static final String PARAM_LOAD_URL_TIME = "loadUrlTime";
    private final SonicSessionClientImpl sessionClient;
    private final Intent intent;

    public SonicJavaScriptInterface(SonicSessionClientImpl sessionClient, Intent intent) {
        this.sessionClient = sessionClient;
        this.intent = intent;
    }

    @JavascriptInterface
    public void getData() {

    }

    /*
     * * From RFC 4627, "All Unicode characters may be placed within the quotation marks except
     * for the characters that must be escaped: quotation mark,
     * reverse solidus, and the control characters (U+0000 through U+001F)."
     */
    private static String toJsString(String value) {
        if (value == null) {
            return "null";
        }
        StringBuilder out = new StringBuilder(1024);
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);


            switch (c) {
                case '"':
                case '\\':
                case '/':
                    out.append('\\').append(c);
                    break;

                case '\t':
                    out.append("\\t");
                    break;

                case '\b':
                    out.append("\\b");
                    break;

                case '\n':
                    out.append("\\n");
                    break;

                case '\r':
                    out.append("\\r");
                    break;

                case '\f':
                    out.append("\\f");
                    break;

                default:
                    if (c <= 0x1F) {
                        out.append(String.format("\\u%04x", (int) c));
                    } else {
                        out.append(c);
                    }
                    break;
            }

        }
        return out.toString();
    }
}
