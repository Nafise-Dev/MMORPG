package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import carte.Carte;

public class Serveur {

   //On initialise des valeurs par défaut
   private int port = 63333;
   private String host = "127.0.0.1";
   private ServerSocket server = null;
   private boolean isRunning = true;
   private int nombreDeClient=0;
   private Carte carte;
   //private Timer timerPA;
   private Socket[] listeClient=new Socket[2];
   
   public Serveur(String pHost, int pPort){
      host = pHost;
      port = pPort;
      try {
     	 System.out.println("############  Démaarage du SEVEUR ##############");
         server = new ServerSocket(port, 100, InetAddress.getByName(host));
         System.out.println("Serveur initialisé : "+server);
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      this.carte=new Carte();
//      timerPA = new Timer();
//      timerPA.schedule(new TimerTask() {
//    	  public void run() {
//    		  //labelPA.setText("Point d'action : "+String.valueOf(carte.joueur1.getPointAction()));
//    	  }
//      },1, 5000);
      
   }
   
   
   //On lance notre serveur
   public void ouvrir(){
      //Toujours dans un thread à part vu qu'il est dans une boucle infinie
      Thread t = new Thread(new Runnable(){
         public void run(){
        	System.out.println("SERVEUR OUVERT : "+server);
        	System.out.println("En attentte d'un client .....");
            while(isRunning == true){
	       try {
				//On attend une connexion d'un client
				Socket client = server.accept();
				listeClient[nombreDeClient]=client;
				nombreDeClient++;
			  
				ClientProcessor cp=new ClientProcessor(client,nombreDeClient);
			  
				PrintWriter envoieVersClient = new PrintWriter(client.getOutputStream(), true);
				envoieVersClient.println("####################################");
				envoieVersClient.println("####        Bievenu au serveur du JEU        ####");
				envoieVersClient.println("####   Vous etes le joueur "+nombreDeClient+" du serveur   ####");
				envoieVersClient.println("####################################");
				envoieVersClient.println(carte.afficherFen());
				envoieVersClient.flush();
				
				//Une fois reçue, on la traite dans un thread séparé
				System.out.println("Client n° "+nombreDeClient+" est connecté au SERVEUR");
				
				new Thread(cp).start();
				if (nombreDeClient==2){
					isRunning=false;
				};  
				
	       } catch (IOException e) {
	          e.printStackTrace();
	       }
            }
            
            try {
            	System.out.println(" ###### FERMETURE DU SERVEUR ###########");
            	server.close();
            } catch (IOException e) {
               e.printStackTrace();
               server = null;
            }
         }
      });
      
      t.start();
   }
   
   public void close(){
      isRunning = false;
   }
   

	public Socket[] getListeClient() {
		return listeClient;
	}
	
	
	public void setListeClient(Socket[] listeClient) {
		this.listeClient = listeClient;
	}
	
	public static void main(String[] args) {
	      new Serveur("localhost", 63334).ouvrir();;
	}
	
	public class ClientProcessor implements Runnable{

		private Socket clientConnecte;
		private PrintWriter envoyeurDonneeVersClient = null;
		private BufferedReader lecteurDonneeClient = null;
		private int numero;
		
		private static final String ATTAQUER = "attaquer";
		private static final String RAMASSER = "ramasser";
		private static final String DEPOSER = "deposer";
		private static final String HAUT = "haut";
		private static final String BAS = "bas";
		private static final String DROITE = "droite";
		private static final String GAUCHE = "gauche";
		private static final String MESSAGE_CLOSE = "close";
		private static final String MESSAGE_CONVERSATION = "message";
		private static final String MESSAGE_CARTE = "catre";
		//private static final String MESSAGE_PA = "pa";
		private static final String MESSAGE_DEPLACEMENT = "deplacement";
		private static final String MESSAGE_ACTION = "action";
		
		   
		public ClientProcessor(Socket pSock,int num){
			clientConnecte = pSock;
			setNumero(num);
			Timer tm = new Timer();
		      tm.schedule(new TimerTask() {
		    	  public void run() {
		    		  carte.monstre.deplacer();
		    		  for(Socket c : listeClient) {
		    			  if(c!=null) {
							try {
								envoyeurDonneeVersClient = new PrintWriter(c.getOutputStream());
								//System.out.println(MESSAGE_CARTE+"/"+carte.afficherFen());
								envoyeurDonneeVersClient.println(MESSAGE_CARTE+"/"+carte.afficherFen());
								envoyeurDonneeVersClient.flush();
								System.err.println("Envoyé au client n°"+(numero)+" actualisation carte");
							
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		    			  }
						}
		    		  //caseCarte.setText(carte.afficherFen());
		    	  }
		      },1, 1000);
		}
		   
		//Le traitement lancé dans un thread séparé
		public void run(){
			System.out.println("Lancement du traitement de la connexion cliente");
			
			//Ici, nous n'utilisons pas les mêmes objets que précédemment
			//Je vous expliquerai pourquoi ensuite
			try {
				envoyeurDonneeVersClient = new PrintWriter(clientConnecte.getOutputStream());
				lecteurDonneeClient = new BufferedReader(new InputStreamReader(clientConnecte.getInputStream()));
//				sender= new DataOutputStream(clientConnecte.getOutputStream());
//				receveur= new DataInputStream(clientConnecte.getInputStream());
				boolean closeConnexion = false;
				//tant que la connexion est active, on traite les demandes
				
				while(!clientConnecte.isClosed()){
			
					String messageClient = lecteurDonneeClient.readLine();
					String reponse="";
					
					String debug = "--> Commande reçue : " + messageClient + "\n";
					System.out.println(debug);
					
					@SuppressWarnings("resource")
					Scanner sc = new Scanner(messageClient);
					sc.useDelimiter("/");
					String m= sc.next();
					switch(m) {
						case MESSAGE_DEPLACEMENT: {
							String d = sc.next();
							switch (d) {
								case HAUT: {
									//
									if (this.numero==1)
										carte.joueur1.deplacerFEN(1);
									else
										carte.joueur2.deplacerFEN(1);
									break;
								}
								case BAS: {
									//
									if (this.numero==1)
										carte.joueur1.deplacerFEN(2);
									else
										carte.joueur2.deplacerFEN(2);
									break;
								}
								case DROITE: {
									//
									if (this.numero==1)
										carte.joueur1.deplacerFEN(3);
									else
										carte.joueur2.deplacerFEN(3);
									break;
								}
								case GAUCHE: {
									//
									if (this.numero==1)
										carte.joueur1.deplacerFEN(4);
									else
										carte.joueur2.deplacerFEN(4);
									break;
								}
							}
							// reponse
							break;
						}
						case MESSAGE_CONVERSATION: {
							reponse=sc.next();
							System.out.println(reponse);
							if(listeClient[(this.numero)%2]!=null && messageClient!=null && messageClient.length()>0) {
								envoyeurDonneeVersClient = new PrintWriter(listeClient[(this.numero)%2].getOutputStream());
								System.out.println(MESSAGE_CONVERSATION+"/"+reponse);
								envoyeurDonneeVersClient.println(MESSAGE_CONVERSATION+"/"+reponse);
								envoyeurDonneeVersClient.flush();
								System.err.println("Envoyé au client n°"+(this.numero%2+1)+" message de l'autre");
							}
							break;
						}
						case MESSAGE_ACTION: {
							String a = sc.next();
							switch (a) {
								case ATTAQUER: {
									//qsdfqsd
									if (this.numero==1)
										carte.joueur1.attaquer();
									else
										carte.joueur2.attaquer();
									break;
								}
								case DEPOSER: {
									//
									if (this.numero==1)
										carte.joueur1.deposer();
									else
										carte.joueur2.deposer();
									break;
								}
								case RAMASSER: {
									//
									if (this.numero==1)
										carte.joueur1.ramasser();
									else
										carte.joueur2.ramasser();
									break;
								}
							}
							break;
						}
						case MESSAGE_CLOSE: {
							reponse = "Communication terminée avec le serveur Merci Au revoir !!!!"; 
							closeConnexion = true;
							break;
						}
					}
					if (m!=MESSAGE_CONVERSATION) {
						for(Socket c : listeClient) {
							if (c!=null && !closeConnexion) {
								envoyeurDonneeVersClient = new PrintWriter(c.getOutputStream());
								//System.out.println(MESSAGE_CARTE+"/"+carte.afficherFen());
								envoyeurDonneeVersClient.println(MESSAGE_CARTE+"/"+carte.afficherFen());
								envoyeurDonneeVersClient.flush();
								System.err.println("Envoyé au client n°"+(this.numero%2+1)+" message carte");
							}
						}
					}
					if(closeConnexion){
						System.err.println("COMMANDE CLOSE DETECTEE ! ");
						envoyeurDonneeVersClient = null;
						envoyeurDonneeVersClient = null;
						clientConnecte.close();
						break;
					}
				}
			}catch(SocketException e){
				System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
				//break;
			} catch (IOException e) {
				System.err.println("RIEN N4EST ENVOY2 ! ");
				e.printStackTrace();
			}
		}
		public Socket getClientConnecte() {
			return clientConnecte;
		}

		public void setClientConnecte(Socket clientConnecte) {
			this.clientConnecte = clientConnecte;
		}

		public PrintWriter getEnvoyeurDonneeVersClient() {
			return envoyeurDonneeVersClient;
		}

		public void setEnvoyeurDonneeVersClient(PrintWriter writer) {
			this.envoyeurDonneeVersClient = writer;
		}
		
		public BufferedReader getLecteurDonneeClient() {
			return lecteurDonneeClient;
		}

		public void setLecteurDonneeClient(BufferedReader reader) {
			this.lecteurDonneeClient = reader;
		}
		public int getNumero() {
			return numero;
		}
			
		public void setNumero(int numero) {
			this.numero = numero;
		}
	}
}
