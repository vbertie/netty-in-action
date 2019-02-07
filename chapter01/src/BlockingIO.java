import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingIO {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8080)){
            Socket clientSocket = serverSocket.accept();
            BufferedReader bufferedReader
                    = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out
                    = new PrintWriter(clientSocket.getOutputStream(), true);
            String request, response;
            while ((request = bufferedReader.readLine()) != null) {
                if ("Done".equals(request)) {
                    break;
                }
                response = processRequest(request);
                out.println(response);
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processRequest(String request) {
        return request.toUpperCase();
    }
}
