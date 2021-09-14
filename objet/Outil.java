package objet;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("fe1e44f4-29f6-4676-b5fc-78009fce7047")
public class Outil extends Objet {
    @objid ("ff689eb1-40e7-40de-9ecb-9277b94f2c9c")
    private String nom;

    @objid ("96d92c2a-9224-4d29-94d7-62473acce0c0")
    String getNom() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.nom;
    }

    @objid ("be229993-0014-4909-ba2e-66851e054ddc")
    void setNom(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.nom = value;
    }

    @objid ("5f1773f1-c80c-408c-9d1a-106ddf1d5f51")
    public Outil() {
    }

}
