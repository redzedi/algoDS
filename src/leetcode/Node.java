package leetcode;

import java.util.ArrayList;
import java.util.List;

class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
	@Override
	public String toString() {
		return "Node [val=" + val + ", neighbors=" + neighbors + "]";
	}
	
	
	public static Node createGraph(List<List<Integer>> ns) {
		ArrayList<Node> res = new ArrayList<>(ns.size());
		for(int i=0;i<ns.size();i++) {
			res.add(i, new Node(i));
		}
		
		for(int i=0;i<ns.size();i++) {
			
			for(Integer x:ns.get(i)) {
				res.get(i).neighbors.add(res.get(x-1));
			}
		}
		return res.get(0);
	}
    
    
}
