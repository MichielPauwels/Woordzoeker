import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Woordzoeker {
	
	public boolean checkWord(String word){
		word = word.trim();
		if(word.contains(" ")){return false;}
		else if(word.length()<10 || word.length()>16){return false;}
		else if(!Pattern.matches("[a-z]+", word)){return false;}
		else {return true;}
	}
	
	private boolean checkValidity(String woord, ArrayList<String> straten) {
		
		//Test 1: Alle letters van woord moeten vervatten zitten in straatnamen
		boolean checked=false;
		for(int i=0; i<woord.length(); i++){
			checked = false;
			for(int u=0; u<straten.size(); u++){
				if(straten.get(u).contains(woord.substring(i, i+1))){checked=true; break;}
			}
			if(!checked){return false;}
		}
		
		//Test 2: Elke straatnaam bevat minstens 1 letter van woord
		for(int i=0; i<straten.size(); i++){
			checked=false;
			for(int u=0; u<straten.get(i).length(); u++){
				if(woord.contains(straten.get(i).substring(u,u+1))){checked=true;}
			}
			if(!checked){return false;}
		}
		
		//Test 3: Het aantal keer dat een letter in het woord voorkomt mag niet groter zijn dan 
		//		  het aantal keer dat de letter voorkomt in de straatnamen
		int count;
		for(int i=0; i<woord.length(); i++){
			String curChar = woord.substring(i,i+1);
			count = woord.split(curChar).length-1;
			int listcount = 0;
			for(int u=0; u<straten.size();u++){
				listcount += (straten.get(u).split(curChar).length-1);
			}
			if(listcount < count){return false;}
		}
		
		//Test 4: test de repetitiviteit van het woord
		int counter = 0;
		while(woord.length()>0){
			woord = woord.replace(woord.substring(0, 1), "");
			counter++;
		}
		if(counter<13){return false;}
		
		return true;
	}

	public static void main(String[] args) throws IOException {
		Woordzoeker w = new Woordzoeker();
		int counter = 0;
		ArrayList<String> straten = new ArrayList<String>(Arrays.asList("Kattestraat", "Paardenstraat", "Mr Van der Borghtstraat", "Haachtsebaan", "Hollandstraat","Pelgrimhofstraat","Kastanjedreef","Biekorfstraat","Heestenweg","Heidestraat","Kattegracht"));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Dutch_dictionairy.dic")));
		try {
		    String woord;
		    while ((woord = br.readLine()) != null) {
		        if(w.checkWord(woord)){
		        	if(w.checkValidity(woord,straten)){System.out.println(woord); counter++;}
		        }
		    }
		} finally {
		    br.close();
		}
		System.out.println(counter+" woorden gevonden");
	}

}
