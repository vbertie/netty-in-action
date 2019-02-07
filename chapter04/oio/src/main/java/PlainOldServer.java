import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class PlainOldServer {
    public void serve(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);

        try {
            for (;;) {
                final Socket client = serverSocket.accept();
                System.out.println(
                        "accepted connection from " + client);
                new Thread(new Runnable() {
                    public void run() {
                        OutputStream out;
                        try {
                            out = client.getOutputStream();
                            out.write("Hi!\r\n".getBytes(
                                    Charset.forName("UTF-8")));
                            out.flush();
                            client.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new PlainOldServer().serve(8090);
    }
}
