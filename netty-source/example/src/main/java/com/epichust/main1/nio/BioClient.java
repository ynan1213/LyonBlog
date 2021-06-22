package com.epichust.main1.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClient
{
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 6666));

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String message = console.readLine();
        writer.write(message + "\n");
        writer.flush();

        String responseMsg = reader.readLine();
        System.out.println("收到了来自服务器的回复：" + responseMsg);

        writer.close();
    }
}
