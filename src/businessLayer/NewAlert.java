package businessLayer;

import javafx.scene.control.Alert;

/**
 * Created by Andrei on 22.05.2016.
 */
public class NewAlert
{
    public static void display(String title, String header, String context)
    {
        Alert warning = new Alert(Alert.AlertType.INFORMATION);
        warning.setTitle(title);
        warning.setHeaderText(header);
        warning.setContentText(context);
        warning.showAndWait();
    }
}
