package dataLayer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Andrei on 29.05.2016.
 */
public class GameSerialization
{
    private static ArrayList<Game> listOfSavedGames;

    public static void saveGame(Game game)
    {
        listOfSavedGames = loadListOfSavedGames();

        listOfSavedGames.add(game);

        save(listOfSavedGames, Constants.pathToMatrixFile);
    }

    public static Integer getNumberOfSavedGames()
    {
        listOfSavedGames = null;
        listOfSavedGames = loadListOfSavedGames();

        if (listOfSavedGames != null && !listOfSavedGames.isEmpty())
            return listOfSavedGames.size();
        else
            return 0;
    }

    public static Game getSavedGame(Integer position)
    {
        listOfSavedGames = loadListOfSavedGames();

        if (listOfSavedGames != null && !listOfSavedGames.isEmpty() && position < listOfSavedGames.size())
            return listOfSavedGames.get(position);
        else
            return null;
    }

    private static void save(ArrayList<Game> list, String where)
    {
        try
        {

            FileOutputStream fileOut = new FileOutputStream(where);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            ObjectOutputStream out = new ObjectOutputStream(bos);

            out.writeObject(list);

            out.flush();
            out.close();
            bos.close();
            fileOut.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static ArrayList<Game> loadListOfSavedGames()
    {
        ArrayList<Game> game = new ArrayList<>();

        try
        {
            FileInputStream fileIn = new FileInputStream(Constants.pathToMatrixFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            game = (ArrayList<Game>) in.readObject();

            in.close();
            fileIn.close();
        } catch (IOException exception)
        {
            exception.printStackTrace();
        } catch (ClassNotFoundException c)
        {
            System.out.println("gameMatrix.txt not found");
            c.printStackTrace();
        } finally
        {
            return game;
        }
    }
}
