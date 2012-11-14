package sa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

import form.Form;

public class SAReader {
	LinkedList<Form> TheForm;
	
	SAReader(String dir){
		try {
			FileReader fr = new FileReader(dir);
			Scanner sc = new Scanner(fr);
			String line;
			String[] tokens;
			TheForm = new LinkedList<Form>();
			while (sc.hasNext()){
				line = sc.nextLine();
				tokens = line.split("(,\"\\[)|(\\]\",\\[)|(\\],\"\\[)|(\\]\",\"\\[)|(\\],\\[)");
				TheForm.add(new Form(tokens));
			}
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void extract(String WriteHere, String ReadHere, String ticker){
		int count = 1;
		TokenizerFactory tf = IndoEuropeanTokenizerFactory.INSTANCE;
		for(Form now: TheForm){
			if(now.tickers.contains(ticker)){
				try{
					FileReader fr = new FileReader(ReadHere+"\\article"+String.valueOf(now.index)+".txt");
					Scanner sc = new Scanner(fr);
					new File(WriteHere+"\\"+ticker).mkdirs();
					FileWriter fw = new FileWriter(WriteHere+"\\"+ticker+"\\"+String.valueOf(count)+".txt");
					BufferedWriter bw = new BufferedWriter(fw);
					while(sc.hasNext()){
						String line = sc.next();
						Tokenizer tk = tf.tokenizer(line.toCharArray(), 0, line.length());
						for(String token: tk)
							if (!token.matches("^\\W+$"))
								fw.write(token+"\n");
					}
					sc.close();
					bw.close();
					count++;
				} catch (FileNotFoundException e){
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] Arg0) throws FileNotFoundException{
		SAReader sar = new SAReader("data\\SA.csv");
		FileReader fr = new FileReader("list.txt");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(fr);
		while(sc.hasNext()){
			sar.extract("output", "data", sc.next());
		}
	}
}
