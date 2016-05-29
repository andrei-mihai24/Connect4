package businessLayer;

import dataLayer.GameSerialization;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by andre on 29.05.2016.
 */
public class ChooseSavedGame
{
    private static Integer chosenGame;

    public static void display()
    {
        List<String> choices = new ArrayList<>();
        for (Integer i = 0; i < GameSerialization.getNumberOfSavedGames(); i++)
            choices.add(Integer.toString(i + 1));

        ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
        dialog.setTitle("List of Saved Games");
        dialog.setHeaderText("There are currently " + Integer.toString(GameSerialization.getNumberOfSavedGames()) + " saved games");
        dialog.setContentText("Choose one:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(choice -> chosenGame = Integer.parseInt(choice) - 1);
    }

    public static Integer getChosenGame()
    {
        return chosenGame;
    }
}
