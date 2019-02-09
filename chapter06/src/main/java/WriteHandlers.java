import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

public class WriteHandlers {

    /**
     * This one causes a write event to flow all the way through pipeline
     */

    public static void writeViaChannel() {
        ChannelHandlerContext ctx = null; //.. from somewhere
        Channel channel = ctx.channel();
        channel.write(Unpooled.copiedBuffer("netty rocks", CharsetUtil.UTF_8));
    }

    /**
     * This one causes a write event to flow all the way through pipeline
     */

    public static void writeViaPipeline() {
        ChannelHandlerContext ctx = null; //.. from somewhere
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.write(Unpooled.copiedBuffer("netty rocks", CharsetUtil.UTF_8));
    }

    /**
     * This one causes a write to the next channel handler
     */

    public static void writeViaContext() {
        ChannelHandlerContext ctx = null; //.. from somewhere
        ctx.write(Unpooled.copiedBuffer("netty rocks", CharsetUtil.UTF_8));
    }
}
