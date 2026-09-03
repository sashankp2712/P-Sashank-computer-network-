import java.io.*;
import java.net.*;

public class FileClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            System.out.println("Connected to File Server.");

            InputStream input = socket.getInputStream();
            FileOutputStream fileOutput =
                    new FileOutputStream("received_sample.txt");

            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytes = 0;

            while ((bytesRead = input.read(buffer)) != -1) {
                fileOutput.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }

            fileOutput.close();
            socket.close();

            System.out.println("File received successfully.");
            System.out.println("Saved as: received_sample.txt");
            System.out.println("Total bytes received: " + totalBytes);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}