import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;

public class ModyfyingHandlersOrder {

    private final static ChannelPipeline PIPELINE_FROM_SOMEWHERE = null;

    public void modify(){
        ChannelPipeline pipeline = PIPELINE_FROM_SOMEWHERE;
        DiscardOutboundHandler discardOutboundHandler = new DiscardOutboundHandler();
        DiscardHandler discardHandler = new DiscardHandler();
        RandomHandler randomHandler = new RandomHandler();

        pipeline.addLast("h1", discardHandler);
        pipeline.addFirst("h2", discardOutboundHandler);
        pipeline.addLast("h3", randomHandler);
        pipeline.remove("h2");
        pipeline.remove(randomHandler);
        pipeline.replace("h1", "h5", new RandomHandler());

    }
}

class RandomHandler extends ChannelHandlerAdapter {

}
