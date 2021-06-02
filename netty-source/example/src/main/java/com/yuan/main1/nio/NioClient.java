package com.epichust.main1.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient
{
    public static void main(String[] args) throws IOException
    {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if(!socketChannel.connect(socketAddress))
        {
            while (!socketChannel.finishConnect())
            {
                System.out.println("未连接到");
            }
        }

        String str = "hello,世界";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
