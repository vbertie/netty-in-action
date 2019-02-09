import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.junit.Test;

import static io.netty.buffer.Unpooled.buffer;
import static junit.framework.TestCase.*;

public class InboundDecoderTest {

    @Test
    public void testFramesDecoded() throws IllegalAccessException {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 16; i++) {
            buf.writeDouble(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel ec = new EmbeddedChannel(
                new FixedLengthFrameDecoder(8)
        );

        //write bytes
        assertTrue(ec.writeInbound(input.retain()));
        assertTrue(ec.finish());

        //read messages
        for (int i=0;i<16; i++) {
            ByteBuf read = (ByteBuf) ec.readInbound();
            assertEquals(buf.readSlice(8), read);
            System.out.println(read.readDouble());
            read.release();
        }
        assertNull(ec.readInbound());
        buf.release();
    }

    @Test
    public void testFramesDecoded2() throws IllegalAccessException {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel ec = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3)
        );

        assertFalse(ec.writeInbound(input.readBytes(2)));
        assertTrue(ec.writeInbound(input.readBytes(7)));
        assertTrue(ec.finish());

        //read messages
        for (int i=0;i<3; i++) {
            ByteBuf read = (ByteBuf) ec.readInbound();
            assertEquals(buf.readSlice(3), read);
            System.out.println(read.readByte());
            read.release();
        }
        assertNull(ec.readInbound());
        buf.release();
    }
}
