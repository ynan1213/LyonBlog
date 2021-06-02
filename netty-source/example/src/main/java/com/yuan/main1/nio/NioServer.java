package com.epichust.main1.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(1213));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("注册后的SelectionKey数量=" + selector.keys().size());

        while (true)
        {
            /**
             * int select()：阻塞到至少有一个通道在你注册的事件上就绪了。
             * int select(long timeout)：和select()一样，但最长阻塞时间为timeout毫秒。
             * int selectNow()：非阻塞，只要有通道就绪就立刻返回。
             *
             * 等待请求，每次等待阻塞3s，超过时间则向下执行，若传入0或不传值，则在接收到请求前一直阻塞
             */
            if (selector.select(3000) == 0)
            {
                continue;
            }
            /**
             * 一个SelectionKey键表示了一个特定的通道对象和一个特定的选择器对象之间的注册关系
             *
             * SelectionKey.attachment();   //返回SelectionKey的attachment，attachment可以在注册channel的时候指定。
             * SelectionKey.channel();      //返回该SelectionKey对应的channel。
             * SelectionKey.selector();     //返回该SelectionKey对应的Selector。
             * SelectionKey.interestOps();  //返回代表需要Selector监控的IO操作的bit mask
             * SelectionKey.readyOps();     //返回一个bit mask，代表在相应channel上可以进行的IO操作。
             */
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKey有事件的数量：" + selectionKeys.size());

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext())
            {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable())
                {
                    SocketChannel sockerChannel = serverSocketChannel.accept();
                    sockerChannel.configureBlocking(false);
                    sockerChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("注册的SelectionKey数量为" + selector.keys().size());
                }
                if (selectionKey.isReadable())
                {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("客户端数据：" + new String(buffer.array()));
                }
            }
            iterator.remove();
        }

    }
}
