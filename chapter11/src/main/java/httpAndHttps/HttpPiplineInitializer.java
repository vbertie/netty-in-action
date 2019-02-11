package httpAndHttps;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpPiplineInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpPiplineInitializer(boolean client) {
        this.client = client;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client) {
            pipeline.addLast("decoder", new HttpResponseDecoder());
            pipeline.addLast("encoder", new HttpResponseEncoder());
        }
        else {
            pipeline.addLast("encoder", new HttpResponseEncoder());
            pipeline.addLast("decoder", new HttpResponseDecoder());
        }
    }
}
