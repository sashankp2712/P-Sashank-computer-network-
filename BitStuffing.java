public class BitStuffing {

    public static String bitStuff(String data) {
        StringBuilder result = new StringBuilder("01111110");
        int count = 0;

        for (char bit : data.toCharArray()) {
            result.append(bit);

            if (bit == '1') {
                count++;

                if (count == 5) {
                    result.append('0');
                    count = 0;
                }
            } else {
                count = 0;
            }
        }

        result.append("01111110");
        return result.toString();
    }

    public static void main(String[] args) {

        String data = "011111101111110";

        System.out.println("Bit Stuffing Mechanism");
        System.out.println("----------------------");
        System.out.println("Input Data   : " + data);
        System.out.println("Flag         : 01111110");
        System.out.println("Stuffed Data : " + bitStuff(data));
        System.out.println("Rule         : Insert 0 after five consecutive 1s");
    }
}