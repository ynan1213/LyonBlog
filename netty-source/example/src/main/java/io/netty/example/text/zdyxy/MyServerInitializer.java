package io.netty.example.text.zdyxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class MyServerInitializer extends ChannelInitializer
{
    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        // ChannelPipeline pipeline = ch.pipeline();
        //
        // pipeline.addLast(new MyPersonDecoder());
        // pipeline.addLast(new MyPersonEncoder());
        // pipeline.addLast(new MyServerHandler());
    }
}