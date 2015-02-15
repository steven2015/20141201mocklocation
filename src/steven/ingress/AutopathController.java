/**
 *
 */
package steven.ingress;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import steven.graph.Edge;
import steven.graph.Graph;
import steven.graph.Node;
import android.location.Location;

/**
 * @author Steven
 *
 */
public class AutopathController{
	private static final List<LocationNode> NODES = new ArrayList<LocationNode>();
	private static final Map<String, LocationNode[]> AREA_MAP = new HashMap<String, LocationNode[]>();
	private static String[] AREAS;
	private static final Graph<LocationNode> GRAPH = new Graph<LocationNode>();

	public AutopathController(){
	}
	public static final void main(final String[] args) throws Exception{
		InputStream nodesJs = null;
		InputStream linksJs = null;
		try{
			nodesJs = new FileInputStream("E:\\Java\\Fusion\\res\\raw\\nodes.js");
			linksJs = new FileInputStream("E:\\Java\\Fusion\\res\\raw\\links.js");
			AutopathController.initialize(nodesJs, linksJs);
		}finally{
			nodesJs.close();
			linksJs.close();
		}
		final LocationNode 宏景花園 = AutopathController.getNearestNode(22.34098, 114.207872);
		final LocationNode 嘉峰臺 = AutopathController.getNearestNode(22.34049, 114.20995);
		final LocationNode 健體站 = AutopathController.getNearestNode(22.34054, 114.21116);
		final LocationNode 風車 = AutopathController.getNearestNode(22.34272, 114.20617);
		final LocationNode god = AutopathController.getNearestNode(22.34517, 114.20247);
		for(final LocationNode node : AutopathController.getPath(嘉峰臺, 風車)){
			System.out.println(node);
		}
		System.out.println();
		for(final LocationNode node : AutopathController.getPath(健體站, 風車)){
			System.out.println(node);
		}
		System.out.println();
		for(final LocationNode node : AutopathController.getPath(健體站, god)){
			System.out.println(node);
		}
		System.out.println();
		for(final Edge<LocationNode> edge : AutopathController.getAreaNodes("富山", 嘉峰臺.getLatitude(), 嘉峰臺.getLongitude())){
			System.out.println(edge.getNode().getContent() + " " + edge.getDistance());
		}
		System.out.println();
		for(final Edge<LocationNode> edge : AutopathController.getNearNodes(宏景花園, 500, 1000)){
			System.out.println(edge.getNode().getContent() + " " + edge.getDistance());
		}
	}
	public static final void initialize(final InputStream nodesJs, final InputStream linksJs) throws IOException{
		final Map<String, List<LocationNode>> areaMap = new HashMap<String, List<LocationNode>>();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try{
			isr = new InputStreamReader(nodesJs);
			br = new BufferedReader(isr);
			// var nodes = [];
			br.readLine();
			// nodes[nodes.length] = ["富山", "賓霞洞", 22.34155, 114.20847];
			String line = null;
			while((line = br.readLine()) != null){
				if(line.trim().length() > 0){
					final int i1 = line.indexOf('\"');
					final int i2 = line.indexOf('\"', i1 + 1);
					final String area = line.substring(i1 + 1, i2);
					final int i3 = line.indexOf('\"', i2 + 1);
					final int i4 = line.indexOf('\"', i3 + 1);
					final String name = line.substring(i3 + 1, i4);
					final int i5 = line.indexOf(',', i4 + 1);
					final int i6 = line.indexOf(',', i5 + 1);
					final double latitude = Double.parseDouble(line.substring(i5 + 1, i6).trim());
					final int i7 = line.indexOf(']', i6 + 1);
					final double longitude = Double.parseDouble(line.substring(i6 + 1, i7).trim());
					final LocationNode node = new LocationNode(area, name, latitude, longitude);
					AutopathController.NODES.add(node);
					List<LocationNode> nodes = areaMap.get(node.getArea());
					if(nodes == null){
						nodes = new ArrayList<LocationNode>();
						areaMap.put(node.getArea(), nodes);
					}
					nodes.add(node);
					final Node<LocationNode> n = new Node<LocationNode>(node);
					node.setNode(n);
					AutopathController.GRAPH.addNode(n);
				}
			}
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(final IOException e){
				}
			}
			if(isr != null){
				try{
					isr.close();
				}catch(final IOException e){
				}
			}
		}
		try{
			isr = new InputStreamReader(linksJs);
			br = new BufferedReader(isr);
			// var links = [];
			br.readLine();
			// links[links.length] = [5, 0];
			String line = null;
			while((line = br.readLine()) != null){
				if(line.trim().length() > 0){
					final int i1 = line.indexOf('=');
					final int i2 = line.indexOf('[', i1 + 1);
					final int i3 = line.indexOf(',', i2 + 1);
					final int index1 = Integer.parseInt(line.substring(i2 + 1, i3).trim());
					final int i4 = line.indexOf(']', i3 + 1);
					final int index2 = Integer.parseInt(line.substring(i3 + 1, i4).trim());
					final LocationNode node1 = AutopathController.NODES.get(index1);
					final LocationNode node2 = AutopathController.NODES.get(index2);
					AutopathController.GRAPH.addEdge(node1.getNode(), node2.getNode(), node1.distanceTo(node2));
				}
			}
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(final IOException e){
				}
			}
			if(isr != null){
				try{
					isr.close();
				}catch(final IOException e){
				}
			}
		}
		AutopathController.GRAPH.makeCache();
		for(final Entry<String, List<LocationNode>> entry : areaMap.entrySet()){
			AutopathController.AREA_MAP.put(entry.getKey(), entry.getValue().toArray(new LocationNode[entry.getValue().size()]));
		}
		AutopathController.AREAS = AutopathController.AREA_MAP.keySet().toArray(new String[AutopathController.AREA_MAP.size()]);
		Arrays.sort(AutopathController.AREAS);
	}
	public static final LocationNode getNearestNode(final double latitude, final double longitude){
		LocationNode n = null;
		int d = Integer.MAX_VALUE;
		for(final LocationNode node : AutopathController.NODES){
			final int dd = node.distanceTo(latitude, longitude);
			if(dd < d){
				d = dd;
				n = node;
			}
		}
		return n;
	}
	public static final Edge<LocationNode>[] getNearNodes(final LocationNode node, final int maxDistance, final int maxCount){
		return Graph.getNearNodes(node.getNode(), maxDistance, maxCount);
	}
	public static final double[] getNextPoint(final LocationNode to, final Location current, final long time, final double speed){
		final int d = to.distanceTo(current.getLatitude(), current.getLongitude());
		if(d < speed * time / 1000){
			return null;
		}
		final double factor = speed * time / 1000 / d;
		final double latitude = current.getLatitude() + (to.getLatitude() - current.getLatitude()) * factor * (1 + (Math.random() - 0.5) * 0.4);
		final double longitude = current.getLongitude() + (to.getLongitude() - current.getLongitude()) * factor * (1 + (Math.random() - 0.5) * 0.4);
		return new double[]{latitude, longitude};
	}
	public static final LocationNode[] getPath(final double latitude, final double longitude, final LocationNode to){
		return AutopathController.getPath(AutopathController.getNearestNode(latitude, longitude), to);
	}
	public static final LocationNode[] getPath(final LocationNode from, final LocationNode to){
		final LocationNode[] path = Graph.getPath(from, to, LocationNode.class);
		if(path == null){
			return new LocationNode[]{to};
		}else{
			return path;
		}
	}
	public static final String[] getAreas(){
		return AutopathController.AREAS.clone();
	}
	public static final Edge<LocationNode>[] getAreaNodes(final String area, final double currentLatitude, final double currentLongitude){
		return Graph.getDistances(AutopathController.getNearestNode(currentLatitude, currentLongitude), AutopathController.AREA_MAP.get(area));
	}
}
