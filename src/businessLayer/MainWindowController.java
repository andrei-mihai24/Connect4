package businessLayer;

import dataLayer.Constants;
import dataLayer.Game;
import dataLayer.SavedGameSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Andrei on 20.05.2016.
 */
public class MainWindowController
{
    public Button mainWindowSaveExitButton;
    public Button mainWindowExitButton;
    public Button column1Button;
    public Button column2Button;
    public Button column3Button;
    public Button column4Button;
    public Button column5Button;
    public Button column6Button;
    public Button column7Button;
    public Button column1PopButton;
    public Button column2PopButton;
    public Button column3PopButton;
    public Button column4PopButton;
    public Button column5PopButton;
    public Button column6PopButton;
    public Button column7PopButton;
    public Label currentPlayerLabel;
    public GridPane gameBoard;

    private Game currentGame;
    private boolean saveButtonClicked;

    public void mainWindowButtonsClicked(ActionEvent event)
    {
        if (event.getSource() == mainWindowExitButton)
        {
            Stage stage = (Stage) mainWindowExitButton.getScene().getWindow();
            stage.close();
        } else
        {
            try
            {
                saveButtonClicked = true;
                SavedGameSingleton.getInstance().saveGame(currentGame);
                Stage stage = (Stage) mainWindowSaveExitButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentationLayer/startWindow.fxml"));
                Parent root = loader.load();

                StartWindowController controller = loader.<StartWindowController>getController();
                controller.setSavedGameExists(saveButtonClicked);

                stage.setTitle("Connect Four");
                stage.setScene(new Scene(root, 400, 400));
                stage.setResizable(false);
                stage.show();
            } catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public void mainWindowAddButtonClicked(ActionEvent event)
    {
        byte activePlayer = currentGame.getNextPlayer();
        if (event.getSource() == column1Button)
            updateBoardOnPush((byte) 0, activePlayer);
        else if (event.getSource() == column2Button)
            updateBoardOnPush((byte) 1, activePlayer);
        else if (event.getSource() == column3Button)
            updateBoardOnPush((byte) 2, activePlayer);
        else if (event.getSource() == column4Button)
            updateBoardOnPush((byte) 3, activePlayer);
        else if (event.getSource() == column5Button)
            updateBoardOnPush((byte) 4, activePlayer);
        else if (event.getSource() == column6Button)
            updateBoardOnPush((byte) 5, activePlayer);
        else
            updateBoardOnPush((byte) 6, activePlayer);
    }

    private void updateBoardOnPush(byte column, byte activePlayer)
    {
        String title, header, content;
        byte gameStatus = currentGame.addPiece(column, activePlayer);
        switch (gameStatus)
        {
            case Constants.COLUMN_FULL:
                title = "Warning Message";
                header = "Oops, no more space...";
                content = "Your token could not be added, because the column you chose is already full!";
                NewAlert.display(title,header,content);
                break;
            case Constants.GAME_OVER:
                addToken(column, currentGame.getRowOfLastAddedToken(), activePlayer);
                title = "Game Over";
                header = "Player " + currentGame.getPlayerWhoWon() + " has won!";
                content = "CONGRATULATIONS";
                NewAlert.display(title,header,content);
                forwardToStartWindow();
                break;
            case Constants.TOKEN_ADDED_GAME_NOT_OVER:
                addToken(column, currentGame.getRowOfLastAddedToken(), activePlayer);
                changeCurrentPlayer(currentGame.getNextPlayer());
                break;
        }
    }

    private void addToken(byte column, byte row, byte player)
    {
        Pane pane = new Pane();
        if (player == Constants.RED_PLAYER)
            pane.setStyle("-fx-background-color: " + Constants.RED_HEX + "; -fx-border-color: black;");
        else if (player == Constants.YELLOW_PLAYER)
            pane.setStyle("-fx-background-color: " + Constants.YELLOW_HEX + "; -fx-border-color: black;");
        else
            pane.setStyle("-fx-background-color: " + Constants.WHITE_HEX + "; -fx-border-color: black;");
        gameBoard.add(pane, column, row);
    }

    public void popButtonClicked(ActionEvent event)
    {
        byte activePlayer = currentGame.getNextPlayer();
        if (event.getSource() == column1PopButton)
            updateBoardOnPop((byte) 0, activePlayer);
        else if (event.getSource() == column2PopButton)
            updateBoardOnPop((byte) 1, activePlayer);
        else if (event.getSource() == column3PopButton)
            updateBoardOnPop((byte) 2, activePlayer);
        else if (event.getSource() == column4PopButton)
            updateBoardOnPop((byte) 3, activePlayer);
        else if (event.getSource() == column5PopButton)
            updateBoardOnPop((byte) 4, activePlayer);
        else if (event.getSource() == column6PopButton)
            updateBoardOnPop((byte) 5, activePlayer);
        else
            updateBoardOnPop((byte) 6, activePlayer);
    }

    private void updateBoardOnPop(byte column, byte activePlayer)
    {
        String title, header, content;
        byte gameStatus = currentGame.popPiece(column, activePlayer);
        switch (gameStatus)
        {
            case Constants.GAME_OVER:
                moveTokensAfterPop(column);
                title = "Game Over";
                header = "Player " + currentGame.getPlayerWhoWon() + " has won!";
                content = "CONGRATULATIONS";
                NewAlert.display(title,header,content);
                forwardToStartWindow();
                break;
            case Constants.TOKEN_REMOVED_GAME_NOT_OVER:
                moveTokensAfterPop(column);
                changeCurrentPlayer(currentGame.getNextPlayer());
                break;
            case Constants.POP_NOT_ALLOWED:
                title = "Warning Message";
                header = "WTF";
                content = "What are you trying to do mate? You can not do that. Read the rules!";
                NewAlert.display(title,header,content);
                break;
        }
    }

    private void moveTokensAfterPop(byte column)
    {
        byte row = Constants.ROWS - 1;
        for (; (row > 0) && (currentGame.getElement((byte) (row - 1), column) != Constants.EMPTY); row--)
            addToken(column, row, currentGame.getElement(row, column));
        addToken(column, row, currentGame.getElement(row, column));
        addToken(column, (byte) (row - 1), Constants.EMPTY);
    }

    private void changeCurrentPlayer(byte nextPlayer)
    {
        if (nextPlayer == Constants.YELLOW_PLAYER)
        {
            currentPlayerLabel.setText("YELLOW");
            currentPlayerLabel.setTextFill(Color.web(Constants.YELLOW_HEX));
        } else
        {
            currentPlayerLabel.setText("RED");
            currentPlayerLabel.setTextFill(Color.web(Constants.RED_HEX));
        }
    }

    private void forwardToStartWindow()
    {
        Stage window;
        Parent root;
        window = (Stage) mainWindowExitButton.getScene().getWindow();
        try
        {
            root = FXMLLoader.load(getClass().getResource("/presentationLayer/startWindow.fxml"));
            window.setTitle("Connect Four");
            window.setScene(new Scene(root, 400, 400));
            window.show();
        } catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public void initialize(boolean newGame)
    {
        saveButtonClicked = false;
        if (newGame)
        {
            currentGame = new Game();
            loadBoard();
        } else
        {
            currentGame = SavedGameSingleton.getInstance().loadGame();
            loadBoard();
        }
        gameBoard.setStyle("-fx-background-color: " + Constants.WHITE_HEX + ";");
    }

    private void loadBoard()
    {
        for (byte row = 0; row < Constants.ROWS; row++)
            for (byte column = 0; column < Constants.COLUMNS; column++)
                addToken(column, row, currentGame.getElement(row, column));
        changeCurrentPlayer(currentGame.getNextPlayer());
    }
}
