import java.util.*;

public class Memory {
	
	private TicTacToeGraph graph; //Knows all possible board states - not just those which are good. Yet.
	private ArrayList<TicTacToeVertex> currentGame = new ArrayList<TicTacToeVertex>(); //Array of boardStates encountered this game (in order). Used to adjust probability of choosing a path.
	private Random rnd = new Random();
	
	//In Tic Tac Toe, graph can be completely known (small enough) and will be acyclic and positive weight.
	//TODO: In case of more complex games, graph is too large to store fully in memory. Think about how to dynamically grow graph as needed. Probably update ArrayList while playing. 
	//TODO: Pick a cutoff probability - when option becomes so improbable, it sets to zero (and adjust the others accordingly).	
	
	public Memory() {
		this.graph = new TicTacToeGraph();
	}
	
	public void resetCurrentGameMemory() {
		currentGame.removeAll(currentGame);
	}
		
	public int chooseMove(int currentBoard) {
		//Pick arbitrary d in [0,1]. If e0 and e1 are edges with weights w0 and w1 such that w0 < w1 and w0 < d <= w1, return e1.getEnd().
		double moveProb = rnd.nextDouble();
		TicTacToeVertex vertex = graph.getVertex(currentBoard);
		ArrayList<TicTacToeEdge> edges = vertex.getEdges();
		int nextBoard = findNextBoard(edges, moveProb);
		
		//Remember the moves for learning at the end of the game.
		currentGame.add(graph.getVertex(currentBoard));
		currentGame.add(graph.getVertex(nextBoard));
		
		return nextBoard;
	}
	
	public void rememberGame(int player, boolean won) {
		//TODO: Work on this.
		//Adjust probabilities (weights of edges) to learn.
		TicTacToeVertex preMoveBoard;
		TicTacToeVertex postMoveBoard;
		for (int i = player - 1; i < currentGame.size() - 1; i++) {
			preMoveBoard = currentGame.get(i);
			postMoveBoard = currentGame.get(i + 1);
			if (won) {
				preMoveBoard.adjustWeight(postMoveBoard, true);
			} else {
				//TODO: Handle draws and losses.
			}
		}
		resetCurrentGameMemory();
	}
	
	private int findNextBoard(ArrayList<TicTacToeEdge> edges, double moveProb) {
		//Assumes edges are ordered by weight.
		int maxIndex = edges.size();
		int currentIndex = maxIndex / 2;
		int minIndex = 0;
		while(true) {
			if (maxIndex - minIndex <= 1) {
				return(edges.get(maxIndex).getEnd().getBoardNum());
			}
			if (moveProb < edges.get(currentIndex).getWeight()) {
				maxIndex = currentIndex;
				currentIndex = currentIndex - (currentIndex - minIndex) / 2;
			} else if (moveProb > edges.get(currentIndex).getWeight()) {
				minIndex = currentIndex;
				currentIndex = currentIndex + (maxIndex - currentIndex) / 2;
			} else {
				return(edges.get(currentIndex).getEnd().getBoardNum()); //TODO: Fix this. Currently, can have multiple edges with same weight. Account for this case, since we want first instance, not middle instance. 
			}
		}
	}
}
