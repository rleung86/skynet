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
			int EI = in.nextInt(); // the index of a gateway node
			mapGateAway.add(EI);
		}

		// game loop
		while (true) {
			int SI = in.nextInt(); // The index of the node on which the Skynet
									// agent is positioned this turn
			nodeTree = new ArrayList<Node>(); // la liste de ttes les
												// possibilités

			Node nodeSI = instancePlayer.new Node(SI);
			// on récupère la liste des connections de ce SI, on itere pour
			// chaque connection et on crée autant de node qu'il y a d'element
			// fils
			constructNodeTree(nodeTree, nodeSI, mapLink, SI, mapGateAway);

			Node tmpNode = nodeTree.get(0);
			for (Node node : nodeTree) {
				if (node.idNodeAjoute.size() < tmpNode.idNodeAjoute.size()) {
					tmpNode = node;
				}
			}

			System.out.println(tmpNode.id + " " + tmpNode.idNodeAjoute.get(0));

			List<Integer> tmpList = mapLink.get(tmpNode.id);
			tmpList.remove(tmpNode.idNodeAjoute.get(0));
			mapLink.get(tmpNode.idNodeAjoute.get(0));
			tmpList.remove(tmpNode.id);

		}
	}

	public class Node {
		List<Integer> idNodeAjoute;

		int id;

		public Node(int id) {
			this.id = id;
			idNodeAjoute = new ArrayList<Integer>();
		}

	}

	private static void constructNodeTree(List<Node> nodeTree, Node nodeSI, Map<Integer, List<Integer>> mapLink,
			Integer nodeFilleId, List<Integer> mapGateAway) {
		if (mapGateAway.contains(nodeFilleId)) {
			nodeTree.add(nodeSI);
		} else {
			// on itere autours de la liste des connections du noeud
			List<Integer> nodesfillesList = mapLink.get(new Integer(nodeFilleId));

			if (!nodesfillesList.isEmpty()) {
				for (Integer linkNodeFille : nodesfillesList) {
					nodeSI.idNodeAjoute.add(linkNodeFille);
					constructNodeTree(nodeTree, nodeSI, mapLink, linkNodeFille, mapGateAway);
				}
			}

		}
	}

}
