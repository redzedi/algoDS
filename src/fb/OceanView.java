package fb;

import java.util.ArrayDeque;

public class OceanView {
	
	public int[] findBuildings(int[] heights) {
        ArrayDeque<Integer> stk = new ArrayDeque<>();
        stk.push(heights.length-1);
        for(int i=heights.length-2 ; i>=0;i-- ){
            if(heights[i]>heights[stk.peek()]){
                stk.push(i);
            }
        }

        int[] res = new int[stk.size()];

        for(int i=0;i<res.length;i++){
            res[i] = stk.pop();
        }
        return res;
        
    }

}
