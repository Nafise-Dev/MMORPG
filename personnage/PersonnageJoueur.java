package personnage;

import java.util.Scanner;

import java.util.TimerTask;
import java.util.Timer;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

import carte.*;
import objet.*;

@objid ("4e3a145c-a940-4c3f-9a4e-d02834ecf19b")
public class PersonnageJoueur extends Personnage {
// Attributs
    private int force=6;
    private int adresse=8;
    private int resistance=5;
    
    private int initiative=6;
    private int attaque=8;
    private int defense=5;
    private int degat=6;

    private int pointAction=16;
    
    private Timer tempsPA;
    private Outil mainGauche;

    private Outil mainDroite;
    
    public Sac sac=null;
    

// Constructeurs
    public PersonnageJoueur(Carte carte) {
    	
        super(carte);
        this.setNom("Joueur 1");
        tempsPA = new Timer();
		tempsPA.schedule(new TimerTask() {
						public void run() {
							pointAction+=5;
							System.out.println("Point d'action du :"+pointAction);
						}
		},1, 5000);
        
        //this.setPAdate();
    }

// Getters et Setters
    int getAdresse() {
        return this.adresse;
    }

    void setAdresse(int value) {
        this.adresse = value;
    }

    int getResistance() {
        return this.resistance;
    }

    void setResistance(int value) {
        this.resistance = value;
    }

    int getForce() {
        return this.force;
    }

    void setForce(int value) {
        this.force = value;
    }
    
    public int getPointAction() {
		return pointAction;
	}

	public void setPointAction(int pointAction) {
		this.pointAction = pointAction;
	}
	
	public Objet getMainGauche() {
		return mainGauche;
	}

	public void setManGauche(Outil manGauche) {
		this.mainGauche = manGauche;
	}

	public Objet getMainDroite() {
		return mainDroite;
	}

	public void setMainDroite(Outil mainDroite) {
		this.mainDroite = mainDroite;
	}

public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public int getAttaque() {
		return attaque;
	}

	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getDegat() {
		return degat;
	}

	public void setDegat(int degat) {
		this.degat = degat;
	}

	// Methodes des personnages
    public void deplacer() {
    	if (this.pointAction>=3) {
    	@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
    	String direction = "";
    	
        do {
	        System.out.println("H: vers le haut | B : vers le bas | G vers la guache | D : vers la droite");
	        System.out.print("Votre choix: _");direction = input.next().toLowerCase();
	        
        }while (!direction.equals("h") && !direction.equals("b") && !direction.equals("g") && !direction.equals("d") );
        
        super.deplacer(direction);
        this.pointAction-=2;}
    }
    
    public void deplacerFEN(int s) {
    	if (this.pointAction>=2) {
    		String sens = null;
        	switch (s) {
        	case 1:
        		sens="h";
        		this.pointAction-=2;
        		break;
        	case 2:
        		sens="b";
        		this.pointAction-=2;
        		break;
        	case 3:
        		sens="d";
        		this.pointAction-=2;
        		break;
        	case 4:
        		sens="g";
        		this.pointAction-=2;
        		break;
        	}
        	super.deplacer(sens);
        
        }
    }

    public void attaquer() {
    	if (this.pointAction>=3) {
    		int j=this.getPositionYL();
    		int i=this.getPositionXl();
    		System.out.println("Position : y : "+j+", x : "+i+", face "+this.getDirectionFace());
    		do {
				switch (this.getDirectionFace()) {
	    	    	case HAUT:
	    	    		j--;
	    	    		break;
	    	    	case BAS:
	    	    		j++;
	    	    		break;
	    	    	case DROITE:
	    	    		i++;
	    	    		break;
	    	    	case GAUCHE:
	    	    		i--;
	    	    		break;
	    	    	default:
	    	    		break;
    	    		}
    			}while(this.carte.estVide(j, i));
    		
    		System.out.println("Position impacte y : "+j+", x : "+i);
    		Objet o=carte.getObjet(j, i);
    		if(o instanceof PersonnageNonJoueur) {
    			System.out.println("Focer PNJ : "+((PersonnageNonJoueur) o).getForce());
    			System.out.println("Degat Jou : "+this.degat);
    			System.out.println("Focer PNJ : "+(((PersonnageNonJoueur) o).getForce()-this.degat));
    			((PersonnageNonJoueur) o).setForce(((PersonnageNonJoueur) o).getForce()-this.degat);
    			this.carte.pnj=(PersonnageNonJoueur) o;
    		}
    		
    		this.pointAction-=3;
	    	}
    }

    public void utiliser(Objet o) {
    }
    
    public void deposer() {
    	if((this.saveOjet==null) && !(this.mainDroite==null)) {
    		this.saveOjet=this.mainDroite;
    		this.mainDroite=null;
    	}
    }
    public void ramasser() {
    	if(!(this.saveOjet==null)) {
    		this.mainDroite=this.saveOjet;
    		this.saveOjet=null;
    	}
    }
    

    public void finir() {
    }

    public String toString() {
    	//▲ ► ▼ ◄
//    	switch (this.getDirectionFace()) {
//    	case HAUT:
//    		return "▲";
//    	case BAS:
//    		return "▼";
//    	case DROITE:
//    		return "►";
//    	case GAUCHE:
//    		return "◄";
//    	default:
//    		return "J";
//    	}
    	return "1";
    }

    public String infoJoueur() {
   
        return "_______________________________________________________________________\n"
            +"Vos carateristiques "+this.getNom()+"| Adresse : "+this.infoADR()+" | Force :"+this.infoFRC()+" | Resistance :"+this.infoRST()
            +"\n_______________________________________________________________________\n"
            + "Main Droite : "+this.mainDroite + " | Main Gauche : "+this.mainGauche
            +"\n_______________________________________________________________________\n"
            		+ "Vos points d'action : "+this.pointAction
        	+"\n_______________________________________________________________________\n";
    }
    public String infoADR() {
    	String a="Adresse : ";
    	a+=String.valueOf(this.adresse/3)+"D";
    	a+= this.adresse%3==0 ? "" : "+"+String.valueOf(this.adresse%3);
    	return a;
    }
    public String infoFRC() {
    	String f="Fore : ";
    	f+=String.valueOf(this.force/3)+"D";
    	f+= this.force%3==0 ? "" : "+"+String.valueOf(this.force%3);
    	return f;
    }
    public String infoRST() {
    	String r="Resistance : ";
    	r+=String.valueOf(this.resistance/3)+"D";
    	r+= this.resistance%3==0 ? "" : "+"+String.valueOf(this.resistance%3);
    	return r;
    }
    
    

}
