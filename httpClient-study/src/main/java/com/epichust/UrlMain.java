package com.epichust;

import java.io.*;
import java.net.*;

public class UrlMain
{
    public static void main(String[] args) throws IOException
    {
        URL url = new URL("http://localhost:8182/print-proxy/printer/print");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);//当前的连接可以从服务器读取内容, 默认是true

        OutputStream os = urlConnection.getOutputStream();
        os.write("barCodeZpl=barCodeZpl&".getBytes());
        os.write("printerStr=yyyy".getBytes());
        os.flush();

        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);
        String line = null;
        while((line = reader.readLine()) != null)
        {
            System.out.println(line);
        }
    }
}
