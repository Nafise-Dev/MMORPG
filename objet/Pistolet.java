package objet;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("ebd319ff-f02f-447f-8fb0-64eebc363ea7")
public class Pistolet extends Arme {
    @objid ("241b5710-bd48-4d72-9801-57522419e1a4")
    private int capaciteBalle;

    @objid ("24f0232c-47aa-493f-b921-567051140282")
    public void tirer() {
    }

    @objid ("7116dc17-de72-4c78-b20b-f202a357780d")
    int getCapaciteBalle() {
        return this.capaciteBalle;
    }

    @objid ("2edf45a9-1ad3-40cb-afc0-a1a854def64c")
    void setCapaciteBalle(int value) {
        this.capaciteBalle = value;
    }

    @objid ("902d656f-1491-4d8f-a0a2-c4ee360fea60")
    public Pistolet(String nom, int dgt, int cap) {
        super(nom,dgt);
        this.capaciteBalle=cap;
    }

    @objid ("548e82b6-3338-460e-a209-7c153ede343c")
    public Pistolet(int dgt, int cap) {
        this("Pistolet",dgt,cap);
    }

}
