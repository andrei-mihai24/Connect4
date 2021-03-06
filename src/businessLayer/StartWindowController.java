package businessLayer;

import dataLayer.GameSerialization;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Andrei on 20.05.2016.
 */
public class StartWindowController
{
    @FXML
    public Button newGameButton;
    public Button loadGameButton;
    @FXML
    public Button exitButton;

    private boolean newGame = true;
    private Integer gameToLoad;

    /**
     * Method executed when the EXIT button on the start window is clicked.
     * Terminates the process.
     */
    public void exitButtonClicked(ActionEvent event)
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Method executed when the New Game button on the start window is clicked.
     * A new window is loaded and opened.
     */
    public void newGameClicked(ActionEvent event)
    {
        newGame = true;
        Stage window = (Stage) newGameButton.getScene().getWindow();
        forwardToNewWidow(window, "/presentationLayer/mainWindow.fxml");
    }

    /**
     * Method executed when the Load Game button on the start window is clicked.
     * An existing game is opened and can be continued.
     */
    public void loadGameButtonClicked(ActionEvent event)
    {
        /*if (GameSerialization.getNumberOfSavedGames() != 0)
        {
            newGame = false;
            Stage window = (Stage) loadGameButton.getScene().getWindow();
            forwardToNewWidow(window, "/presentationLayer/mainWindow.fxml");
        } else
            NewAlert.display("Warning", "Could not load saved game", "There is no saved game. You have to start a new game!");*/

        if (GameSerialization.getNumberOfSavedGames() != 0)
        {
            ChooseSavedGame.display();
            if (ChooseSavedGame.getChosenGame() != null)
            {
                newGame = false;
                gameToLoad = ChooseSavedGame.getChosenGame();
                Stage window = (Stage) loadGameButton.getScene().getWindow();
                forwardToNewWidow(window, "/presentationLayer/mainWindow.fxml");
            }
        } else
            NewAlert.display("Warning", "Could not load saved game", "There is no saved game. You have to start a new game!");
    }

    private void forwardToNewWidow(Stage window, String fxmlFile)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            MainWindowController controller = loader.<MainWindowController>getController();
            controller.initialize(newGame, gameToLoad);

            window.setResizable(false);
            window.setScene(new Scene(root, 900, 700));
            window.show();
        } catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
