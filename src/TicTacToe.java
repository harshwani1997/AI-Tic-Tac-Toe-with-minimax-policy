import java.util.*;
import java.util.Scanner;


class minimax
{
    char player = Main.currentPlayer;
    char opponent = player=='X'?'0':'X';

    public int evaluate(char[][] board)
    {
        for(int row=0;row<3;row++)
        {
            if(board[row][0]==board[row][1] && board[row][1]==board[row][2])
            {
                if(board[row][0]==player)
                {
                    return 10;
                }
                else if(board[row][0]==opponent)
                {
                    return -10;
                }
            }
        }

        for(int col=0;col<3;col++)
        {
            if(board[0][col]==board[1][col] && board[1][col]==board[2][col])
            {
                if(board[0][col]==player)
                {
                    return 10;
                }
                else if(board[0][col]==opponent)
                {
                    return -10;
                }
            }
        }

        if(board[0][0] == board[1][1] && board[1][1] == board[2][2])
        {
            if(board[0][0] == player)
                return +10;
            else if(board[0][0] == opponent)
                return -10;
        }

        if(board[0][2] == board[1][1] && board[1][1] == board[2][0])
        {
            if(board[0][2] == player)
                return +10;
            else if(board[0][2] == opponent)
                return -10;
        }

        return 0;
    }

    public boolean movesLeft(char[][] board)
    {
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[0].length;j++)
            {
                if(board[i][j]=='-')
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int minimaxAlgo(char[][] board, boolean isMax)
    {
        int score = evaluate(board);

        if(score==10)
        {
            return score;
        }

        if(score==-10)
        {
            return score;
        }

        if(!movesLeft(board))
        {
            return 0;
        }

        if(isMax)
        {
            int best = Integer.MIN_VALUE;
            for(int i=0;i<board.length;i++)
            {
                for(int j=0;j<board[0].length;j++)
                {
                    if(board[i][j]=='-')
                    {
                        board[i][j]=player;
                        best = Math.max(best, minimaxAlgo(board, false));
                        board[i][j]='-';
                    }
                }
            }
            return best;
        }
        else
        {
            int best = Integer.MAX_VALUE;
            for(int i=0;i<board.length;i++)
            {
                for(int j=0;j<board[0].length;j++)
                {
                    if(board[i][j]=='-')
                    {
                        board[i][j]=opponent;
                        best = Math.min(best, minimaxAlgo(board, true));
                        board[i][j]='-';
                    }

                }
            }
            return best;
        }
    }


    public int[] getBestMove(char[][] board)
    {
        int row=-1;
        int col=-1;
        int bestscore=Integer.MIN_VALUE;

        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[0].length;j++)
            {
                if(board[i][j]=='-')
                {
                    board[i][j]=player;

                    int score = minimaxAlgo(board, false);

                    if(score>bestscore)
                    {
                        bestscore=score;
                        row = i;
                        col = j;
                    }

                    board[i][j]='-';
                }
            }
        }
        return new int[]{row, col};
    }
}

class TicTacToe{

    private static Scanner input = new Scanner(System.in);
    static char[][] board;
    static char currentPlayer;

    TicTacToe()
    {
        board = new char[3][3];
        for(char[] b:board)
        {
            Arrays.fill(b, '-');
        }
        currentPlayer = 'X';
    }

    public static boolean isFull(char[][] board)
    {
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[0].length;j++)
            {
                if(board[i][j]=='-')
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeMarker(int row, int col, char[][] board, char currentPlayer)
    {
        board[row][col]=currentPlayer;
    }

    public static boolean valid(int row, int col, char[][] board)
    {
        return row>=0 && row<board.length && col>=0 && col<board[0].length && board[row][col]=='-';
    }

    public static char changePlayer(char currentPlayer)
    {
        return currentPlayer = currentPlayer=='X'?'0':'X';
    }

    public static boolean isWinner(char[][] board, char currentPlayer)
    {
        String line = "";
        line = currentPlayer + "" + currentPlayer + "" + currentPlayer;

        return (board[0][0] + "" + board[0][1] + "" + board[0][2]).equals(line) ||
                (board[1][0] + "" + board[1][1] + "" + board[1][2]).equals(line) ||
                (board[2][0] + "" + board[2][1] + "" + board[2][2]).equals(line) ||


                (board[0][0] + "" + board[1][0] + "" + board[2][0]).equals(line) ||
                (board[0][1] + "" + board[1][1] + "" + board[2][1]).equals(line) ||
                (board[0][2] + "" + board[1][2] + "" + board[2][2]).equals(line) ||

                (board[0][0] + "" + board[1][1] + "" + board[2][2]).equals(line) ||
                (board[0][2] + "" + board[1][1] + "" + board[2][0]).equals(line);
    }
}

class Main extends TicTacToe
{
    public static void main(String []args){

        TicTacToe obj = new TicTacToe();

        int count=0;
        while(true)
        {
            System.out.println(currentPlayer + " play your turn");
            int min=0;
            int max=3;
            int range = max-min+1;

            minimax mm = new minimax();
            int[] dir = mm.getBestMove(board);

            int row = dir[0];
            int col = dir[1];

            if(count==0)
            {
                count++;
                row = 0;
                col = 0;
            }


            if(!valid(row, col, board))
            {
                continue;
            }

            placeMarker(row, col, board, currentPlayer);

            for(int i=0;i<board.length;i++)
            {
                for(int j=0;j<board[0].length;j++)
                {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println();

            if(isWinner(board, currentPlayer))
            {
                System.out.print(currentPlayer + " is the winner");
                break;
            }
            else if(isFull(board))
            {
                System.out.print("It is a draw");
                break;
            }

            currentPlayer = changePlayer(currentPlayer);
        }
    }
}