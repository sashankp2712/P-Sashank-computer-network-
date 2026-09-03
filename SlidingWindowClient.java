import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Experiment 35: Implementation of Sliding Window Protocol - Client Component
 */
public class SlidingWindowClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 5000;
    private static final int TOTAL_PACKETS = 8;
    private static final int WINDOW_SIZE = 4;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("             Sliding Window Client                ");
        System.out.println("==================================================");

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to Sliding Window Server.");
            System.out.println("Window Size: " + WINDOW_SIZE + ", Total Packets: " + TOTAL_PACKETS);
            System.out.println("--------------------------------------------------");

            int base = 0;
            int nextSeqNum = 0;

            while (base < TOTAL_PACKETS) {
                // Send packets up to the window limit
                while (nextSeqNum < base + WINDOW_SIZE && nextSeqNum < TOTAL_PACKETS) {
                    System.out.println("Sending packet " + nextSeqNum);
                    out.println("PACKET " + nextSeqNum + " FrameData-" + nextSeqNum);
                    nextSeqNum++;
                }

                // Receive ACK from server
                String response = in.readLine();
                if (response != null && response.startsWith("ACK ")) {
                    int ackSeq = Integer.parseInt(response.split(" ")[1]);
                    System.out.println("ACK received for packet " + ackSeq);

                    if (ackSeq >= base) {
                        base = ackSeq + 1;
                        System.out.println("Window slid");
                        if (base < TOTAL_PACKETS) {
                            List<Integer> currentWindow = new ArrayList<>();
                            for (int i = base; i < Math.min(base + WINDOW_SIZE, TOTAL_PACKETS); i++) {
                                currentWindow.add(i);
                            }
                            System.out.println("Current window: " + currentWindow);
                        }
                    }
                }
            }

            out.println("BYE");
            System.out.println("--------------------------------------------------");
            System.out.println("All packets transmitted and acknowledged successfully.");
            System.out.println("==================================================");

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
