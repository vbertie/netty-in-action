package httpAndHttps;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpCompressionInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline cp = channel.pipeline();
        if (isClient) {
            cp.addLast("codec", new HttpClientCodec())
                    .addLast("decompressor", new HttpContentDecompressor());
        } else {
            cp.addLast("codec", new HttpServerCodec())
                    .addLast("compressor", new HttpContentCompressor());
        }
    }
}
