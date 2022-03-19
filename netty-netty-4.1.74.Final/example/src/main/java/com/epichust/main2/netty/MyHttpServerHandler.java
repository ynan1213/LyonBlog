package com.epichust.main2.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.Set;

/**
 * pipeline中上一道handler是HttpServerCodec，它会将ByteBuf类型的mes进行解码然后和转换成HttpObject类型
 * 在这里就可以拿到关于http协议相关的数据
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject>
{
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception
    {
        System.out.println("实际类型:" + msg.getClass());
        if(msg instanceof HttpRequest)
        {
            //-------------------- 获取浏览器消息 --------------------
            HttpRequest request = (HttpRequest)msg;
            System.out.println("uri：" + request.uri());
            System.out.println("method：" + request.method().toString());
            HttpHeaders headers = request.headers();
            Set<String> names = headers.names();
            for (String name : names)
            {
                System.out.println("header【" + name + "：" + headers.get(name) + "】");
            }

            //-------------------- 返回浏览器消息 --------------------
            ByteBuf content = Unpooled.copiedBuffer("醉卧沙场君莫笑".toCharArray(), CharsetUtil.UTF_8);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
