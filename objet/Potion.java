package objet;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("46d5c1ff-6e0f-4807-a21c-41a5a236172b")
public class Potion extends Outil {
    @objid ("70097d36-20fc-406e-96b6-1d4eb9345a24")
    private int dose;

    @objid ("eb617e14-8c0f-40d1-83b0-a2649fe8c0c1")
    private String nomPotion;

    @objid ("378a3441-faf7-4824-84e4-9f40c11ea70a")
    public String getNomPortion() {
        return this.nomPotion;
    }

    @objid ("528b7104-5c28-4980-afc0-f1f85168aa34")
    public void setNomPortion(String value) {
        this.nomPotion = value;
    }

    @objid ("9d756ef6-6c88-4b83-82c5-5d0d6ff08e40")
    public void utiliser() {
    }

    @objid ("7d9c3053-9efe-4056-9c97-eb8c4777e60c")
    public int getDose() {
        return this.dose;
    }

    @objid ("b1ff55ac-2f0a-401a-90ea-173b96f8477a")
    public void setDose(int value) {
        this.dose = value;
    }

    @objid ("26390151-7a57-48c7-9b89-8f7225e327fc")
    public Potion(int dose) {
        this.nomPotion="Potion";
        this.dose=dose;
    }

}
