package com.sapient.csv.csvdatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;



public class queryExecute {
	  List<String> col= new ArrayList<String> ();
	     LinkedHashMap<String,ArrayList<Object>> map = null;
	     int row;
	 	 ArrayList<Integer> merged_result =null;
	     Set<String> key= null;
	     String arr[][]=null;
		 HashMap<String,Integer> index= new HashMap<String, Integer>();
		 ArrayList<String> containing_functions=new ArrayList <String>();

		 
		 
		 ArrayList<String> selectedFields=new ArrayList<String> ();
		 ArrayList<ArrayList<String>> where_fields = new ArrayList<ArrayList<String>>();//where condition
 		ArrayList<String> logicalOperators =new ArrayList<String>();
 		String order_by="";
 		String group_by;

 		
	     queryExecute(ArrayList<String> selectedFields, LinkedHashMap<String,ArrayList<Object>> map,ArrayList<ArrayList<String>> where_fields, 	ArrayList<String> logicalOperators,String order_by,ArrayList<String> containing_functions,String group_by, int row){
	    	 super();
	    	 this.selectedFields=selectedFields;
	    	 this.map=map;
	    	 this.row=row;
	    	 key = map.keySet();
	    	 arr= new String[row+1][key.size()];
	    	this.where_fields=where_fields;
	    	this.logicalOperators=logicalOperators;
	    	 this.order_by=order_by;
	    	 merged_result = new ArrayList<Integer>();
	    	 this.containing_functions=containing_functions;
	    	 this.group_by=group_by;
	    	 for(int i=1;i<=69;i++)
	    		 merged_result.add((Integer)i);
	     }
	     
	 	void matrixPopulate() {
		    arr=new String[row+1][key.size()];
		    int c=0;
		    int ind = 0;
		    for(String each : key) {
		    	index.put(each, ind);
		    	ind++;
		    	arr[0][c]=each;
		    	 int r=1;
		    	ArrayList<Object> obj=map.get(each);
		    	for(Object ele : obj) {
		    		String s =(String)ele;
		    		String s1[]=s.split(" ");
		    		s="";
		    		for(String s2 : s1) {
		    			s=s+s2;
		    		}
		    		arr[r][c]=s;
		    		r++;
		    	}
		    	c++;
		    }
		    
	 	}
	 	
