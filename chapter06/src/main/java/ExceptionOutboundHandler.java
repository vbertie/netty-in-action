import io.netty.channel.*;
import io.netty.channel.ChannelFuture;

public class ExceptionOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        //1st approach
        ChannelFuture cf = ctx.write(msg);
        cf.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    channelFuture.cause().printStackTrace();
                    channelFuture.channel().close();
                }
            }
        });

        //2nd approach
        promise.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("sent correctly");
                }
            }
        });
    }
}
