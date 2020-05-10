package com.epichust;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HttpClientPostMain
{
    public static void main(String[] args) throws IOException, URISyntaxException
    {
        CloseableHttpClient client = HttpClients.createDefault();
        String postUrl = "http://localhost:8182/print-proxy/printer/print";
        HttpPost post = new HttpPost(postUrl);
        List<NameValuePair> list = new LinkedList<NameValuePair>();
        BasicNameValuePair param1 = new BasicNameValuePair("barCodeZpl", URLEncoder.encode("AAAAAAAAAAA","UTF-8"));
        BasicNameValuePair param2 = new BasicNameValuePair("printerStr", URLEncoder.encode("BBBBBBBBBBBB","UTF-8"));
        list.add(param1);
        list.add(param2);
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        post.setEntity(entityParam);

        CloseableHttpResponse response = client.execute(post);
        String str = EntityUtils.toString(response.getEntity());
        System.out.println(str);
    }
}
