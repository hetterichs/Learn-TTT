import java.util.*;
public class TicTacToeVertex {
	private int boardNum; //Board state converted from 9-bit base three number to base 10 int.
	private ArrayList<TicTacToeEdge> neighbors = new ArrayList<TicTacToeEdge>(); //TODO: Make sure is ordered by weight. (Should currently do so, but double check.)
	
	public TicTacToeVertex(int boardNum) {
		this.boardNum = boardNum;
	}
	
	public void addNeighbor(TicTacToeVertex neighbor, double edgeWeight) {
		//Inserts neighbor in order by weight. (Smallest to greatest.)
		int size = neighbors.size();
		if (size == 0) {
			neighbors.add(new TicTacToeEdge(this, neighbor, edgeWeight));
		} else {
			int maxIndex = size;
			int currentIndex = size / 2;
			int minIndex = 0;
			while(true) {
				if (maxIndex - minIndex <= 1) {
					neighbors.add(maxIndex, new TicTacToeEdge(this, neighbor, edgeWeight));
					break;
				} else if (edgeWeight < neighbors.get(currentIndex).getWeight()) {
					maxIndex = currentIndex;
					currentIndex = currentIndex - (currentIndex - minIndex) / 2;
				} else if (edgeWeight > neighbors.get(currentIndex).getWeight()) {
					minIndex = currentIndex; 
					currentIndex = currentIndex + (maxIndex - currentIndex) / 2;
				} else {
					neighbors.add(currentIndex, new TicTacToeEdge(this, neighbor, edgeWeight));
					break;
				}
			}
		}
	}	
	
	public ArrayList<TicTacToeVertex> getNeighbors() {
		ArrayList<TicTacToeVertex> neighbors = new ArrayList<TicTacToeVertex>();
		for (int i = 0; i < this.neighbors.size(); i++) {
			neighbors.add(this.neighbors.get(i).getEnd());
		}
		return(neighbors);
	}
	
	public ArrayList<TicTacToeEdge> getEdges() {
		return(neighbors);
	}
	
	public double getWeight(TicTacToeVertex neighbor) {
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbor == neighbors.get(i).getEnd()) {
				return(neighbors.get(i).getWeight());
			}
		}
		return (-1); //If neighbor is not in neighbors.
	}
	
	public int getBoardNum() {
		return(boardNum);
	}
	
	public void adjustWeight(TicTacToeVertex neighbor, boolean increase) {
		int size = neighbors.size(); //TODO: account for size == 0.
		int neighborIndex = neighbors.indexOf(neighbor);
		double weight;
		if (neighborIndex == -1) {
			weight = (double) 0;
		} else if (neighborIndex == 0) {
			weight = (double)(1 - getWeight(neighbor)) / (size * (size - 1));
		} else {
			weight = (double)(1 - (getWeight(neighbor) - neighbors.get(neighborIndex - 1).getWeight())) / (size * (size - 1));
		}
		for (int i = 1; i < size - 1; i++) {
			if (i >= neighborIndex) {
				if (increase) {
					neighbors.get(i).setWeight(neighbors.get(i).getWeight() + weight * (size - 1 - i));
				} else {
					neighbors.get(i).setWeight(neighbors.get(i).getWeight() - weight * (size - 1 - i));
				}
			} else {
				if (increase) {
					neighbors.get(i).setWeight(neighbors.get(i).getWeight() - weight * i);
				} else {
					neighbors.get(i).setWeight(neighbors.get(i).getWeight() + weight * i);
				}
			}
		}
		//Safety check
		for (int i = 1; i < size - 1; i++) {
			if (neighbors.get(i).getWeight() < neighbors.get(i - 1).getWeight()) {
				neighbors.get(i).setWeight(neighbors.get(i - 1).getWeight());
			} else if (neighbors.get(i).getWeight() > neighbors.get(i + 1).getWeight()){
				neighbors.get(i).setWeight(neighbors.get(i + 1).getWeight());
			}
		}
	}
	
	public void setWeight(TicTacToeVertex neighbor, double weight) {
		//Changes weight and reorders. <- Don't need to reorder - adjust weight preserves ordering.
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbor == neighbors.get(i).getEnd()) {
				neighbors.get(i).setWeight(weight);
			}
		}
	}
}