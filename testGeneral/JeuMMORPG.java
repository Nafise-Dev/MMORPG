package testGeneral;

import java.util.Scanner;

import carte.*;
import objet.Fusil;

public class JeuMMORPG {
    public static void main(String[] args) {
    	
        Carte plt = new Carte();
        plt.casePlateau[12][15]=new Fusil("MiniGun",5,20);
        
       int quitter=0;
       do {
        	
        	int choix=-1;
        	@SuppressWarnings("resource")
        	Scanner input = new Scanner(System.in);
            
            do {plt.afficher();
	        	System.out.println("Ta position Y : "+plt.joueur1.getPositionYL()+", X :"+plt.joueur1.getPositionXl());
	        	System.out.println(plt.joueur1.infoJoueur());
    	        System.out.println("Vous pouvez :\r\n" + 
    	        		"1 - vous déplacer (2PA)\r\n" + 
    	        		"2 - attaquer (3PA)\r\n" + 
    	        		"3 - utiliser un objet (Variable)\r\n" + 
    	        		"4 - ramasser/déposer un objet (2PA)\r\n" + 
    	        		"5 - finir et garder les PA restants");
    	        System.out.print("Votre choix : _");
    	       choix = input.nextInt();
            }while (choix!=1 && choix!=2 && choix!=3 && choix!=4 && choix!=5);
            switch (choix) {
        	case 1:
        		plt.monstre.deplacer();
        		plt.joueur1.deplacer();
        		break;
        	case 2:
        		plt.joueur1.attaquer();
        		break;
        	case 3:
        		plt.joueur1.utiliser(null);
        		break;
        	case 4:
        		plt.joueur1.ramasser();
        		break;
        	case 5:
        		quitter=5;
        		break;
        	}        	
        }while(quitter!=5);
        System.out.println("Fin .........................");
    }

}