	void whereExecute() {
		ArrayList<ArrayList<Integer>> id = new ArrayList<ArrayList<Integer>>();
	    for(ArrayList<String> cond : where_fields) {
	    	int col=0;
	    	for(int i=0 ; i< key.size();i++) {
	    		if(arr[0][i].equals(cond.get(0))) {
	    			col=i;
	    			break;	
	    		}
	    	}
	    		
	    	 ArrayList<Integer> id_1=new ArrayList<Integer>();
	   		 for(int i=1;i<row+1;i++) {
	   			 //System.out.println(arr[i][col]);
	   			 if(cond.get(1).equals("=")) {
	   				 if( Pattern.matches( "[0-9]+", arr[i][col])) {
	   					 int a=Integer.parseInt(arr[i][col]);
	   					 int b=Integer.parseInt(cond.get(2));
	   					 if(a == b) {
	   						 id_1.add(i);
	   						// System.out.println("i added " + i);
	   					 }
	   				 }
	   				 else {
	   					 String a = arr[i][col].toLowerCase();
	   					 String b = cond.get(2).toLowerCase();
	   					// System.out.println("a :" + a +" b: "+ b);
	   					// System.out.println(a.equals(b));
	   					 if(a.equals(b)) {
	   						 id_1.add(i);
	   						// System.out.println("i added " +i);
	   					 }
	   				 }
	   			 }
	   			 else if(cond.get(1).equals("!=")) {
	   				 if(Pattern.matches( "[0-9]*",arr[i][col])) {
	   					 int a=Integer.parseInt(arr[i][col]);
	   					 int b=Integer.parseInt(cond.get(2));
	   					 if(a != b)
	   						 id_1.add(i);
	   				 }
	   				 else {
	   					 String a = arr[i][col].toLowerCase();
	   					 String b = cond.get(2).toLowerCase();
	   					 if( ! a.equals(b)) {
	   						 id_1.add(i);
	   						
	   					 }
	   				 }
	   			 }
	   			 else if(cond.get(1).equals(">")) {
	   				 if(Pattern.matches( "[0-9]*",arr[i][col])) {
	   					 int a=Integer.parseInt(arr[i][col]);
	   					 int b=Integer.parseInt(cond.get(2));
	   					 if(a > b)
	   						 id_1.add(i);
	   				 }
	   				 else {
	   					 if(arr[i][col].compareTo(cond.get(2)) > 0) {
	   						 id_1.add(i);
	   					 }
	   				 }
	   				 
	   			 }
	   			 else if(cond.get(1).equals("<")) {
	   				if(Pattern.matches( "[0-9]*",arr[i][col])) {
	  					 int a=Integer.parseInt(arr[i][col]);
	  					 int b=Integer.parseInt(cond.get(2));
	  					 if(a < b)
	  						 id_1.add(i);
	  				 }
	  				 else {
	  					 if(arr[i][col].compareTo(cond.get(2)) < 0) {
	  						 id_1.add(i);
	  					 }
	  				 }
	   			 }
	   		 }
	   		 id.add(id_1);

	    }
	    	 
	         merged_result = new ArrayList<Integer>(id.get(0));
	    	 int i = 1;
	    	 for(String operator : logicalOperators) {
	    		 if(operator.equals("and")) {
	    			 merged_result.retainAll(id.get(i));
	    		 }
	    		 else if(operator.equals("or")){
	    			 for(Integer temp1 : id.get(i)) {
	    				 int flag=0;
	    				 for(Integer temp2 : merged_result) {
	    					 if(temp1.equals(temp2)) {
	    						 flag=1;
	    						 break;
	    					 }
	    				 }
	    				 if(flag==0)
	    					 merged_result.add(temp1);
	    			 }
	    		 }
	    		 i++;
	    	 }	 
	 	}
	 	
	 	
	 	void orderByExecute() {
	    	 String criteria = order_by;
	    	 int col_no =0;
	    	 for(int j=0;j<key.size();j++) {
	    		 String a= criteria.toLowerCase();
	 			 String b = arr[0][j].toLowerCase();
	 			 if(a.equals(b)) {
	 				 col_no=j;
	 				 break;
	 			 }
	    	 }
		     final int col_noF=col_no;
		     if( ! Pattern.matches("[0-9]*", arr[1][col_no])) {
		    	 Collections.sort(merged_result, (entry1,entry2)->{
		    		 return arr[entry1][col_noF].compareTo(arr[entry2][col_noF]);
		    	 });
		     }
		     else {
		    	 Collections.sort(merged_result, (entry1,entry2)->{
		    		 int a=Integer.parseInt(arr[ entry1][col_noF]);
		    		 int b=Integer.parseInt(arr[ entry2][col_noF]);
		    		 return a-b;
		    	 });
		     }	
	     }
	 	
	    void displaySelectedFields() {
	    	
	    	List<Integer> colToDisplay = new ArrayList<Integer>();
	    	if(selectedFields.get(0).equals("*")) {
	    		for(int temp : merged_result) {
	    			for(int j=0;j<key.size();j++) {
	    				System.out.print(arr[temp][j] +" ");
	    			}
	    			System.out.println();
	    		}
	    	}
	    	
	    	else {
	    		for(String temp : selectedFields) {
		    		for(int j=0;j<key.size();j++) {
		    			String a= temp.toLowerCase();
		    			String b = arr[0][j].toLowerCase();
		    			if(a.equals(b)) {
		    				colToDisplay.add(j);
		    				break;
		    			}				
		    		}
		    	}
	    		for(Integer i : merged_result) {
	    			for(Integer j : colToDisplay) {
	    				System.out.print(arr[i][j] + " ");
	    			}
	    			System.out.println();
	    		}
	    	}
	    }	 	
}
