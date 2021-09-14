package personnage;


import carte.Carte;

public class PersonnageNonJoueur extends Personnage {


// Attributs
    private int force;

    private int adresse;

    private int resistance;

// consstructeur
    public PersonnageNonJoueur(Carte c) {
		super(c);
        this.adresse=22;
        this.force=17;
        this.resistance=13;
	}

// M�thodes
    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getAdresse() {
        return adresse;
    }

    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void ramasser() {
    }

    public void utiliser() {
    }

    public String toString() {
        return "p";
    }
    public String infoADR() {
    	String a="Adresse : ";
    	a+=String.valueOf(this.adresse/3)+"D";
    	a+= this.adresse%3==0 ? "" : "+"+String.valueOf(this.adresse%3);
    	return a;
    }
    public String infoFRC() {
    	String f="Fore : ";
    	f+=String.valueOf(this.getForce()/3)+"D";
    	f+= this.getForce()%3==0 ? "" : "+"+String.valueOf(this.getForce()%3);
    	return f;
    }
    public String infoRST() {
    	String r="Resistance : ";
    	r+=String.valueOf(this.resistance/3)+"D";
    	r+= this.resistance%3==0 ? "" : "+"+String.valueOf(this.resistance%3);
    	return r;
    }

}
