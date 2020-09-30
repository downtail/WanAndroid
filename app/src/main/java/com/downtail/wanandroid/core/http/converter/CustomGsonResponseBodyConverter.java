package com.downtail.wanandroid.core.http.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by wmq on 2018/1/23.
 */

class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
//        if (response.contains("\"data\":[]")) {
//            response = response.replace("\"data\":[]", "\"data\":null");
//        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(CustomGsonRequestBodyConverter.UTF_8) : CustomGsonRequestBodyConverter.UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } catch (Exception e) {
            if (e instanceof JsonSyntaxException) {
                String message = e.getMessage();
                if (!TextUtils.isEmpty(message)){
                    if (response.contains("[]") && message.contains("Expected a string but was BEGIN_ARRAY")) {
                        response = response.replace("[]", "\"\"");
                    } else if (response.contains("{}") && message.contains("Expected a string but was BEGIN_OBJECT")) {
                        response = response.replace("{}", "\"\"");
                    }
                }
            }
//            if (response.contains("{}")) {
//                response = response.replace("{}", "[]");
//            } else if (response.contains("[]")) {
//                response = response.replace("[]", "{}");
//            }

            InputStream is = new ByteArrayInputStream(response.getBytes());
            Reader isReader = new InputStreamReader(is, charset);
            JsonReader jr = gson.newJsonReader(isReader);
            return adapter.read(jr);
        } finally {
            value.close();
        }
    }
}
