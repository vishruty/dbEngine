package com.sapient.csv.csvdatabase;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSpinnerUI;



public class split {
	String str;
	String fileName;
	StringBuffer base = new StringBuffer ("");
	StringBuffer filter = new StringBuffer ("");
	ArrayList<String> containing_functions=new ArrayList <>();
	ArrayList<String> logicalOperators =new ArrayList<String>();
	ArrayList<String> selected_fields=new ArrayList<String>();
    ArrayList<ArrayList<String>> where_fields = new ArrayList<>();

	String order_by="";
	String group_by="";
	String arr[];
	
	split(String str){
		String k = str.replaceAll(","," ");
		k = k.replaceAll("^ +| +$|( )+", "$1");
		this.str = k;

	}
	
	public String[] splitWords() {
		 arr=str.split("[\\s,;]+");
		return arr;
	}
	
	public String splitFileName(String arr[]) {
		int i=0;
		for(String part:arr) {
			i++;
			if(part.equals("from") || part.equals("FROM")) {
				break;
			}
		}
		fileName=arr[i];
		return arr[i];
	}
	
	public StringBuffer splitBasePart(String[] arr) {	
		for(String part:arr) {
			if(part.equals("where") || part.equals("WHERE")) {
				break;
			}
			base.append(part +" ");
		}
		return base;
	}
	
	public StringBuffer splitFilterPart(String[] arr) {
		boolean flag=false;
		for(String part:arr) {
			if(flag==true)
				filter.append(part+ " ");
			if(part.equals("where") || part.equals("WHERE")) {
				flag=true;
			}
		}
		return filter;
	}
	
	public ArrayList<String> splitLogicalOperators(String arr[]){
		for(String part:arr) {
			if(part.equalsIgnoreCase("or") || part.equalsIgnoreCase("and") || part.equalsIgnoreCase("not")) {
				logicalOperators.add(part);
			}
		}
		return logicalOperators;
	}
	
	
	public ArrayList<String> splitSelectedFields(String arr[]){
		int i=0;
		while(i<arr.length) {
			i++;
			if(arr[i].equalsIgnoreCase("FROM"))
				break;
			if(arr[i].indexOf("(") == -1)
				selected_fields.add(arr[i]);
			else
				containing_functions.add(arr[i]);

		}
		return selected_fields;
	}
	
	
	int splitWhereFields() {
		int temp=-1;
		String k=filter.toString();
		 arr=k.split("[\\s,;]+");

		if(filter.length()!=0) {
			int prev = temp+1;
			int curr=prev;
			for(temp=prev;;temp++) {
				if( temp == arr.length || arr[temp].equals("order") || arr[temp].equals("group")) {
					ArrayList<String> arr1=new ArrayList<>();
					arr1.add(arr[prev]);
					arr1.add(arr[prev+1]);
					String s = "";
					for(int j = prev+2;j<=curr;j++)
						s=s+arr[j];
					arr1.add(s);
					where_fields.add(arr1);
					break;
				}
				if(arr[temp].equals("and") || arr[temp].equals("or") ) {
					ArrayList<String> arr1=new ArrayList<>();
					arr1.add(arr[prev]);
					arr1.add(arr[prev+1]);
					String s = "";
					for(int j = prev+2;j<=curr;j++)
						s=s+arr[j];
					arr1.add(s);
					where_fields.add(arr1);
					prev=temp+1;
				}
				else {
					curr=temp;
				}	
			}
		}
		//System.out.println(where_fields);
		return 1;
	}
	
	
	public String splitOrderBy(String arr[]) {
		boolean flag=false,flag2=false;
		for(String part:arr) {	
			if(flag2==true) {
				order_by=part;
				return part;
			}
			if(flag==true) {
				if(part.equals("by")) {
					flag2=true;
				}
				else {
					flag=false;
				}
			}
			if(part.equals("order")) {
				flag=true;
			}
		}
		return null;
	}

	
	public String splitGroupBy(String arr[]) {
		boolean flag=false,flag2=false;
		for(String part:arr) {	
			if(flag2==true) {
				group_by=part;
				return part;
			}
			if(flag==true) {
				if(part.equals("by")) {
					flag2=true;
				}
				else {
					flag=false;
				}
			}
			if(part.equals("group")) {
				flag=true;
			}
		}
		return null;
	}
}
