package com.epichust.main1.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class NioDemo
{
    public static void main(String[] args) throws Exception
    {
        List<SocketChannel> list = new ArrayList<>();

        //SocketChannel socketChannel = SocketChannel.open();
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(6667));

        //重点：这里设置了false，表示设置系统内核的读取为非阻塞
        //调用后面的accept()方法就不会阻塞，会立即返回
        ss.configureBlocking(false);

        while (true)
        {
            Thread.sleep(1000);

            //通过上面的ss.configureBlocking(false)，这里不会阻塞
            //无论有没有客户端连接，都会立即返回，NIO的非阻塞就是体现在这里
            //没有客户端连接，返回 null，系统内核是返回 -1
            SocketChannel socketChannel = ss.accept();
            if (socketChannel == null)
            {
                System.out.println("null ......");
            } else
            {
                //同理，这里设置了false，表示通道在读取数据的时候也不会被阻塞
                socketChannel.configureBlocking(false);
                int port = socketChannel.socket().getPort();
                System.out.println("client port:" + port);
                list.add(socketChannel);
            }

            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);

            for (SocketChannel channel : list)
            {
                //上面设置了client.configureBlocking(false)，这里不会被阻塞，会立即返回
                //有数据 num > 0，没有数据 num < 1
                int num = channel.read(buffer);
                if (num > 0)
                {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    System.out.println(new String(bytes));
                    buffer.clear();
                }
            }
        }
    }
}
