package carte;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//import java.io.IOException;
public class FichierCarte {
    public static String CHEMIN = "D:\\informatique\\MonProjetTuto\\ProjetTutoUMLJava\\src\\carte\\carte.txt";
   
    public FichierCarte() {}

    public static String chargerListeObjet() {
        String tab = "";
        try {
            BufferedReader buffR = new BufferedReader(new FileReader(FichierCarte.CHEMIN));
            String ligne;
            while ((ligne = buffR.readLine()) != null) {
            	tab+=ligne;
            }
            buffR.close();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        return tab;
    }

}
