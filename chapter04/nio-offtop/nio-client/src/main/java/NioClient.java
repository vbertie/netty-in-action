import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NioClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8080);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(inetSocketAddress);

        log("connected to server on port 8080");

        Scanner scanner = new Scanner(System.in);
        String in = "";
        do {
            in = scanner.nextLine();
            byte[] bytes = in.getBytes();
            ByteBuffer message = ByteBuffer.wrap(bytes);
            socketChannel.write(message);
            message.clear();
        } while (!in.equals("done"));
        socketChannel.close();
    }
    private static void log(String message) {
        System.out.println(message);
    }
}
