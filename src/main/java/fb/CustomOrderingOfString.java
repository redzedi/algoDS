package fb;

import java.util.Arrays;

public class CustomOrderingOfString {
	
	
	public String customSortString(String order, String s) {

        String[] orderArr = order.split("");

        int[] sFreqs = new int[26];

        Arrays.fill(sFreqs,-1);

        s.chars().forEach(c-> sFreqs[c-'a']++);

        StringBuilder res = new StringBuilder();

        order.chars().forEach(o->{
            if(sFreqs[o-'a'] != -1){
                res.repeat(o,sFreqs[o-'a']+1);
                sFreqs[o-'a']=-1;
            }
        });

         for(int i=0;i<sFreqs.length;i++){
            if(sFreqs[i] != -1){
                res.repeat((char)(i+'a'),sFreqs[i]+1);
            }
         }

        return res.toString();
        
    }
	
	
	public static void main(String[] args) {
		
		"dasfsa".chars().forEach(c->System.out.println(" "+(c-'a')));
		
		StringBuilder sb = new StringBuilder();
		sb.append((char)(3+'a')) ;
		
		sb.repeat((char)(4+'a'), 3);
		
		System.out.println(sb.toString());
		
	}

}
