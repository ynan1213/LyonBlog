package com.epichust;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

public class HttpClientGetMain
{
    public static void main(String[] args) throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();
        String getUrl = "http://localhost:8181/print-proxy/printer/list";
        HttpPost post = new HttpPost(getUrl);
        CloseableHttpResponse response = client.execute(post);
        String str = EntityUtils.toString(response.getEntity());
        System.out.println(str);
    }
}
