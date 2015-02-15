/**
 *
 */
package steven.graph;

/**
 * @author Steven
 *
 */
public interface INode<T extends INode<T>>{
	public int getHeuristic(T to);
	public Node<T> getNode();
}
