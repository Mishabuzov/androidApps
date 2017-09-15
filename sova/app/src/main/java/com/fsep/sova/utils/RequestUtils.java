package com.fsep.sova.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RequestUtils {

    public static MultipartBody.Part getMultipartBody(String filePath) {
        File file = new File(filePath.toLowerCase());
        RequestBody requestFile = RequestBody.create(MediaType.parse(FileUtils.getMimeType(file)), file);
        return MultipartBody.Part.createFormData("file", convertToUTF8(file.getName()), requestFile);
    }

    private static String convertToUTF8(String fileName) {
        String file = fileName;
        try {
            file =  URLEncoder.encode(file, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return file;
    }
}
