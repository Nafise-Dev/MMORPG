package objet;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("2ef539f9-6b64-4105-9ed8-75e6b6dfdb1b")
public class Arme extends Outil {
    public int positionX;
    public int positionY;
    
    private String nomArme;

    private int degat;

    public Arme(String n, int d) {
        this.nomArme=n;
        this.degat=d;
    }

    public String getNomArme() {
        return nomArme;
    }

    public void setNomArme(String nomArme) {
        this.nomArme = nomArme;
    }

    public int getDegat() {
        return degat;
    }

    public void setDegat(int degat) {
        this.degat = degat;
    }
    
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int x) {
        this.positionX = x;
    }
    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int y) {
        this.positionY = y;
    }

}
