import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Experiment 35: Implementation of Sliding Window Protocol - Server Component
 */
public class SlidingWindowServer {
    private static final int PORT = 5000;
    private static final int TOTAL_PACKETS = 8;
    private static final int WINDOW_SIZE = 4;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("             Sliding Window Server                ");
        System.out.println("==================================================");
        System.out.println("Sliding Window Server started...");
        System.out.println("Waiting for client...");

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket clientSocket = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            System.out.println("--------------------------------------------------");

            int expectedSeqNum = 0;

            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("BYE")) {
                    break;
                }

                if (line.startsWith("PACKET ")) {
                    String[] parts = line.split(" ", 3);
                    int seqNum = Integer.parseInt(parts[1]);
                    String data = parts.length > 2 ? parts[2] : "";

                    System.out.println("Received packet " + seqNum + " with data: \"" + data + "\"");

                    if (seqNum == expectedSeqNum) {
                        System.out.println("Sending ACK for packet " + seqNum);
                        out.println("ACK " + seqNum);
                        expectedSeqNum++;

                        List<Integer> rcvWindow = new ArrayList<>();
                        for (int i = expectedSeqNum; i < Math.min(expectedSeqNum + WINDOW_SIZE, TOTAL_PACKETS); i++) {
                            rcvWindow.add(i);
                        }
                        if (!rcvWindow.isEmpty()) {
                            System.out.println("Receiver window moved. Expecting window: " + rcvWindow);
                        } else {
                            System.out.println("Receiver window moved. All expected packets received.");
                        }
                    } else {
                        System.out.println("Duplicate or out-of-order packet. Sending cumulative ACK " + (expectedSeqNum - 1));
                        out.println("ACK " + (expectedSeqNum - 1));
                    }
                    System.out.println("--------------------------------------------------");
                }
            }

            System.out.println("All packets received successfully.");
            System.out.println("Sliding Window Server completed successfully.");
            System.out.println("==================================================");

        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
