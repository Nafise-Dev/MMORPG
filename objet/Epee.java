package objet;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("c1e605cc-95db-4d75-a75c-2bbafb002a7c")
public class Epee extends Arme {
    @objid ("5dba46d3-a73f-4328-bffa-1f68c23ffb0a")
    private int longueur;

    @objid ("808e6993-ecc1-4e70-abd5-42aa0eb7f228")
    public Epee(String n, int d, int l) {
        super(n,d);
        this.setLongueur(l);
    }

    @objid ("6301584e-bebb-4117-86a3-d657a14c08c5")
    public Epee(int d, int l) {
        this("Ep�e",d,l);
    }

    @objid ("c4a13bc9-b756-49f0-a009-354da4c3ada4")
    public String toString() {
        return "e";
    }

    @objid ("474fa800-b09a-4265-a35d-6a66e632f20f")
    public void frapper() {
    }

    @objid ("e1290ff6-de93-40d8-afb9-48fc739b9813")
    public int getLongueur() {
        return longueur;
    }

    @objid ("50a4eb93-c79b-48f1-bf65-3795426bddba")
    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

}
