package com.sapient.csv.csvdatabase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class read {
	static String []header=null;
    int row=0;
    LinkedHashMap<String,ArrayList<Object>> readFile()throws IOException {
	
	LinkedHashMap<String,ArrayList<Object>> map = new LinkedHashMap<String, ArrayList<Object>>();
	File f=new File("/home/sapient/Desktop/vishruty/STS-WORKSPACE/Database-Engine/ipl.csv");
	BufferedReader reader = new BufferedReader(new FileReader(f));
    String head=reader.readLine();
    
    header=head.split(",");

    //populate header 
    for(int i=0;i<header.length;i++) {
    	ArrayList<Object> list = new ArrayList<Object>();
    	map.put(header[i], list);
    }
    //populate with file data
    String entry=reader.readLine();
    while(entry != null) {
    	String temp[]=entry.split(",");
    	for(int i=0;i<header.length;i++) {
    		ArrayList<Object> list= map.get(header[i]);
    	    list.add(temp[i]);
    	    map.put(header[i], list);
    	}
    	entry=reader.readLine();
    }
   
    	
    reader.close();
    row = (map.get(header[0])).size();
    return map;
}
    int getHeader() {
    	return row;
    }

}
