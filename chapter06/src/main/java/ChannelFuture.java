import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.channels.Channel;

public class ChannelFuture {
    private static final SocketChannel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final ByteBuf SOME_MSG_FROM_SOMEWHERE = Unpooled.buffer(1024);

    public static void addingChannelFutureListener(){
        SocketChannel channel = CHANNEL_FROM_SOMEWHERE; // get reference to pipeline;
        ByteBuf someMessage = SOME_MSG_FROM_SOMEWHERE; // get reference to pipeline;
        //...
        io.netty.channel.ChannelFuture future = channel.write(someMessage);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(io.netty.channel.ChannelFuture f) {
                if (!f.isSuccess()) {
                    f.cause().printStackTrace();
                    f.channel().close();
                }
            }
        });
    }
}
