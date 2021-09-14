package personnage;
import carte.Carte;
import carte.Vide;
//import com.modeliosoft.modelio.javadesigner.annotations.objid;
import objet.*;

public abstract class Personnage extends Objet {
    private int positionXl;
    private int positionYL;
    private String nom;
    
	public Carte carte ;
	
	public Outil saveOjet;
	
	private String directionFace=HAUT;
	public static final String BAS = "b";
	public static final String GAUCHE = "g";
	public static final String HAUT = "h";
	public static final String DROITE = "d";
	
	public Personnage(Carte c) {
		this.carte=c;
	}

    public int getPositionXl() {
        return this.positionXl;
    }

    public void setPositionXl(int xl) {
    	this.positionXl = xl;
    }
    public int getPositionYL() {
        return this.positionYL;
    }

    public void setPositionYL(int yL) {
        this.positionYL = yL;
    }
    public void setPositionYLxl(int yL,int xl) {
        this.positionXl = xl; this.positionYL = yL;
    }

    public String getNom () {
		return nom;
	}

	public void setNom (String nom) {
		this.nom = nom;
	}
	public String getDirectionFace() {
		return directionFace;
	}

	public void setDirectionFace(String directionFace) {
		this.directionFace = directionFace;
	}

	public boolean estOutil(int y,int x) {
		System.out.println("estO y : "+y+", "+"x : "+x);
		return this.carte.getObjet(y, x) instanceof Outil;
	}

	public void setCarte (Carte carte) {
		if(this.carte == null){
			this.carte = carte;
		}
	}
	public void deplacer(String sens) {
        switch (sens) {
        case Personnage.HAUT:	
        	if (this.carte.estVide(this.getPositionYL()-1,this.getPositionXl())) {
            	this.carte.casePlateau[this.getPositionYL()-1][this.getPositionXl()]=this;
            	this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
                this.setPositionYL(this.getPositionYL()-1);
                if(!(this.saveOjet==null)) {
                	this.carte.casePlateau[this.getPositionYL()+1][this.getPositionXl()] = (Objet) this.saveOjet;
                	this.saveOjet=null;
                }
                }else if (this.estOutil(this.getPositionYL()-1,this.getPositionXl())) {
            		this.saveOjet = (Outil)this.carte.getObjet(this.getPositionYL()-1,this.getPositionXl());
            		System.out.println("Objet dessous : "+this.saveOjet);
            		this.carte.casePlateau[this.getPositionYL()-1][this.getPositionXl()]=this;
            		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
                    this.setPositionYL(this.getPositionYL()-1);
            	}
        	this.directionFace=HAUT;
            break;
        case Personnage.BAS :
        	if (this.carte.estVide(this.getPositionYL()+1,this.getPositionXl())) {
        		this.carte.casePlateau[this.getPositionYL()+1][this.getPositionXl()]=this;
        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
                this.setPositionYL(this.getPositionYL()+1);
                if(!(this.saveOjet==null)) {
                	this.carte.casePlateau[this.getPositionYL()-1][this.getPositionXl()] = (Objet) this.saveOjet;
                	this.saveOjet=null;
                }
                }else if (this.estOutil(this.getPositionYL()+1,this.getPositionXl())) {
            		this.saveOjet= (Outil)this.carte.getObjet(this.getPositionYL()+1,this.getPositionXl());
            		System.out.println("Objet dessous : "+this.saveOjet);
            		this.carte.casePlateau[this.getPositionYL()+1][this.getPositionXl()]=this;
            		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
                    this.setPositionYL(this.getPositionYL()+1);
            	}
        	this.directionFace=BAS;
            break;
        case Personnage.GAUCHE :
        	if (this.carte.estVide(this.getPositionYL(),this.getPositionXl()-1)) {
        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()-1]=this;
        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
                this.setPositionXl(this.getPositionXl()-1);
                if(!(this.saveOjet==null)) {
                	this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()+1] = (Objet) this.saveOjet;
                	this.saveOjet=null;
                }
            }else if (this.estOutil(this.getPositionYL(),this.getPositionXl()-1)) {
            		this.saveOjet= (Outil)this.carte.getObjet(this.getPositionYL(),this.getPositionXl()-1);
            		System.out.println("Objet dessous : "+this.saveOjet);
            		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()-1]=this;
            		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
                    this.setPositionXl(this.getPositionXl()-1);
            	}
        	this.directionFace=GAUCHE;
            break;
        case Personnage.DROITE :
        	if (this.carte.estVide(this.getPositionYL(),this.getPositionXl()+1)) {
        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()+1]=this;
        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
                this.setPositionXl(this.getPositionXl()+1);
                if(!(this.saveOjet==null)) {
                	this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()-1] = (Objet) this.saveOjet;
                	this.saveOjet=null;
                }
        	}else if (this.estOutil(this.getPositionYL(),this.getPositionXl()+1)) {
        		this.saveOjet= (Outil)this.carte.getObjet(this.getPositionYL(),this.getPositionXl()+1);
        		System.out.println("Objet dessous : "+this.saveOjet);
        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()+1]=this;
        		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=new Vide();
                this.setPositionXl(this.getPositionXl()+1);
        	}
        	this.directionFace=DROITE;
            break;
        }
       
    }

}
