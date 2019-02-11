package decoder;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class MessageToMessageDecoder extends io.netty.handler.codec.MessageToMessageDecoder<Integer>{
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer integer, List<Object> list) throws Exception {
        list.add(String.valueOf(integer));
    }
}
