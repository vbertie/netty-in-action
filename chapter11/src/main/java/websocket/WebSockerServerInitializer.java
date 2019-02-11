package websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSockerServerInitializer extends ChannelInitializer<Channel> {
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(
                        new HttpServerCodec(),
                        new HttpObjectAggregator(65536),
                        new WebSocketServerProtocolHandler("/websocket"),
                        new TextFrameHandler(),
                        new BinaryFrameHandler(),
                        new ContinuationFrameHandler());
    }

    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
            //handle text message
        }
    }

    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {
            // hnadle binary message
        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationFrameHandler> {

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ContinuationFrameHandler continuationFrameHandler) throws Exception {
            //handle continuation frame
        }
    }
}
