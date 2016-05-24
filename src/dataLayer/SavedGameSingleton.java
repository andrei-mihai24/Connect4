package dataLayer;

/**
 * Created by Andrei on 22.05.2016.
 */
public class SavedGameSingleton
{
    private static SavedGameSingleton ourInstance = new SavedGameSingleton();

    public static SavedGameSingleton getInstance()
    {
        return ourInstance;
    }

    private Game savedGame;

    private SavedGameSingleton()
    {
        savedGame = new Game();
    }

    public Game loadGame()
    {
        return savedGame;
    }

    public void saveGame(Game game)
    {
        savedGame.setRowOfLastAddedToken(game.getRowOfLastAddedToken());
        savedGame.setPlayerWhoWon(game.getPlayerWhoWon());
        savedGame.setBoard(copyBoard(game.getBoard()));
    }

    private byte[][] copyBoard(byte[][] source)
    {
        byte[][] copy = new byte[source.length][source[0].length];

        for (int i = 0; i < source.length; i++)
            System.arraycopy(source[i], 0, copy[i], 0, source[i].length);

        return copy;
    }
}
