import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            System.out.println("TCP File Server started...");
            System.out.println("Waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            File file = new File("sample.txt");

            FileInputStream fileInput = new FileInputStream(file);
            OutputStream output = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileInput.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            output.flush();

            System.out.println("File transferred successfully.");
            System.out.println("File sent: " + file.getName());

            fileInput.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}