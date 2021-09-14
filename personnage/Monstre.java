package personnage;


import java.util.Random;

import carte.Carte;

public class Monstre extends Personnage {
	private int precedentXl;
	private int precedentYL;
	private Random rdm = new Random(); 
    public Monstre(Carte c) {
		super(c);
	}

    public int getPrecedentXl() {
		return precedentXl;
	}

	public void setPrecedentXl(int precedentXl) {
		this.precedentXl = precedentXl;
	}

	public int getPrecedentYL() {
		return precedentYL;
	}

	public void setPrecedentYL(int precedentYL) {
		this.precedentYL = precedentYL;
	}

	public void attaquer() {
    }

    public void deplacer() {
    	int n =1+rdm.nextInt(4);
    	String sens = null;
    	switch (n) {
    	case 1:
    		sens="h";
    		break;
    	case 2:
    		sens="b";
    		break;
    	case 3:
    		sens="d";
    		break;
    	case 4:
    		sens="g";
    		break;
    	}
    	
    	super.deplacer(sens);
    	
    }
    
    public String toString() {
    	return "♞";
    }

}
