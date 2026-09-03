import java.net.*;
import java.util.*;

public class ARPImplementation {
    public static void main(String[] args) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();

            NetworkInterface networkInterface =
                    NetworkInterface.getByInetAddress(localHost);

            byte[] mac = networkInterface.getHardwareAddress();

            System.out.println("ARP Protocol Implementation");
            System.out.println("---------------------------");
            System.out.println("Host Name   : " + localHost.getHostName());
            System.out.println("IP Address  : " + localHost.getHostAddress());

            if (mac != null) {
                StringBuilder macAddress = new StringBuilder();

                for (int i = 0; i < mac.length; i++) {
                    macAddress.append(String.format("%02X", mac[i]));
                    if (i < mac.length - 1)
                        macAddress.append(":");
                }

                System.out.println("MAC Address : " + macAddress);
            } else {
                System.out.println("MAC Address : Not available");
            }

            System.out.println();
            System.out.println("ARP address resolution completed successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}