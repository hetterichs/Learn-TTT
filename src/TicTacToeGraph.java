import java.util.*;
public class TicTacToeGraph {
	private ArrayList<TicTacToeVertex> adj = new ArrayList<TicTacToeVertex>();
	
	public TicTacToeGraph() {
		buildGraph(new int[9], null, 0, true); //TODO: Fix this - likely builds the wrong graph.
		System.out.println("Finished building graph");
	}

	//TODO: Add check for win. If win, stop building graph for that path.
	private void buildGraph(int[] board, TicTacToeVertex parent, int level, boolean player1) {
		if (level <= 1) {
			System.out.println(level);
		}
		int[] board2 = new int[board.length];
		int boardNum;
		TicTacToeVertex vertex;
		int probCounter = 1;
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board2.length; j++) {
				board2[j] = board[j];
			}
			if (board2[i] != 0) {continue;}
			if (player1) {
				board2[i] = 1;
			} else {
				board2[i] = 2;
			}
			boardNum = TicTacToeBoard.convertFromBaseM(board2, 3);
			vertex = new TicTacToeVertex(boardNum);
			if (!adj.contains(vertex)) {
				adj.add(vertex);
				if (parent != null) {
					parent.addNeighbor(vertex, probCounter * (1.0 / (board.length - level)));
				}
				probCounter++;
			}
			if (contains(board2, 0)) {
				buildGraph(board2, vertex, level + 1, !player1);
			}
		}
	}
	
	public static boolean contains(int[] array, int value) {
		//TODO: Change this to D&Q, though since it is only 9 long, this isn't a priority.
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return(true);
			}
		}
		return(false);
	}
	
	public TicTacToeVertex getVertex(int boardNum) {
		//O(n) - restructure organization of graph to improve time.
		TicTacToeVertex vertex;
		for (int i = 0; i < adj.size(); i++) {
			vertex = adj.get(i);
			if (vertex.getBoardNum() == boardNum) {
				return(vertex);
			}
		}
		return null;
	}
	
}