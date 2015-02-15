/**
 *
 */
package steven.ingress;

import steven.graph.INode;
import steven.graph.Node;
import android.location.Location;

/**
 * @author Steven
 *
 */
public class LocationNode implements INode<LocationNode>{
	private final String area;
	private final String name;
	private final double latitude;
	private final double longitude;
	private Node<LocationNode> node;

	public LocationNode(final String area, final String name, final double latitude, final double longitude){
		this.area = area;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	@Override
	public String toString(){
		return this.area + "-" + this.name;
	}
	@Override
	public int getHeuristic(final LocationNode to){
		return this.distanceTo(to.latitude, to.longitude);
	}
	@Override
	public Node<LocationNode> getNode(){
		return this.node;
	}
	public int distanceTo(final LocationNode node){
		return this.distanceTo(node.latitude, node.longitude);
	}
	public int distanceTo(final double latitude, final double longitude){
		final float[] distance = new float[1];
		Location.distanceBetween(this.latitude, this.longitude, latitude, longitude, distance);
		return (int)distance[0];
	}
	public float bearingTo(final LocationNode node){
		return this.bearingTo(node.latitude, node.longitude);
	}
	public float bearingTo(final double latitude, final double longitude){
		final float[] results = new float[2];
		Location.distanceBetween(this.latitude, this.longitude, latitude, longitude, results);
		return (int)results[1];
	}
	public final String getName(){
		return this.name;
	}
	public final double getLatitude(){
		return this.latitude;
	}
	public final double getLongitude(){
		return this.longitude;
	}
	public final String getArea(){
		return this.area;
	}
	public final void setNode(final Node<LocationNode> node){
		this.node = node;
	}
}
