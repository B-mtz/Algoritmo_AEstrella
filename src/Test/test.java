package Test;

import com.formdev.flatlaf.FlatDarkLaf;
import ui.Welcome;

public class test {
    public static void main(String[] args) {
        FlatDarkLaf.setup();

        Welcome welcome = new Welcome();
    }
}
