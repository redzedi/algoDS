package fb;

public class MatrixSearch {
	
	//https://leetcode.com/problems/search-a-2d-matrix/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length , n = matrix[0].length;
         int rlo=0, rhi=m-1, clo=0,chi=n-1;

         while(rlo<=rhi){
            int rmid = (rlo+rhi)/2;

            if(target == matrix[rmid][0]){
                return true;
            }else if( target > matrix[rmid][0]){
                rlo = rmid+1;
            }else{
                rhi = rmid-1;
            }
         }

         if(rlo == 0){
            return false;
         }
         int r =  rlo-1;

         while(clo <=chi){
            int cmid = (clo+chi)/2;

            if(matrix[r][cmid] == target){
                return true;
            }else if(matrix[r][cmid] < target){
                clo = cmid+1;
            }else{
                chi = cmid-1;
            }
         }

         return false;
        
    }

}
