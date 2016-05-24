import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/presentationLayer/startWindow.fxml"));
        stage.setTitle("Connect Four");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
