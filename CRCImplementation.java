import java.util.zip.CRC32;

/**
 * Experiment 34: Simulation of Error Detection Code - CRC in Java
 */
public class CRCImplementation {

    // Standard CRC-32 Generator Polynomial (IEEE 802.3 representation)
    private static final long POLYNOMIAL = 0xEDB88320L;
    private static final int BIT_LENGTH = 32;

    /**
     * Calculates CRC-32 checksum using explicit bitwise XOR and shift operations.
     *
     * @param data byte array of input data
     * @return 32-bit CRC checksum value
     */
    public static long calculateCRC(byte[] data) {
        long crc = 0xFFFFFFFFL;
        for (byte b : data) {
            crc ^= (b & 0xFF);
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) != 0) {
                    crc = (crc >>> 1) ^ POLYNOMIAL;
                } else {
                    crc = crc >>> 1;
                }
            }
        }
        return (crc ^ 0xFFFFFFFFL) & 0xFFFFFFFFL;
    }

    /**
     * Calculates expected CRC-32 checksum using Java standard library.
     *
     * @param data byte array of input data
     * @return 32-bit CRC checksum value
     */
    public static long getExpectedCRC(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("         CRC Error Detection Simulation           ");
        System.out.println("==================================================");
        System.out.println("Generator Polynomial : 0x" + Long.toHexString(POLYNOMIAL).toUpperCase());
        System.out.println("Polynomial Bit Length: " + BIT_LENGTH + "-bit (CRC-32)");
        System.out.println("--------------------------------------------------");

        // 1. Original Test Data
        String originalData = "Computer Networks Lab Experiment 34";
        byte[] originalBytes = originalData.getBytes();

        System.out.println("\n[1] Original Data Verification:");
        System.out.println("Original Data       : \"" + originalData + "\"");

        long calculatedCRC = calculateCRC(originalBytes);
        long expectedCRC = getExpectedCRC(originalBytes);

        String calcHex = String.format("0x%08X", calculatedCRC);
        String expHex = String.format("0x%08X", expectedCRC);

        System.out.println("Calculated CRC      : " + calcHex);
        System.out.println("Expected CRC        : " + expHex);

        if (calculatedCRC == expectedCRC) {
            System.out.println("CRC Comparison      : MATCH (Calculated == Expected)");
            System.out.println("CRC Verification    : VALID");
        } else {
            System.out.println("CRC Comparison      : MISMATCH");
            System.out.println("CRC Verification    : INVALID");
        }

        // 2. Corrupted Data Verification
        System.out.println("\n[2] Corrupted Data Verification:");
        char[] corruptedChars = originalData.toCharArray();
        corruptedChars[9] = (corruptedChars[9] == 'N') ? 'X' : 'N'; // Modify 'N' in 'Networks'
        String corruptedData = new String(corruptedChars);
        byte[] corruptedBytes = corruptedData.getBytes();

        System.out.println("Corrupted Data      : \"" + corruptedData + "\"");
        long corruptedCRC = calculateCRC(corruptedBytes);
        String corrHex = String.format("0x%08X", corruptedCRC);
        System.out.println("Recalculated CRC    : " + corrHex);

        if (corruptedCRC == calculatedCRC) {
            System.out.println("CRC Verification    : VALID");
        } else {
            System.out.println("CRC Verification    : ERROR DETECTED");
        }

        System.out.println("\n--------------------------------------------------");
        System.out.println("CRC simulation completed successfully.");
        System.out.println("==================================================");
    }
}
