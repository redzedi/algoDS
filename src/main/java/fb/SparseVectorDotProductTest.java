package fb;

import java.util.BitSet;

class SparseVector {
    private BitSet b;
    private int[] n;
    SparseVector(int[] nums) {
        b = new BitSet(nums.length);
        n = nums;
        for(int i=0;i<nums.length;i++){
            b.set(i);
        }
    }
    
	// Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        BitSet currClone = (BitSet) this.b.clone();
        currClone.and(vec.b);
        int res =  0;
        for(int i=currClone.nextSetBit(0);i != -1; i=currClone.nextSetBit(i)){
            res += (n[i] * vec.n[i]);
            currClone.clear(i);
        }

        return res;


    }
}

public class SparseVectorDotProductTest {

}
