package com.sapient.csv.csvdatabase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;


public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		String inputQuery = sc.nextLine();
		
		split obj = new split(inputQuery);
		String arr[] = obj.splitWords();
		for(String part:arr) {
			System.out.println(part);
		}
		
		String fileName=obj.splitFileName(arr);	
		StringBuffer basePart = obj.splitBasePart(arr);
		StringBuffer filterPart = obj.splitFilterPart(arr);
		ArrayList<String> logicalOperators=obj.splitLogicalOperators(arr);
		
		ArrayList<String> selectedFields = obj.splitSelectedFields(arr);
		
		/*Iterator<String> iterator = selectedFields.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}*/
		
		String order_by=obj.splitOrderBy(arr);
		String group_by=obj.splitGroupBy(arr);
		obj.splitWhereFields();
		
		read readObj=new read();
		LinkedHashMap<String,ArrayList<Object>> map = readObj.readFile();
        int row=readObj.getHeader();
         
        queryExecute execute = new queryExecute(selectedFields , map,  obj.where_fields, obj.logicalOperators, obj.order_by, obj.containing_functions,obj.group_by, row);
        execute.matrixPopulate();
        
        if(obj.where_fields.size() !=0 && obj.order_by.length() !=0) {
       	 execute.whereExecute();
       	 execute.orderByExecute();
       	 execute.displaySelectedFields();
        }
        else if(obj.where_fields.size() !=0 || obj.order_by.length() !=0) {
       	 if(obj.where_fields.size() !=0) {
       		 execute.whereExecute();
       		 execute.displaySelectedFields();
       	 }
        
       	 else {
       		 execute.orderByExecute();
       		 execute.displaySelectedFields();
       	 }
        }
       
        else
       	 execute.displaySelectedFields();

	}	
		

}
