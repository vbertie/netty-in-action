import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel socket = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("localhost", 8080);

        socket.bind(address);
        socket.configureBlocking(false);
        socket.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            log("im server and im waiting for new connection");

            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while(iterator.hasNext()) {
                SelectionKey myKey = iterator.next();

                if (myKey.isAcceptable()) {
                    SocketChannel newClient = socket.accept();

                    newClient.configureBlocking(false);

                    newClient.register(selector, SelectionKey.OP_READ);
                    log("connection accepted for " + newClient.getLocalAddress());
                }
                else if (myKey.isReadable()) {
                    SocketChannel newClient = (SocketChannel) myKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    newClient.read(buffer);
                    String result = new String(buffer.array()).trim();

                    log("message received: " + result);

                    if (result.equals("done")) {
                        newClient.close();
                        log("connection closed");
                    }
                }
                iterator.remove();
            }
        }
    }
    private static void log(String str) {
        System.out.println(str);
    }
}
