package objet;

import java.util.ArrayList;


public class Sac {
	private int capacite;
    public ArrayList<Outil> outil;
    
    public Sac() {
    	this.setCapacite(10);
    }

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	
	public boolean contient(Outil o) {
		return true;
	}
   
}
