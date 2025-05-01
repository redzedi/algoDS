package fb;

public class AddStrings {
	
	//https://leetcode.com/problems/add-strings/description/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	public String addStrings(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        int maxlen = Math.max(num1.length(), num2.length());
        int carry = 0;
        for(int i=0;i < maxlen;i++){
           int currSum= 0;
           if(i<num1.length()){
              currSum += num1.charAt(num1.length()-1-i)-'0';
            //  System.out.println(num1+" num1 "+num1.charAt(num1.length()-1-i)+" currSum "+currSum);
           }

           if(i<num2.length()){
              currSum += num2.charAt(num2.length()-1-i)-'0';
             // System.out.println(num2+" num2 "+num2.charAt(num2.length()-1-i)+" currSum "+currSum);
           }
           currSum += carry;
          carry = currSum/10;
          res.append(currSum%10);

        }
        if(carry > 0)
          res.append(carry);
        return res.reverse().toString();
    }
	
	public static void main(String[] args) {
		double avg = (12-5-6+50)/(4*1.0);
		System.out.println(Math.max((12-5-6+50)/(4*1.0), Double.MIN_VALUE));
	}

}
