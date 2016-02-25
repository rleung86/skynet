package skynet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Player {
	

	public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        
        Map<Integer, List<Integer>> mapLink = new HashMap<Integer ,List<Integer>>();
        List<Integer> mapGateAway = new ArrayList<Integer>();
        List<Integer> tmpListMapLink;
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            
            if (mapLink.containsKey(N1))
            	mapLink.get(N1).add(N2);
            else {
            	tmpListMapLink = new ArrayList<Integer>();
            	tmpListMapLink.add(N2);
            	mapLink.put(N1, tmpListMapLink );
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
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn

            Integer[] node = toDestroy(SI, mapLink, mapGateAway);
            
            System.out.println(node[0] + " "+ node[1]);
            
            List<Integer> tmpList = mapLink.get(node[0]);
            tmpList.remove(node[1]);
            mapLink.get(node[1]);
            tmpList.remove(node[0]);
            
        }
    }

	private static Integer[] toDestroy(int SI, Map<Integer, List<Integer>> mapLink, List<Integer> mapGateAway) {
		//on cherche si la node est dans la mapLink
		Integer[] result = null;
		if (!mapGateAway.isEmpty()){
			for (Integer gateAway : mapGateAway) {
				if (mapLink.containsKey(gateAway)) {
					List<Integer> list = mapLink.get(gateAway);
					if (list.contains(SI)) {
						result = new Integer[2];
						result[0]=gateAway;
						result[1]=SI;
						return result;
					} 
				}
			}
			if (result == null){
				//on supprime par défaut le premier GateAway et le premier lien vers GateWay
				result = new Integer[2];
				int i=0;
				while(true) {
					result[0] = mapGateAway.get(i);
					if (!mapLink.get(mapGateAway.get(i)).isEmpty()){
						result[1] = mapLink.get(mapGateAway.get(i)).get(0);
						return result;
					}
				}
			}
			
		}
		return result;
	}
	
	
}
