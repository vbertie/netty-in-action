import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class ChannelInitlializerExample {
    public void start() {
        ServerBootstrap b = new ServerBootstrap ();
        ChannelFuture cf = b.group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new HttpClientCodec())
                                .addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
                    }
                }).bind(new InetSocketAddress(8080));
        cf.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("successfully");
                } else {
                    System.out.println("failed");
                    channelFuture.cause().printStackTrace();;
                }
            }
        });
    }
}
