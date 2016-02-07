
public class Entre {
	Object b;
	String name;
	String type;
	
	public Entre(String name, Object nb){
		b = nb;
		this.name = name;
		this.type = nb.getClass().getSimpleName();
	}
	
	public Entre(String name, int nb){
		b = nb;
		this.name = name;
		this.type = "int";
	}
	
	public Entre(String name, double nb){
		b = nb;
		this.name = name;
		this.type = "double";
	}
	
	public Entre(String name, byte nb){
		b = nb;
		this.name = name;
		this.type = "byte";
	}
	
	public Entre(String name, boolean nb){
		b = nb;
		this.name = name;
		this.type = "boolean";
	}
	
	public Entre(String name, String s, String type){
		this.name = name;
		this.type = type;
		if(type.equals("boolean")){
			b = Boolean.parseBoolean(s);
		}
		if(type.equals("int")){
			b = Integer.parseInt(s);
		}
		
	}
	
	public void changeValue(Object o){
		this.b = o;
	}
	
	public Object getObject(){
		return b;
	}
	
	public boolean isThisEntre(String s){
		if(s.equals(name)){
			return true;
		}
		else
			return false;
	}
}
