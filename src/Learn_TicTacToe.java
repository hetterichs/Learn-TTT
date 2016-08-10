import java.util.Scanner;
import java.util.ArrayList;

public class Learn_TicTacToe {
	//TODO: Now that setup is done, implement a memory/learning system for PlayerType.AISMART
	public static Scanner keyboard = new Scanner(System.in);
	public static void main(String[] args) {
		TicTacToeBoard board = TicTacToeBoard.getInstance();
		Player player1 = new Player(PlayerType.AIRANDOM, 1);
		Player player2 = new Player(PlayerType.AISMART, 2);
		int i = 0;
		double nRounds = 10000;
		while (i++ < nRounds) {
			System.out.println("Start Game " + i);
			board.playGame(player1, player2);
		}
		System.out.println(player1.getWins()/nRounds);
		System.out.println(player1.getDraws()/nRounds);
		System.out.println(player2.getWins()/nRounds);
		System.out.println(player2.getDraws()/nRounds);
		
	}
	
	public static void print(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (i < array.length - 1) {
				System.out.print(array[i] + ", ");
			} else {
				System.out.println(array[i]);
			}
		}
	}
	
	public static void print(boolean[] array) {
		for (int i = 0; i < array.length; i++) {
			if (i < array.length - 1) {
				System.out.print(array[i] + ", ");
			} else {
				System.out.println(array[i]);
			}
		}
	}
}
