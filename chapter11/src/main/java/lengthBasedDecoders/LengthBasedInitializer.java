package lengthBasedDecoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(64 * 1024, 0, 8))
                .addLast(new FrameHandler());
    }

    private static class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        }
    }
}
