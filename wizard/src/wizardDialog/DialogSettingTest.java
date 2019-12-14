package wizardDialog;

import org.eclipse.jface.dialogs.DialogSettings;

import java.io.IOException;

public class DialogSettingTest {

    public static void main(String[] args) {
        DialogSettings settings = new DialogSettings("survey");
        settings.put("Q1", 0);
        settings.put("Q2", 1);
        settings.put("Q3", 2);
        try {
            settings.save("dialog.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        settings = new DialogSettings(null);
        try {
            settings.load("dialog.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Q1=" + settings.get("Q1"));
        System.out.println("Q2=" + settings.get("Q2"));
        System.out.println("Q3=" + settings.get("Q3"));
    }
}
