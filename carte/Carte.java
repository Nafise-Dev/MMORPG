package carte;

import objet.*;
import personnage.*;

public class Carte {
// Attributs
    public static int LONGUEUR = 22;
    public static int LARGEUR = 20;

    public Objet[][] casePlateau;

    public PersonnageJoueur joueur1=new PersonnageJoueur(this);
    public PersonnageJoueur joueur2=new PersonnageJoueur(this);
    public Monstre monstre = new Monstre(this);
    public PersonnageNonJoueur pnj=new PersonnageNonJoueur(this);
    
    public static final String JOUEUR_1 = "J";
    public static final String JOUEUR_2 = "P";
	public static final String AUTRE_JOUEUR = "p";
	public static final String VIDE = "_";
	public static final String MUR = "#";
	public static final String MONSTRE = "m";
	public static final String OBJET = "o";

// Constructeurs
    public Carte() {
    	this.casePlateau= new Objet[LONGUEUR][LARGEUR];
        String chaineOjbet =FichierCarte.chargerListeObjet();
        
        for(int i=0; i <LONGUEUR;i++) {
        	for(int j=0;j<LARGEUR;j++) {
        		switch ((String.valueOf(chaineOjbet.charAt(i*LARGEUR+j)))) {
	        		case MUR:
	        			this.casePlateau[i][j] = new Mur();
	        			break;
	        		case VIDE:
	        			this.casePlateau[i][j] = new Vide();
	        			break;
	        		case JOUEUR_1:
	        			this.casePlateau[i][j] = joueur1;
	        			joueur1.setPositionYLxl(i, j);
	        			break;
	        		case JOUEUR_2:
	        			this.casePlateau[i][j] = joueur2;
	        			joueur2.setPositionYLxl(i, j);
	        			break;
	        		case AUTRE_JOUEUR:
	        			this.casePlateau[i][j] = new PersonnageNonJoueur(this);
	        			break;
	        		case OBJET:
	        			this.casePlateau[i][j] = new Outil();
	        			break;
	        		case MONSTRE:
	        			this.casePlateau[i][j] = monstre;
	        			monstre.setPositionYLxl(i, j);
	        			monstre.setPrecedentXl(monstre.getPositionXl());
	        			monstre.setPrecedentYL(monstre.getPositionYL());
	        			break;
        		}
        	}
        }
        casePlateau[12][15]=new Fusil("MiniGun",5,20);
    }

    public void afficher() {
    	for(int i=0; i <LONGUEUR;i++) {
        	for(int j=0;j<LARGEUR;j++) {
        		System.out.print(this.casePlateau[i][j]);
        	}
        	System.out.println();
        }
    }
    
    public String afficherFen() {
    	String c="";
    	for(int i=0; i <LONGUEUR;i++) {
        	for(int j=0;j<LARGEUR;j++) {
        		c+=this.casePlateau[i][j];
        	}
        	c+="\n";
        }
    	
    	return c;
    }

// Methodes
    public int getLongueur() {
        return LONGUEUR;
    }

    void setLongueur(int value) {
        LONGUEUR = value;
    }

    public int getLargeur() {
        return LARGEUR;
    }

    void setLargeur(int value) {
        LARGEUR = value;
    }

    public boolean estVide(int yL,int xl) {
        return this.casePlateau[yL][xl].toString()==VIDE;
    }

    public boolean estPasBarriere(int yL,int xl) {
        return this.casePlateau[yL][xl].toString()==MUR;
    }

   

    public void charger() {
    	
    }

    public void sauver() {
    }

    public void setObjet(int x,int y, Objet o) {
        if (this.estVide( y, x) && ! this.estPasBarriere(y,x)) this.casePlateau[y][x]=o;
    }

    public Objet getObjet(int y,int x) {
        return this.casePlateau[y][x];
    }

}
