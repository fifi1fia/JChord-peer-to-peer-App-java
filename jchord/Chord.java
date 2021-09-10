package jchord;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Chord {
        //declare list of nodes
	List<ChordNode> nodeList = new ArrayList<ChordNode>();
	SortedMap<ChordKey, ChordNode> sortedNodeMap = new TreeMap<ChordKey, ChordNode>();
	Object[] sortedKeyArray;
        //add new peer
	public void createNode(String nodeId) throws ChordException {
                //ad peer function
		ChordNode node = new ChordNode(nodeId);
		nodeList.add(node);
		
		if (sortedNodeMap.get(node.getNodeKey()) != null ) {
			throw new ChordException("Duplicated Key: " + node);
		}
		
		sortedNodeMap.put(node.getNodeKey(), node);
	}
        //findKey
	public ChordNode getNode(int i) {
		return (ChordNode) nodeList.get(i);
                
	}

	public ChordNode getSortedNode(int i) {
		if (sortedKeyArray == null) {
			sortedKeyArray = sortedNodeMap.keySet().toArray();
		}
		return (ChordNode) sortedNodeMap.get(sortedKeyArray[i]);
	}
       
}

