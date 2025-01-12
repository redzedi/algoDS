package leetcode.dp;

class ClimbStairs {
    public int climbStairs(int n) {

        if(n<=2){
          return n;
        }

        
        int secondLast = 1;
        int last = 2;

        for(int i=2;i<n;i++){
            int tmp=last;
            last += secondLast;
            secondLast = tmp;
            
            
        }

        return last;
    
        
    }
}

