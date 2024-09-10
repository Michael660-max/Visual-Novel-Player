import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println(Integer.parseInt(sc.nextLine()));
            int a = 12;
            System.out.println(a+12);
        } catch (NumberFormatException e) {
            System.out.println("Error " + e.getMessage());
        }


    }
}
