package dataLayer;

import dataLayer.Constants;

import java.io.Serializable;

/**
 * Created by Andrei on 20.05.2016.
 */
public class Game implements Serializable
{
    private byte rowOfLastAddedToken;
    private byte playerWhoWon;
    private byte nextPlayer;
    private byte[][] board;

    public Game()
    {
        board = new byte[Constants.ROWS][Constants.COLUMNS]; //By default initialized with all elements 0
        nextPlayer = 1; //Because 1 (RED) starts
    }

    /**
     * A new token is added to the selected column.
     * If there is no more place in the column 0 is returned (and nothing will be changed).
     * If there is place left a new token will be added by changing its place in the matrix from 0 (EMPTY) to 1 or 2 (depends on player)
     * After adding new token the checkGameStatus(i, column) method checks if the last move was the winning move.
     * If it was 2 is returned.
     * If it was not 1 is returned.
     *
     * @param column The column where the player chose to add his token
     * @param player The current player
     * @return 0 (nothing changed), 1 (token added, game not over), 2 (token added, game over)
     */
    public byte addPiece(byte column, byte player)
    {
        boolean gameWon;
        for (byte i = Constants.ROWS - 1; i >= 0; i--)
            if (board[i][column] == Constants.EMPTY)
            {
                board[i][column] = player;
                setRowOfLastAddedToken(i);
                gameWon = checkGameStatus(i, column);
                if (gameWon)
                    return Constants.GAME_OVER;
                else
                {
                    setNextPlayer(player);
                    return Constants.TOKEN_ADDED_GAME_NOT_OVER;
                }
            }
        return Constants.COLUMN_FULL;
    }

    public byte popPiece(byte column, byte player)
    {
        if (board[Constants.ROWS - 1][column] != player)
            return Constants.POP_NOT_ALLOWED;
        updateBoardAfterPop(column);

        byte row = Constants.ROWS - 1;
        while (board[row][column] != Constants.EMPTY)
            if (checkGameStatus(row--, column))
                return Constants.GAME_OVER;
        setNextPlayer(player);
        return Constants.TOKEN_REMOVED_GAME_NOT_OVER;
    }

    private void updateBoardAfterPop(byte column)
    {
        byte i = Constants.ROWS - 1;
        for (; (i > 0) && (board[i - 1][column] != Constants.EMPTY); i--)
            board[i][column] = board[i - 1][column];
        board[i][column] = Constants.EMPTY;
    }

    /**
     * Method to check if one of the players won after his last move.
     */
    public boolean checkGameStatus(byte row, byte column)

    {
        byte currentPlayer = board[row][column];
        return (horizontalCheck(row, currentPlayer) ||
                verticalCheck(row, column, currentPlayer) ||
                descendingDiagonalCheck(row, column, currentPlayer) ||
                ascendingDiagonalCheck(row, column, currentPlayer));
    }

    /**
     * Method that performs a vertical check starting from the position of the last added token.
     * If there are 4 tokens in a column (belonging to the same player) it returns true, if not it return false.
     */
    private boolean verticalCheck(byte row, byte column, byte player)
    {
        if (row + 3 < Constants.ROWS)
            if ((board[row][column] == player) && (board[row + 1][column] == player) && (board[row + 2][column] == player) && (board[row + 3][column] == player))
            {
                playerWhoWon = player;
                return true;
            }
        return false;
    }

    /**
     * Method that performs a horizontal check starting from the position of the last added token.
     * It checks if there are 4 tokens in a row starting from the given position and going right and then left.
     * If 4 tokens in a row are found (belonging to the same player), true is returned, if not, false.
     */
    private boolean horizontalCheck(byte row, byte player)
    {
        for (byte column = 0; column <= 3; column++)
            if ((board[row][column] == player) && (board[row][column + 1] == player) && (board[row][column + 2] == player) && (board[row][column + 3] == player))
            {
                playerWhoWon = player;
                return true;
            }
        return false;

    }

    private boolean descendingDiagonalCheck(byte row, byte column, byte player)
    {
        byte numberOfSameTokensRightBelow = 0;
        byte numberOfSameTokensLeftAbove = 0;

        byte rowCopy = row;
        byte columnCopy = column;

        //Right, below
        while (rowCopy + 1 < Constants.ROWS && columnCopy + 1 < Constants.COLUMNS)
            if (board[++rowCopy][++columnCopy] == player)
                numberOfSameTokensRightBelow++;

        //Left, above
        rowCopy = row;
        columnCopy = column;
        while (rowCopy - 1 > -1 && columnCopy - 1 > -1)
            if (board[--rowCopy][--columnCopy] == player)
                numberOfSameTokensLeftAbove++;

        if ((numberOfSameTokensLeftAbove + numberOfSameTokensRightBelow + 1) >= Constants.TOKENS_TO_WIN)
        {
            playerWhoWon = player;
            return true;
        }

        return false;
    }

    private boolean ascendingDiagonalCheck(byte row, byte column, byte player)
    {
        byte numberOfSameTokensLeftBelow = 0;
        byte numberOfSameTokensRightAbove = 0;

        byte rowCopy, columnCopy;

        //Left, below
        rowCopy = row;
        columnCopy = column;
        while (rowCopy + 1 < Constants.ROWS && columnCopy - 1 > -1)
            if (board[++rowCopy][--columnCopy] == player)
                numberOfSameTokensLeftBelow++;

        //Right, above
        rowCopy = row;
        columnCopy = column;
        while (rowCopy - 1 > -1 && columnCopy + 1 < Constants.COLUMNS)
            if (board[--rowCopy][++columnCopy] == player)
                numberOfSameTokensRightAbove++;

        if (numberOfSameTokensLeftBelow + numberOfSameTokensRightAbove + 1 >= Constants.TOKENS_TO_WIN)
        {
            playerWhoWon = player;
            return true;
        }

        return false;
    }


    //Getters and Setters

    public byte getRowOfLastAddedToken()
    {
        return rowOfLastAddedToken;
    }

    public byte getElement(byte row, byte column)
    {
        return board[row][column];
    }

    public byte getPlayerWhoWon()
    {
        return playerWhoWon;
    }

    public byte[][] getBoard()
    {
        return board;
    }

    public void setPlayerWhoWon(byte playerWhoWon)
    {
        this.playerWhoWon = playerWhoWon;
    }

    public void setBoard(byte[][] board)
    {
        this.board = board;
    }

    public void setRowOfLastAddedToken(byte rowOfLastAddedToken)
    {
        this.rowOfLastAddedToken = rowOfLastAddedToken;
    }

    public byte getNextPlayer()
    {
        return nextPlayer;
    }

    public void setNextPlayer(byte currentPlayer)
    {
        if (currentPlayer == Constants.RED_PLAYER)
            nextPlayer = Constants.YELLOW_PLAYER;
        else
            nextPlayer = Constants.RED_PLAYER;
    }
}
