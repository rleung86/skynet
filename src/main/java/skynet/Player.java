package skynet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Player {

	public static void main(String args[]) {
		Player instancePlayer = new Player();
		Scanner in = new Scanner(System.in);
		int N = in.nextInt(); // the total number of nodes in the level,
								// including the gateways
		int L = in.nextInt(); // the number of links
		int E = in.nextInt(); // the number of exit gateways
		List<Node> nodes = new ArrayList<Node>();
		
		for (int i = 0; i< N; i++) {
			nodes.add(instancePlayer.new Node(i));
		}
			

		Map<Integer, List<Integer>> mapLink = new HashMap<Integer, List<Integer>>();
		List<Integer> mapGateAway = new ArrayList<Integer>();
		List<Integer> tmpListMapLink;
		List<Node> nodeTree;
		for (int i = 0; i < L; i++) {
			int N1 = in.nextInt(); // N1 and N2 defines a link between these
									// nodes
			int N2 = in.nextInt();
			if (mapLink.containsKey(N1))
				mapLink.get(N1).add(N2);
			else {
				tmpListMapLink = new ArrayList<Integer>();
				tmpListMapLink.add(N2);
				mapLink.put(N1, tmpListMapLink);

			}
			if (mapLink.containsKey(N2))
				mapLink.get(N2).add(N1);
			else {
				tmpListMapLink = new ArrayList<Integer>();
				tmpListMapLink.add(N1);
				mapLink.put(N2, tmpListMapLink);
			}
		}
		for (int i = 0; i < E; i++) {
			int EI = in.nextInt(); 
			mapGateAway.add(EI);
		}

		nodeTree = new ArrayList<Node>(); 
		
		constructNodeTree(nodeTree,mapLink, mapGateAway, nodes);

		// game loop
		while (true) {
			int SI = in.nextInt(); 
			
			 Integer[] node = toDestroy(SI, mapLink, mapGateAway, nodeTree);
	            
	            System.out.println(node[0] + " "+ node[1]);
	            
	            List<Integer> tmpList = mapLink.get(node[0]);
	            tmpList.remove(node[1]);
	            tmpList = mapLink.get(node[1]);
	            tmpList.remove(node[0]);
	            
	            if (nodes.get(node[0]).idNodeAjoute.size()>1 )
            		nodes.get(node[0]).idNodeAjoute.remove(0);
	            else 
	            	nodeTree.remove(nodes.get(node[0]));

		}
	}

	public class Node {
		List<Integer> idNodeAjoute;
		int id;
		public Node(int id) {
			this.id = id;
			idNodeAjoute = new ArrayList<Integer>();
		}
		
		public List<Integer> getIdNodeAjoute() {
			return idNodeAjoute;
		}
		public void setIdNodeAjoute(List<Integer> idNodeAjoute) {
			this.idNodeAjoute = idNodeAjoute;
		}

	}

	private static void constructNodeTree(List<Node> nodeTree, Map<Integer, List<Integer>> mapLink,
			List<Integer> mapGateAway, List<Node> nodes) {
		
		for ( Integer gaId : mapGateAway) {
			if ( mapLink.containsKey(gaId)) {
				//on itère autours des node enfants de GA et on rajoute le noeud 
				for ( int i =0; i < mapLink.get(gaId).size(); i++) {
					Integer noeudEnfantId = mapLink.get(gaId).get(i);
						
						nodes.get(noeudEnfantId).idNodeAjoute.add(gaId);
						
						if(!nodeTree.contains(nodes.get(noeudEnfantId))) {
							nodeTree.add(nodes.get(noeudEnfantId));
						}
				}
			}
		}
	}
	
	
	private static Integer[] toDestroy(int SI, Map<Integer, List<Integer>> mapLink, List<Integer> mapGateAway,
			List<Node> nodeTree) {
		Integer[] result = null;

		if (!mapGateAway.isEmpty()) {
			for (Integer gateAway : mapGateAway) {
				if (mapLink.containsKey(gateAway)) {
					List<Integer> listConnectedToGA = mapLink.get(gateAway);
					if (listConnectedToGA.contains(new Integer(SI))) {
						result = new Integer[2];
						result[0] = gateAway;
						result[1] = SI;
						return result;
					}
				}
			}
			if (result == null) {
				result = new Integer[2];
				
				Node selectedNode = nodeTree.get(0);
				for (int i = 0; i < nodeTree.size(); i++) {
					if (selectedNode.idNodeAjoute.size() < nodeTree.get(i).idNodeAjoute.size())
						selectedNode = nodeTree.get(i);
				}
				result[0] = selectedNode.id;
				result[1] = selectedNode.idNodeAjoute.get(0);
			}
		}
		return result;
	}

}
