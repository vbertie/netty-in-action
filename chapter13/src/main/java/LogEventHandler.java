import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             LogEvent event) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(event.getReceived());
        builder.append(" [");
        builder.append(event.getSource().toString());
        builder.append("] [");
        builder.append(event.getMsg());
        System.out.println(builder.toString());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
