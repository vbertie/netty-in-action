import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

public class AttributeExample {
    final AttributeKey<Integer> key = AttributeKey.newInstance("ID");
    public void start() {
        ServerBootstrap b = new ServerBootstrap ();
        ChannelFuture cf = b.group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addFirst(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                        Integer value = ctx.channel().attr(key).get();

                                        //..do something with value
                                    }
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                        System.out.println("received data");
                                    }
                                })
                                .addLast(new HttpClientCodec())
                                .addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
                    }
                })
                .attr(key, 12345
                ).bind(new InetSocketAddress(8080)).syncUninterruptibly();
    }
}
