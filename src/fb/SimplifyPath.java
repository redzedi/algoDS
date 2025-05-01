package fb;

import java.util.ArrayDeque;
import java.util.Arrays;

public class SimplifyPath {
	
	 public String simplifyPath(String path) {
	        String[] parts = path.splitWithDelimiters("/",-1);
	      
	       System.out.println(Arrays.asList(parts));
	       
	        //StringBuilder sb = new StringBuilder();
	        
	        ArrayDeque<String> stk = new ArrayDeque<>();
	        

	        for(int i=0; i<parts.length;i++){
             if(parts[i].trim().isBlank()){
	                    continue;
	                } else if(i==0 && !"/".equals(parts[i])){
	            	stk.push("/");
	            	stk.push(parts[i]);
	            }else if( "/".equals(parts[i])){
	              
	                if( !( (i== parts.length-1 && parts.length > 1) || ( "/".equals(stk.peek())))){
	                	stk.push(parts[i]);
	                }

	            }else if( ".".equals(parts[i])){
	                      continue;
	                }else if( "..".equals(parts[i])){
	                  
	                      if(stk.size()>2){
	                        stk.pop();
	                        stk.pop();
	                        
	                      }
	                }else if(parts[i].trim().isBlank()){
	                    continue;
	                } else{
	                	stk.push(parts[i]);
	            }
	        }
	        if("/".equals( stk.peek()) && stk.size()>1){
	            stk.pop();
	        }
	        
	        StringBuilder sb = new StringBuilder();
	        
	        while(!stk.isEmpty()) {
	           sb.append(stk.removeLast()) ;
	        }
	        
	        return sb.toString();

	    }
	}
	

