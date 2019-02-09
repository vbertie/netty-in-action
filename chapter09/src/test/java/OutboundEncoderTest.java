import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class OutboundEncoderTest {

    @Test
    public void testEncoder() {
        ByteBuf b = Unpooled.buffer();

        for (int i=1; i<10; i++) {
            b.writeInt(i * -1);
        }
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new AbsIntegerEncoder()
        );

        assertTrue(embeddedChannel.writeOutbound(b));
        assertTrue(embeddedChannel.finish());

        //read
        for (int i=1; i<10; i++) {
            assertEquals(i, embeddedChannel.readOutbound());
        }
    }
}
