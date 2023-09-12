package Test;
import com.formdev.flatlaf.FlatDarkLaf;
import logic.LogicWelcome;
import ui.Welcome;

public class testWhitInterfaz {
    public static void main(String[] args) {

        FlatDarkLaf.setup();
        Welcome welcome = new Welcome();
        LogicWelcome logicWelcome = new LogicWelcome(welcome);
    }
}

