/* 
 Author : P. Lakshmi Susmitha
Below code is for playing Tic - Tac - toe
*/

import java.io.*;
import java.util.Random;
import java.util.Scanner;

//class for storing the row and column index of a board

class Move {
    int row, col;
    Move(int x, int y) {
        row = x;
        col = y;
    }
}


// Class for storing the corresponding variables of a computer and oppponent

class Variable {
    char computer, opponent;
    Variable () {
        computer = 'x';
        opponent = 'o';
    }
}

// Main Class

class TTTGame {
    
    public static Scanner in = new Scanner(System.in);
    
    //Function to check whether the board is full or empty
    static boolean moveLeft(char board[][]) {
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(board[i][j] == '_')
                    return true;
            }
        }
        return false;
    }
    
    //Function to check for the win of the computer or opponent
    static int evaluate(char b[][]) {
        Variable Var = new Variable();
    
        // Checking for Rows for X or O victory
        for(int row = 0; row < 3; row++) {
            if( (b[row][0] == b[row][1]) && (b[row][1] == b[row][2]) ) {
                if(b[row][0] == Var.computer)
                    return 10;
                else if (b[row][1] == Var.opponent)
                    return -10;
            }
        }
        
        // Checking for Columns for X or O victory
        for(int col = 0; col<3; col++) {
            if( (b[0][col] == b[1][col]) && (b[1][col] == b[2][col]) ) {
                if(b[0][col] == Var.computer)
                    return 10;
                else if(b[0][col] == Var.opponent)
                    return -10;
            }
        }
  
        // Checking for Diagonals for X or O victory      
        if( (b[0][0] == b[1][1]) && (b[1][1] == b[2][2]) ) {
            if(b[0][0] == Var.computer)
                return 10;
            else if(b[0][0] == Var.opponent)
                return -10;
        }
        
        if( (b[0][2] == b[1][1]) && (b[1][1] == b[2][0]) ) {
            if(b[0][2] == Var.computer)
                return 10;
            else if(b[0][2] == Var.opponent)
                return -10;
        }
        
        //else if non of them won return 0
        return 0;
    }
    

    /* This Funciton  is the implementation of MiniMax Algorithm
       It considers all the possible ways the game can go and returns
       the value of the board.
       Here the Maximizer is Computer and Minimizer is Opponent
    */
    static int minimax(char board[][], int depth, boolean isMax) {
        Variable VAR = new Variable();
        int score = evaluate(board);
        
        /* If the maximizer has won the game
           return +10 */
        if(score == 10)
            return score;

        /* If the Minimizer has won the game
           return -10 */
        if(score == -10)
            return score;

        /* If there are no moves and no winner then
           it is a tie */
        if(moveLeft(board) == false)
            return 0;

        // If this maximizers's move
        if(isMax == true) {
            int best = -1000;
          
            // Traverse all cells
            for(int i = 0; i< 3; i++) {
                for(int j = 0;j<3; j++) {
             
                    // Check if cell is empty
                    if(board[i][j] == '_') {
                       
                        // Make a move
                        board[i][j] = VAR.computer;

                        // Call minimax recursively and choose maximum value
                        best = Math.max(best, minimax (board, depth+1, !isMax));

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }

        // If minimizer's move
        else {
            int best = 1000;

            // Traverse all cells
            for(int i = 0; i<3; i++) {
                for(int j = 0; j <3; j++) {

                    // Check if cell is empty
                    if(board[i][j] == '_') {

                        // Make a move
                        board[i][j] = VAR.opponent;

                        // Call minimax algorithm recursively and choose the minimum value
                        best = Math.min(best, minimax(board, depth+1, !isMax));

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }
    

    // This function will return the best possible move for the Computer
    static Move findBestMove(char board[][]) {

        Variable va = new Variable();
        int bestVal = -1000;
        Move bestMove = new Move(-1, -1);

        /* Traverse all the cells and evaluate the minimax function
            to all empty cells. And return the cell with optimal value
        */
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(board[i][j] == '_') {
                    board[i][j] = va.computer;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = '_';

                    // if the value of the current move is
                    // more than the best value, then update best
                    if(moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
    

    // Function to print the board along with the 
    // moves made by the computer in every iteration
    public static void PrintStatements(char board[][], int pos_c, int ii, int ch) {

        if(ii % 2 == 0 && ch == 1){
            System.out.println("\nCOMPUTER has put x at position "+pos_c);
        }
        else if(ii % 2 != 0 && ch == 0){
            System.out.println("\nCOMPUTER has put x at position "+pos_c);
        }

        System.out.print("\t ");
        for(int i =0; i<3; i++) {
		    for(int j = 0; j < 3; j++) {
		        if(board[i][j] == '_') System.out.print(" ");
		        else System.out.print(board[i][j]);
		        if(j<2)
		            System.out.print(" | ");
		    }
		    if(i<2)
		        System.out.println("\n\t ---------");
		        System.out.print("\t ");
		}
    }
    
 
    //This is the function where the computer first starts
    // playing when opponent selects computer to play first 
    public static int PlayWithComputer(char board[][], int i){
        int pos_c;
        Random rand = new Random();
        Variable variables = new Variable();
        Move mov = new Move(-1, -1);

        // First move of the computer is genereted randomly by using the Random() class
        if(i == 0) {
            pos_c = rand.nextInt(9);
            mov.row = pos_c / 3;
            mov.col = pos_c % 3;
            board[mov.row][mov.col] = variables.computer;
            pos_c = pos_c + 1;
        }
        
        // When the i is value is odd then it's opponent turn to play  
        else if (i % 2 != 0) {
            System.out.print("\n\nEnter your position: ");
            pos_c = in.nextInt();
            pos_c = pos_c - 1;
            mov.row = pos_c / 3;
            mov.col = pos_c % 3;
            board[mov.row][mov.col] = variables.opponent;
        }
        
        // If i is even and i is not equal to zero
        // then finding the optimal move of the computer   
        else {
            mov = findBestMove(board);
            board[mov.row][mov.col] = variables.computer;
            pos_c = (mov.row*3)+mov.col;
            pos_c = pos_c + 1;
        }
        return pos_c;
    }

    
    // This is the function where opponent starts playing first
    // when he/she selects to play first
    public static int PlayWithPlayer(char board[][], int i) {

        int pos_c;
        Variable variables = new Variable();
        Move mov = new Move(-1, -1);

        // If i is even then it's opponent trun to play
        if(i%2 == 0){
            System.out.print("\nEnter your position: ");
            pos_c = in.nextInt();
            pos_c = pos_c - 1;
            mov.row = pos_c / 3;
            mov.col = pos_c % 3;
            board[mov.row][mov.col] = variables.opponent;
        }

        // If i is odd then it's computer turn to play
        else {
            mov = findBestMove(board);
            board[mov.row][mov.col] = variables.computer;
            pos_c = (mov.row*3)+mov.col;
            pos_c = pos_c + 1; 
        }
        return pos_c;
    }

    // This function is the start of the game where it decides
    // who will play first
    // Checks for the winner and
    // Checks for the tie
    public static void TicTacToe(char board[][]) {
        System.out.println("\n- - - - - - - - - - - - - - - - -");
        System.out.println("\nChoose who will play first");
        System.out.print("(For computer enter 1,  For you enter 0) :");
        int choice = in.nextInt();
        int pos_c;
        int flag = 0;
        
        for(int i = 0; i < 9; i++) {
            int SCORE = evaluate(board);
            
            // If score is +10 then Computer won and set flag to 1 
            if(SCORE == 10) {
              System.out.println("\nCOMPUTER won the game! Better Luck next time :)");
              flag = 1;
              break;
            }
 
            // If score is -10 then Opponent won adn set flag to 1
            else if(SCORE == -10) {
              System.out.println("\nCongratulations! YOU won the game! :)");
              flag = 1;
              break;
            }
 
            // If choice is 1, then computer should start the game
            else if(choice == 1) {
                pos_c = PlayWithComputer(board, i);
            }
   
            // If choice is 0, then opponent should start the game
            else {
               pos_c = PlayWithPlayer(board, i);
            }
     
            PrintStatements(board, pos_c, i, choice);
        }

        // If flag is still 0, then it is tie game
        if(flag == 0) System.out.println("\nDRAW GAME!");
        System.out.println("GAME ENDED");
    }
    
        // Main Function
	public static void main (String[] args) {
		char board[][] = {
		    {'_', '_', '_'},
		    {'_', '_', '_'},
		    {'_', '_', '_'}
		};
		System.out.println("\n\t\tTIC-TAC-TOE\t\t\n");
		System.out.println("\t\t  WELCOME!\t\t");
		System.out.println("\n");
		System.out.println("\nPositions to choose :\n");
		System.out.print("\t ");
		int x_p = 1;
		for(int i =0; i<3; i++) {
		    for(int j = 0; j < 3; j++) {
		        System.out.print(x_p);
		        x_p++;
		        if(j<2)
		            System.out.print(" | ");
		    }
		    if(i<2)
		        System.out.println("\n\t ---------");
		    System.out.print("\t ");
		}
		
		System.out.println("\n\nCOMPUTER's choice is 'x'\nYOUR choice is 'o'");
		
		TicTacToe(board);
	}
}
