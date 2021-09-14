package objet;


public class Fusil extends Arme {
    private int capaciteBalle;
    
    public Fusil(int dgt, int cap) {
        this("Pistolet",dgt,cap);
    }
   
    public void tirer() {
    }

    int getCapaciteBalle() {
        return this.capaciteBalle;
    }

    void setCapaciteBalle(int value) {
        this.capaciteBalle = value;
    }

    public Fusil(String nom, int dgt, int cap) {
        super(nom,dgt);
        this.capaciteBalle=cap;
    }
    public String toString() {
    	return "'f'";
    }
    

}
