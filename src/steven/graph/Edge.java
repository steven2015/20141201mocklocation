/**
 *
 */
package steven.graph;

/**
 * @author Steven
 *
 */
public class Edge<T extends INode<T>> implements Comparable<Edge<T>>{
	private final Node<T> node;
	private final int distance;

	public Edge(final Node<T> node, final int distance){
		this.node = node;
		this.distance = distance;
	}
	@Override
	public int compareTo(final Edge<T> o){
		return this.distance - o.distance;
	}
	@Override
	public String toString(){
		return this.node + " " + this.distance;
	}
	public final Node<T> getNode(){
		return this.node;
	}
	public final int getDistance(){
		return this.distance;
	}
}
