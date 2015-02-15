/**
 *
 */
package steven.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven
 *
 */
public class Node<T extends INode<T>> implements Comparable<Node<T>>{
	private final T content;
	private final List<Edge<T>> edges = new ArrayList<Edge<T>>();
	@SuppressWarnings("unchecked")
	private Edge<T>[] cache = new Edge[0];
	protected int distance;
	protected int heuristic;
	protected int cost;
	protected Node<T> parent;

	public Node(final T content){
		this.content = content;
	}
	@Override
	public int compareTo(final Node<T> o){
		return this.cost - o.cost;
	}
	public void addEdge(final Edge<T> edge){
		this.edges.add(edge);
	}
	public void addEdge(final Node<T> neighbor, final int distance){
		this.addEdge(new Edge<T>(neighbor, distance));
	}
	@SuppressWarnings("unchecked")
	public void makeCache(){
		this.cache = new Edge[this.edges.size()];
		for(int i = 0; i < this.cache.length; i++){
			this.cache[i] = this.edges.get(i);
		}
		Arrays.sort(this.cache);
	}
	public final T getContent(){
		return this.content;
	}
	public Edge<T>[] getEdges(){
		return this.cache;
	}
}
