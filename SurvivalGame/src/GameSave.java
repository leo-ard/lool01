import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameSave {
	private ArrayList<Entre> entres;
	
	public GameSave(){
		entres = new ArrayList<Entre>();
	}
	
	public Object get(String s){
		for(int i = 0; i < entres.size(); i++){
			if(entres.get(i).isThisEntre(s)){
				System.out.println(entres.get(i).getObject());
				return entres.get(i).getObject();
			}
		}
		
		System.out.println("ERROR: L'entré : "+ s +" n:existe pas.");
		return null;
	}
	
	public void save(){
		BufferedWriter out;
		
		try {
			out = new BufferedWriter(new FileWriter("reglages.txt"));
			
			for(int i = 0; i < entres.size(); i++){
				out.write(entres.get(i).name+":"+entres.get(i).b+":"+entres.get(i).type);
				System.out.println(entres.get(i).name+":"+entres.get(i).b+":"+entres.get(i).type);
				out.newLine();
			}
			
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Le fichier reglages.txt n'existe pas.");
			return;
		}
		
	}
	
	public void load(){
		BufferedReader in;
		
		//load du fichier .txt
		try {
			in = new BufferedReader(new FileReader("reglages.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Le fichier reglages.txt n'existe pas.");
			return;
		}
		
		//load de l'image
		String line;
		
		try {
			while ((line = in.readLine()) != null) {
				if(!line.startsWith("//")&&!line.equals("")){
					entres.add(new Entre(line.substring(0,line.indexOf(':')), line.substring(line.indexOf(':')+1, line.lastIndexOf(':')), line.substring(line.lastIndexOf(':')+1)));
				}
			}
			in.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void add(Entre e){
		entres.add(e);
	}
	
	public void changeEntree(String entree, Object newValue){
		for(int i = 0; i < entres.size(); i++){
			if(entres.get(i).isThisEntre(entree)){
				entres.get(i).changeValue(newValue);
			}
		}
		
		System.out.println("ERROR: L'entré 1 : "+ entree +" n'existe pas.");
	}
}
