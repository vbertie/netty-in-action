package httpAndHttps;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class HttpsCodecInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;
    private final SslContext context;

    public HttpsCodecInitializer(boolean isClient, SslContext context) {
        this.isClient = isClient;
        this.context = context;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline cp = channel.pipeline();
        SSLEngine engine = context.newEngine(channel.alloc());
        cp.addLast("ssl", new SslHandler(engine));

        if (isClient) {
                cp.addLast("codec", new HttpClientCodec());
        } else {
            cp.addLast("codec", new HttpServerCodec());
        }
    }
}
