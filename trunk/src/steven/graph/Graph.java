/**
 *
 */
package steven.graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author Steven
 *
 */
public class Graph<T extends INode<T>>{
	private final List<Node<T>> nodes = new ArrayList<Node<T>>();
	@SuppressWarnings("unchecked")
	private Node<T>[] cache = new Node[0];

	public Graph(){
	}
	public void addNode(final Node<T> node){
		this.nodes.add(node);
	}
	public void addEdge(final Node<T> node1, final Node<T> node2, final int distance){
		node1.addEdge(node2, distance);
		node2.addEdge(node1, distance);
	}
	@SuppressWarnings("unchecked")
	public void makeCache(){
		this.cache = new Node[this.nodes.size()];
		for(int i = 0; i < this.cache.length; i++){
			final Node<T> node = this.nodes.get(i);
			this.cache[i] = node;
			node.makeCache();
		}
	}
	@SuppressWarnings("unchecked")
	public static final <T extends INode<T>>Node<T>[] getPath(final Node<T> from, final Node<T> to){
		final Set<Node<T>> open = new HashSet<Node<T>>();
		final Set<Node<T>> closed = new HashSet<Node<T>>();
		final PriorityQueue<Node<T>> queue = new PriorityQueue<Node<T>>();
		from.distance = 0;
		from.heuristic = from.getContent().getHeuristic(to.getContent());
		from.cost = from.heuristic;
		from.parent = null;
		open.add(from);
		queue.add(from);
		while(queue.size() > 0){
			final Node<T> nearest = queue.poll();
			if(nearest == to){
				final List<Node<T>> path = new ArrayList<Node<T>>();
				Node<T> tmp = nearest;
				while(tmp != null){
					path.add(tmp);
					tmp = tmp.parent;
				}
				Collections.reverse(path);
				return path.toArray(new Node[path.size()]);
			}
			open.remove(nearest);
			closed.add(nearest);
			for(final Edge<T> edge : nearest.getEdges()){
				final Node<T> neighbor = edge.getNode();
				if(closed.contains(neighbor) == false){
					final int thisDistance = nearest.distance + edge.getDistance();
					if(open.contains(neighbor)){
						if(neighbor.distance > thisDistance){
							neighbor.distance = thisDistance;
							neighbor.cost = thisDistance + neighbor.heuristic;
							neighbor.parent = nearest;
						}
					}else{
						neighbor.distance = thisDistance;
						neighbor.heuristic = neighbor.getContent().getHeuristic(to.getContent());
						neighbor.cost = thisDistance + neighbor.heuristic;
						neighbor.parent = nearest;
						open.add(neighbor);
						queue.add(neighbor);
					}
				}
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static final <T extends INode<T>>Edge<T>[] getDistances(final Node<T> from, final Node<T>... tos){
		final Set<Node<T>> remainings = new HashSet<Node<T>>();
		final Edge<T>[] results = new Edge[tos.length];
		for(final Node<T> to : tos){
			remainings.add(to);
		}
		final Set<Node<T>> open = new HashSet<Node<T>>();
		final Set<Node<T>> closed = new HashSet<Node<T>>();
		final PriorityQueue<Node<T>> queue = new PriorityQueue<Node<T>>();
		from.distance = 0;
		from.heuristic = Graph.getHeuristic(from, tos, remainings);
		from.cost = from.heuristic;
		from.parent = null;
		open.add(from);
		queue.add(from);
		while(queue.size() > 0){
			final Node<T> nearest = queue.poll();
			if(remainings.remove(nearest)){
				results[tos.length - remainings.size() - 1] = new Edge<T>(nearest, nearest.distance);
				if(remainings.size() == 0){
					return results;
				}
			}
			open.remove(nearest);
			closed.add(nearest);
			for(final Edge<T> edge : nearest.getEdges()){
				final Node<T> neighbor = edge.getNode();
				if(closed.contains(neighbor) == false){
					final int thisDistance = nearest.distance + edge.getDistance();
					if(open.contains(neighbor)){
						if(neighbor.distance > thisDistance){
							neighbor.distance = thisDistance;
							neighbor.cost = thisDistance + neighbor.heuristic;
							neighbor.parent = nearest;
						}
					}else{
						neighbor.distance = thisDistance;
						neighbor.heuristic = Graph.getHeuristic(neighbor, tos, remainings);
						neighbor.cost = thisDistance + neighbor.heuristic;
						neighbor.parent = nearest;
						open.add(neighbor);
						queue.add(neighbor);
					}
				}
			}
		}
		return results;
	}
	private static final <T extends INode<T>>int getHeuristic(final Node<T> current, final Node<T>[] tos, final Set<Node<T>> remainings){
		int heuristic = -1;
		for(final Node<T> to : tos){
			if(remainings.contains(to)){
				final int h = current.getContent().getHeuristic(to.getContent());
				if(heuristic < 0 || h < heuristic){
					heuristic = h;
				}
			}
		}
		return heuristic;
	}
	@SuppressWarnings("unchecked")
	public static final <T extends INode<T>>T[] getPath(final INode<T> from, final INode<T> to, final Class<T> clazz){
		final Set<Node<T>> open = new HashSet<Node<T>>();
		final Set<Node<T>> closed = new HashSet<Node<T>>();
		final PriorityQueue<Node<T>> queue = new PriorityQueue<Node<T>>();
		final Node<T> fromNode = from.getNode();
		fromNode.distance = 0;
		fromNode.heuristic = from.getHeuristic((T)to);
		fromNode.cost = fromNode.heuristic;
		fromNode.parent = null;
		open.add(fromNode);
		queue.add(fromNode);
		while(queue.size() > 0){
			final Node<T> nearest = queue.poll();
			if(nearest == to.getNode()){
				final List<Node<T>> path = new ArrayList<Node<T>>();
				Node<T> tmp = nearest;
				while(tmp != null){
					path.add(tmp);
					tmp = tmp.parent;
				}
				final T[] results = (T[])Array.newInstance(clazz, path.size());
				for(int i = 0, j = results.length - 1; j >= 0; i++, j--){
					results[i] = path.get(j).getContent();
				}
				return results;
			}
			open.remove(nearest);
			closed.add(nearest);
			for(final Edge<T> edge : nearest.getEdges()){
				final Node<T> neighbor = edge.getNode();
				if(closed.contains(neighbor) == false){
					final int thisDistance = nearest.distance + edge.getDistance();
					if(open.contains(neighbor)){
						if(neighbor.distance > thisDistance){
							neighbor.distance = thisDistance;
							neighbor.cost = thisDistance + neighbor.heuristic;
							neighbor.parent = nearest;
						}
					}else{
						neighbor.distance = thisDistance;
						neighbor.heuristic = neighbor.getContent().getHeuristic((T)to);
						neighbor.cost = thisDistance + neighbor.heuristic;
						neighbor.parent = nearest;
						open.add(neighbor);
						queue.add(neighbor);
					}
				}
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static final <T extends INode<T>>Edge<T>[] getDistances(final INode<T> from, final INode<T>... tos){
		final Set<Node<T>> remainings = new HashSet<Node<T>>();
		final Edge<T>[] results = new Edge[tos.length];
		for(final INode<T> to : tos){
			remainings.add(to.getNode());
		}
		final Set<Node<T>> open = new HashSet<Node<T>>();
		final Set<Node<T>> closed = new HashSet<Node<T>>();
		final PriorityQueue<Node<T>> queue = new PriorityQueue<Node<T>>();
		final Node<T> fromNode = from.getNode();
		fromNode.distance = 0;
		fromNode.heuristic = Graph.getHeuristic(fromNode, tos, remainings);
		fromNode.cost = fromNode.heuristic;
		fromNode.parent = null;
		open.add(fromNode);
		queue.add(fromNode);
		while(queue.size() > 0){
			final Node<T> nearest = queue.poll();
			if(remainings.remove(nearest)){
				results[tos.length - remainings.size() - 1] = new Edge<T>(nearest, nearest.distance);
				if(remainings.size() == 0){
					return results;
				}
			}
			open.remove(nearest);
			closed.add(nearest);
			for(final Edge<T> edge : nearest.getEdges()){
				final Node<T> neighbor = edge.getNode();
				if(closed.contains(neighbor) == false){
					final int thisDistance = nearest.distance + edge.getDistance();
					if(open.contains(neighbor)){
						if(neighbor.distance > thisDistance){
							neighbor.distance = thisDistance;
							neighbor.cost = thisDistance + neighbor.heuristic;
							neighbor.parent = nearest;
						}
					}else{
						neighbor.distance = thisDistance;
						neighbor.heuristic = Graph.getHeuristic(neighbor, tos, remainings);
						neighbor.cost = thisDistance + neighbor.heuristic;
						neighbor.parent = nearest;
						open.add(neighbor);
						queue.add(neighbor);
					}
				}
			}
		}
		return results;
	}
	private static final <T extends INode<T>>int getHeuristic(final Node<T> current, final INode<T>[] tos, final Set<Node<T>> remainings){
		int heuristic = -1;
		for(final INode<T> to : tos){
			if(remainings.contains(to.getNode())){
				@SuppressWarnings("unchecked")
				final int h = current.getContent().getHeuristic((T)to);
				if(heuristic < 0 || h < heuristic){
					heuristic = h;
				}
			}
		}
		return heuristic;
	}
	@SuppressWarnings("unchecked")
	public static final <T extends INode<T>>Edge<T>[] getNearNodes(final Node<T> from, final double maxDistance, final int maxCount){
		final List<Edge<T>> results = new ArrayList<Edge<T>>();
		final Set<Node<T>> open = new HashSet<Node<T>>();
		final Set<Node<T>> closed = new HashSet<Node<T>>();
		final PriorityQueue<Node<T>> queue = new PriorityQueue<Node<T>>();
		closed.add(from);
		for(final Edge<T> edge : from.getEdges()){
			final Node<T> neighbor = edge.getNode();
			final int distance = edge.getDistance();
			if(distance <= maxDistance){
				neighbor.distance = distance;
				neighbor.cost = distance;
				open.add(neighbor);
				queue.add(neighbor);
			}
		}
		while(queue.size() > 0){
			final Node<T> nearest = queue.poll();
			results.add(new Edge<T>(nearest, nearest.distance));
			if(results.size() >= maxCount){
				final Edge<T>[] edges = new Edge[results.size()];
				for(int i = 0; i < edges.length; i++){
					edges[i] = results.get(i);
				}
				return edges;
			}
			open.remove(nearest);
			closed.add(nearest);
			for(final Edge<T> edge : nearest.getEdges()){
				final Node<T> neighbor = edge.getNode();
				if(closed.contains(neighbor) == false){
					final int thisDistance = nearest.distance + edge.getDistance();
					if(thisDistance <= maxDistance){
						if(open.contains(neighbor)){
							if(neighbor.distance > thisDistance){
								neighbor.distance = thisDistance;
								neighbor.cost = thisDistance;
							}
						}else{
							neighbor.distance = thisDistance;
							neighbor.cost = thisDistance;
							open.add(neighbor);
							queue.add(neighbor);
						}
					}
				}
			}
		}
		final Edge<T>[] edges = new Edge[results.size()];
		for(int i = 0; i < edges.length; i++){
			edges[i] = results.get(i);
		}
		return edges;
	}
	public Node<T>[] getNodes(){
		return this.cache;
	}
}
