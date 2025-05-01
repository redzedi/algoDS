package fb;

public class LongestCommonPrefix {
	
	//https://leetcode.com/problems/longest-common-prefix/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	public String longestCommonPrefix(String[] strs) {
        String res = strs[0];
        for(int i=1;i<strs.length;i++){
            res = lcp(res,strs[i]);
            if("".equals(res)){
                break;
            }
        }
        return res;
    }

    private String lcp(String s1 , String s2){

        int minLen = s1.length()<s2.length()?s1.length():s2.length();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<minLen;i++){
            if(s1.charAt(i) != s2.charAt(i)){
                break;
            }
            sb.append(s1.charAt(i));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		System.out.println('9'-'0');
	}

}
