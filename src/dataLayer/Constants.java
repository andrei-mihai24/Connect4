package dataLayer;

/**
 * Created by Andrei on 21.05.2016.
 */
public class Constants
{
    public static final byte EMPTY = 0;
    public static final byte RED_PLAYER = 1; // RED goes first
    public static final byte YELLOW_PLAYER = 2; // YELLOW goes second

    public static final String RED_HEX = "#ff0000";
    public static final String YELLOW_HEX = "#daa002";
    public static final String WHITE_HEX = "#ffffff";

    public static final byte COLUMNS = 7;
    public static final byte ROWS = 6;
    public static final byte TOKENS_TO_WIN = 4;

    public static final byte GAME_OVER = 2; // One of the players has won
    public static final byte TOKEN_ADDED_GAME_NOT_OVER = 1;
    public static final byte TOKEN_REMOVED_GAME_NOT_OVER = 1;
    public static final byte COLUMN_FULL = 0;
    public static final byte POP_NOT_ALLOWED = 0; // Because column is empty or because player tries to pop token that is not his own
}
