package org.iaws.classes;

public class Poteau{
	private String name;
	private String id;
	private Ligne maLigne;
	
	public Poteau(String nom, String id){
		this.name = nom.replaceAll("\"", "");
		this.id = id.replaceAll("\"", "");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		String str = this.maLigne.getName() + " : déstination : " + this.maLigne.getDestination().getName();
		return str;
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Poteau) {

			Poteau other = (Poteau) obj;

			if (other.id.equals(this.id)) {
				return true;
			}
		}

		return false;
	}
	
}
