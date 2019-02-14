package delimiter.specificDelimiter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class CmdHandlerInitializer extends ChannelInitializer {
    public final byte SPACE = (byte) ' ';

    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new CmdDecoder(64 * 1024))
                .addLast(new CmdHandler());
    }

    public static final class Cmd {
        private final ByteBuf name;
        private final ByteBuf args;

        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf name() {
            return name;
        }

        public ByteBuf args() {
            return args;
        }
    }

    public static class CmdDecoder extends LineBasedFrameDecoder {
        private byte SPACE = (byte) ' ';

        public CmdDecoder(int maxLength) {
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
            if (frame == null) {
                return null;
            }
            int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), SPACE);

            return new Cmd(frame.slice(frame.readerIndex(), index), frame.slice(index + 1, frame. writerIndex()));
        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd> {
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Cmd cmd) throws Exception {

        }
    }
}
