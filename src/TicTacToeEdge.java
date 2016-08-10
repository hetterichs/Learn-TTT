
public class TicTacToeEdge {
	private TicTacToeVertex startVertex;
	private TicTacToeVertex endVertex; 
	private double weight;
	
	public TicTacToeEdge(TicTacToeVertex startVertex, TicTacToeVertex endVertex, double weight) {
		this.startVertex = startVertex;
		this.endVertex = endVertex; 
		this.weight = weight;
	}
	
	public TicTacToeVertex getStart() {
		return(startVertex);
	}
	
	public TicTacToeVertex getEnd() {
		return(endVertex);
	}
	
	public double getWeight() {
		return(weight);
	}
	
	public void setWeight(double newWeight) {
		this.weight = newWeight;
		if (this.weight > 1) {
			this.weight = 1;
		} else if (this.weight < 0) {
			this.weight = 0;
		}
	}
}