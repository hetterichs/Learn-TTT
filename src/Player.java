import java.util.Scanner;
import java.util.Random;

public class Player {
	public static Scanner keyboard = new Scanner(System.in);

	private TicTacToeBoard board = TicTacToeBoard.getInstance();
	private Random random = new Random();
	private int wins = 0;
	private int draws = 0;
	
	private int player;
	private PlayerType playerType;
	private Memory memory;
	
	public Player(PlayerType playerType, int player) {
		this.playerType = playerType;
		this.player = player;			
		if (playerType == PlayerType.AISMART) { 
			memory = new Memory();
		}
	}
	
	public int getWins() {
		return(wins);
	}
	
	public int getDraws() {
		return(draws);
	}
	
	public void makeMove() {
		int[] move = chooseMove();
		board.replaceBoard(move);
		int winState = board.didWin(player);
		if (winState == player) {
			wins++;
			if (playerType == PlayerType.AISMART) {
				memory.rememberGame(player, true);
			}
		} else if (winState == 0) {
			draws++;
		}
	}
	
	private int[] chooseMove() {
		int[] currentBoard = board.getBoard();
		int[] newBoard = new int[currentBoard.length];
		for (int i = 0; i < currentBoard.length; i++) {
				newBoard[i] = currentBoard[i];
		}
		boolean[] possibleMoves = board.getPossibleMoves();
		int selectedSpace = - 1;
		switch(playerType) {
		case HUMAN: 
			while (true) {
				System.out.println("Select a space using 1 - 9. Press 0 to exit.");
				selectedSpace = keyboard.nextInt() - 1;
				if (possibleMoves[selectedSpace - 1] || selectedSpace == 0) {
					break;
				}
			}
			newBoard[selectedSpace] = player;
		case AISMART: 
			newBoard = TicTacToeBoard.convertToBaseM(memory.chooseMove(TicTacToeBoard.convertFromBaseM(board.getBoard(), 3)), 3, board.getBoard().length);
		case AIRANDOM: 
			while (true) {
				selectedSpace = random.nextInt(board.getSize());
				if (possibleMoves[selectedSpace]) {
					break;
				}
			}
			newBoard[selectedSpace] = player;
		}
		return(newBoard);
	}
}
