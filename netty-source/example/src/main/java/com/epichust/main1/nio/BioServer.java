package com.epichust.main1.nio;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress((InetAddress)null, 6666));
        Socket socket = serverSocket.accept();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String message = reader.readLine();
        writer.write("你才是" + message + "\n");
        writer.flush(); //清理缓冲区
    }
}
