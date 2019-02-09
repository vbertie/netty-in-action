import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class FrameChunkDecoderTest {

    @Test
    public void frameChunkTest() {
        ByteBuf buf = Unpooled.buffer();
        for (int i=0; i<9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel ec = new EmbeddedChannel(
                new FrameChunkDecoder(3)
        );

        assertTrue(ec.writeInbound(input.readBytes(2)));
        try {
            ec.writeInbound(input.readBytes(4));
            Assert.fail();
        } catch (TooLongFrameException ex) {

        }
        assertTrue(ec.writeInbound(input.readBytes(3)));
        assertTrue(ec.finish());

        //read frames
        ByteBuf read = (ByteBuf) ec.readInbound();
        assertEquals(buf.readSlice(2), read);
        read.release();

        read = (ByteBuf) ec.readInbound();
        assertEquals(buf.skipBytes(4).readSlice(3), read);
        read.release();
        buf.release();
    }
}
