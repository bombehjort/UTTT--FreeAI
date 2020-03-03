/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.bll.bot;

import dk.easv.bll.field.IField;
import static dk.easv.bll.field.IField.AVAILABLE_FIELD;
import static dk.easv.bll.field.IField.EMPTY_FIELD;
import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;
import dk.easv.bll.move.Move;
import dk.easv.gui.BoardModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author mega_
 */
public class ChiliBot implements IBot{

    private String player;
    private String enemy;
    protected int[][] preferredMoves = {
            {1, 1}, //Center
            {0, 0}, {2, 2}, {0, 2}, {2, 0},  //Corners ordered across
            {0, 1}, {2, 1}, {1, 0}, {1, 2}}; //Outer Middles ordered across


    @Override
    public IMove doMove(IGameState state) {
        checkWhoIAm(state);
        

        List<IMove> avail = state.getField().getAvailableMoves();
        for (IMove move : avail) {
            
            if(isWin(state.getField().getBoard(), move, player)){
                return move;
            }
            
        }
        
        for (IMove move : avail) {
            
            if(isWin(state.getField().getBoard(), move, enemy)){
                return move;
            }  
        }
        
        for (int[] move : preferredMoves)
        {
            if(state.getField().getMacroboard()[move[0]][move[1]].equals(IField.AVAILABLE_FIELD))
            {
                //find move to play
                for (int[] selectedMove : preferredMoves)
                {
                    int x = move[0]*3 + selectedMove[0];
                    int y = move[1]*3 + selectedMove[1];
                    if(state.getField().getBoard()[x][y].equals(IField.EMPTY_FIELD))
                    {
                        return new Move(x,y);
                    }
                }
            }
        }

        //NOTE: Something failed, just take the first available move I guess!
        return state.getField().getAvailableMoves().get(0);
    }

        
            

    

    @Override
    public String getBotName() {

        return "Chilis Bot";

    }
    
    private void checkWhoIAm(IGameState state)
    {
        if (player==null && enemy==null)
        {
            if (state.getField().isEmpty())
            {
                player = "0";
                enemy = "1";

            } else
            {
                player = "1";
                enemy = "0";
            }
        }
    }
    
    
     private static boolean isWin(String[][] board, IMove move, String currentPlayer){
        int localX = move.getX() % 3;
        int localY = move.getY() % 3;
        int startX = move.getX() - (localX);
        int startY = move.getY() - (localY);

        //check col
        for (int i = startY; i < startY + 3; i++) {
            if (!board[move.getX()][i].equals(currentPlayer))
                break;
            if (i == startY + 3 - 1) return true;
        }

        //check row
        for (int i = startX; i < startX + 3; i++) {
            if (!board[i][move.getY()].equals(currentPlayer))
                break;
            if (i == startX + 3 - 1) return true;
        }

        //check diagonal
        if (localX == localY) {
            //we're on a diagonal
            int y = startY;
            for (int i = startX; i < startX + 3; i++) {
                if (!board[i][y++].equals(currentPlayer))
                    break;
                if (i == startX + 3 - 1) return true;
            }
        }

        //check anti diagonal
        if (localX + localY == 3 - 1) {
            int less = 0;
            for (int i = startX; i < startX + 3; i++) {
                if (!board[i][(startY + 2)-less++].equals(currentPlayer))
                    break;
                if (i == startX + 3 - 1) return true;
            }
        }
        return false;
    }
     /*
     public Boolean isInActiveMicroboard(int x, int y) {
        int xTrans = x>0 ? x/3 : 0;
        int yTrans = y>0 ? y/3 : 0;
        String value = macroBoard[xTrans][yTrans];
        return value.equals(AVAILABLE_FIELD);
    }
     public List<IMove> getAvailableMoves(String[][] board) {
        List<IMove> availMoves = new ArrayList<>();

        for (int i = 0; i < board.length; i++)
            for (int k = 0; k < board[i].length; k++) {
                if(isInActiveMicroboard(i,k) && board[i][k].equals(EMPTY_FIELD)) {
                    availMoves.add(new Move(i,k));
                }
        }

        return availMoves;
    }*/
}
