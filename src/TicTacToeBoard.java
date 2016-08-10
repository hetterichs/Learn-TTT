
public class TicTacToeBoard {
	
	private static TicTacToeBoard instance = null;
	private int[] board = new int[9];
	private boolean[] possibleMoves = new boolean[9];
	private boolean gameOver = false;
	
	private TicTacToeBoard() {}
	
	public static TicTacToeBoard getInstance() {
		if (instance == null) {
			instance = new TicTacToeBoard();
		}
		return(instance);
	}
	
	public int getSize() {
		return(board.length);
	}
	
	public int[] getBoard() { 
		return(this.board);
	}
	
	public void playGame(Player player1, Player player2) {
		resetBoard();
		while(true) {
			player1.makeMove();
			printBoard();
			System.out.println("");
			if(gameOver) {break;}
			player2.makeMove();
			printBoard();
			System.out.println("");
			if(gameOver) {break;}
		}
	}
	
	public void resetBoard() {
		board = new int[9];
		for (byte i = 0; i < possibleMoves.length; i++) {
			possibleMoves[i] = true;
		}
		gameOver = false;
	}
	
	public void placeMove(int player, int position) {
		board[position] = player;
		possibleMoves[position] = false;
	}
	
	public void replaceBoard(int[] newBoard) {
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 0 && newBoard[i] != 0) {
				possibleMoves[i] = false;
			}
		}
		board = newBoard;
	}
	
	public boolean[] getPossibleMoves() {
		return(possibleMoves);
	}
	
	public int didWin(int player) {
		int winState = winState(player);
		if (winState == player) {
			System.out.println("Player " + player + " wins!");
			gameOver = true;
		} else if (winState == 0) {
			System.out.println("Draw.");
			gameOver = true;
		}
		return(winState);
	}
	
	public int winState(int player) {
		for (byte i = 0; i < 3; i++) {
			if (board[i * 3] == player && board[i * 3 + 1] == player && board[i * 3 + 2] == player) {
				return(player);
			} else if (board[i] == player && board[i + 3] == player && board[i + 6] == player) {
				return(player);
			}
		}
		if (board[0] == player && board[4] == player && board[8] == player) {
			return (player);
		} else if (board[2] == player && board[4] == player && board[6] == player) {
			return(player);
		}
		byte numberOfPossibleMoves = 0;
		for (byte i = 0; i < possibleMoves.length; i++) {
			if (possibleMoves[i]) {
				numberOfPossibleMoves++;
			}
		}
		if (numberOfPossibleMoves == 0) {
			return(0);
		}
		return(-1);
	}
	
	public void printBoard() {
		for (byte i = 0; i < 3; i++) {
			System.out.println(board[3 * i] + "|" + board[3 * i + 1] + "|" + board[3 * i + 2]);
		}
	}
	
	public void printBoard(int[] board){
		for (byte i = 0; i < 3; i++) {
			System.out.println(board[3 * i] + "|" + board[3 * i + 1] + "|" + board[3 * i + 2]);
		}
	}
	
	public static void printBoard(int[] board, boolean justToMakeItDifferent) {
		for (byte i = 0; i < 3; i++) {
			System.out.println(board[3 * i] + "|" + board[3 * i + 1] + "|" + board[3 * i + 2]);
		}
	}
	
	public static int convertFromBaseM(int[] baseMNumber, int baseM) {
		int n = 0;
		for(int i = 0; i < baseMNumber.length; i++) {
			n = baseM * n + baseMNumber[i];
		}
		return(n);
	}
	
	public static int[] convertToBaseM(int base10, int baseM, int nDigits){
		int[] newBase = new int[nDigits];
		int i = 0;
		while(base10 > 0) {
			newBase[i++] = (byte) (base10 % baseM);
			base10 = base10/baseM;
		}
		return(newBase);
	}
}
