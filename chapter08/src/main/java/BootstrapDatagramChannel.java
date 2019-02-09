import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class BootstrapDatagramChannel {
    public void start() throws InterruptedException {
        Bootstrap b = new Bootstrap();
        ChannelFuture cf = b.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(8080))
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket msg) throws Exception {
                        System.out.println(Unpooled.copiedBuffer(msg.getData())
                                                                    .toString(CharsetUtil.UTF_8));
                    }
                }).bind().sync();
        
    }
}
