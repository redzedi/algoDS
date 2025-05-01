package fb;

import java.util.BitSet;
import java.util.HashMap;

class LRUCache {

    class Node{
        public final int k;
        public int val;
        public Node prev;
        public Node next;

        Node(int key ,int val){
            this.val = val;
            this.k = key;
        }
    }

    private Node head;

    private Node tail;

    private HashMap<Integer,Node> mp ;

    private int c;

    private int currSize;

    public LRUCache(int capacity) {
        this.c = capacity;
        mp = new HashMap<>();
    }
    
    public int get(int key) {

        Node currValNd = mp.get(key);

        if(currValNd == null){
            return -1;
        }

        removeFromCurrPositionAndAddToHead(currValNd);

        return currValNd.val;
        
    }
    
    public void put(int key, int value) {

        if(!mp.containsKey(key)){
            Node nd = new Node(key,value);
           if(++currSize>c){
               if(tail != null){
                mp.remove(tail.k);
                tail = tail.prev;
                currSize--;
                
               }
           }

            addToHead(nd);
            if(tail == null){
                tail = nd;
            }
            mp.put(key, nd);
        }else{
            Node currValNd = mp.get(key);
            currValNd.val = value;
            //remove from curr position
            //add to head
           removeFromCurrPositionAndAddToHead(currValNd);
        }
        
    }

    private void removeFromCurrPositionAndAddToHead(Node currValNd){
         if(currValNd != head){
                currValNd.prev.next = currValNd.next;
                if(currValNd.next != null){
                    currValNd.next.prev = currValNd.prev;
                }

               if(currValNd == tail){
                 tail = currValNd.prev;
               }

               currValNd.prev = null;
               currValNd.next = null;

               

               addToHead(currValNd);

            }
    }

    private void addToHead(Node nd){
         if(head != null){
                 nd.next = head;
            head.prev = nd;
            }
           
            head = nd;
    }
    
    public static void main(String[] args) {
		BitSet b = new BitSet(5);
	}
}


