package com.allenai.expandablelistview.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onRequestComplete(String result);
    }

    public static void doGetAsyn(final String urlStr, final CallBack callBack) {

        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);

                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * Get请求， 获得返回Raw Data
     *
     * @param urlStr
     * @return
     */
    public static String doGet(String urlStr) {

        URL url = null;

        HttpURLConnection     connection = null;
        InputStream           is         = null;
        ByteArrayOutputStream baos       = null;

        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT_IN_MILLIONS);
            connection.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");

            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;

                byte[] buf = new byte[1024];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }

                baos.flush();

                return baos.toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {

            }

            try {
                if (baos != null)
                    baos.close();

            } catch (IOException e) {

            }


            try {
                if (connection != null)
                    connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}