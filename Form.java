package form;

import java.util.LinkedList;

public class Form {
	public int index;
	public String date;
	public LinkedList<String> tickers;
	
	public Form(String[] tokens){
		index = Integer.valueOf(tokens[0]);
		tickers = new LinkedList<String>();
		date = tokens[1].replace(", ", "-");
		date = date.replace("[", "");
		date = date.replace("]", "");
		if(tokens[2].length()>2)
			tickers.add(tokens[2].replaceAll("\\W",""));
		if(tokens[3].length()>2){
			String[] temps = tokens[3].split(",");
			for(String temp: temps)
				tickers.add(temp.replaceAll("\\W", ""));
		}
	}
}
