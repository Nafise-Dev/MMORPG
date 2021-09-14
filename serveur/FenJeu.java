package serveur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

   public class FenJeu extends JFrame implements Runnable{
	   
	   // partie donné
	   private Socket connexion = null;
	   private PrintWriter writer = null;
	   private BufferedReader reader = null;
	   
	   private String msg;
	   // partie IHM

	   private static final long serialVersionUID = 1L;
		
		
	   private JLabel labelADR;
	   private JLabel labelFRC;
	   private JLabel labelRST;
	   private JLabel labelPA;
		
	   private JLabel labelADR1;
	   private JLabel labelFRC1;
	   private JLabel labelRST1;
		
	   private JLabel labelMainDroite;
	   private JLabel labelMainGauche;
		
	   private JButton haut;
	   private JButton bas;
	   private JButton droite;
	   private JButton gauche;
		
	   private JButton ramasser;
	   private JButton deposer;
	   private JButton attaquer;
		
	   private JButton boutonEnvoyer;
	   public JTextField envoyerTexte = new JTextField(5);
		
		
		//private JLabel[][] boutonCase;
		
		private JTextArea caseCarte;
		private JTextArea conversation= new JTextArea("");
		
				
		static final int BOUTON_HAUT = 1;
		static final int BOUTON_BAS = 2;
		static final int BOUTON_DROITE = 3;
		static final int BOUTON_GAUCHE = 4;
		static final int BOUTON_RAMASSER = 5;
		static final int BOUTON_DEPOSER = 6;
		static final int BOUTON_ATTAQUER = 7;
		static final int BOUTON_ENVOYER = 8;
		
		private static final String ATTAQUER = "attaquer";
		private static final String RAMASSER = "ramasser";
		private static final String DEPOSER = "deposer";
		private static final String HAUT = "haut";
		private static final String BAS = "bas";
		private static final String DROITE = "droite";
		private static final String GAUCHE = "gauche";
//		private static final String MESSAGE_CLOSE = "close";
		private static final String MESSAGE_CONVERSATION = "message";
		private static final String MESSAGE_CARTE = "catre";
		private static final String MESSAGE_PA = "pa";
		private static final String MESSAGE_DEPLACEMENT = "deplacement";
		private static final String MESSAGE_ACTION = "action";
		
		public FenJeu(String host, int port) {
			super("Jeu MMORPG");
			this.initComposants();
			this.initEcouteurs();
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.centrer(0.6);
			this.setVisible(true);
			try {
		         connexion = new Socket(host, port);
		         
		      } catch (UnknownHostException e) {
		         e.printStackTrace();
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		}
		public void run(){
			   Thread Lecteur = new Thread(new Lecteur());
			   Lecteur.start();
			   System.out.println("Lecteur créé .......................");
//			   Thread envoyeur = new Thread(new Envoyeur());
//			   envoyeur.start();
			   System.out.println(" Envoyeur créé .......................");
		   }
		public static void main(String[] args) {
			FenJeu c = new FenJeu("localhost", 63334);
			Thread t = new Thread(c);
		    t.start();
		       		       
		}
		   public class Lecteur implements Runnable{
				public void run() {
					try {	    
						reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
						writer = new PrintWriter(connexion.getOutputStream(), true);
						// au début on recoit un message de bienvenu en 4 flush du serveur
						String reponse;
						for(int i=0; i<4;i++) {
							reponse = reader.readLine();
							System.out.println(reponse);
							conversation.setText(conversation.getText()+reponse+"\n");
						}
						reponse = reader.readLine();
						caseCarte.setText(reponse);
						for(int i=0; i<21;i++) {
							reponse = reader.readLine();
							caseCarte.setText(caseCarte.getText()+"\n"+reponse);
						}
						//On attend la réponse
						while(true) {
							//TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
							reponse = reader.readLine();
							if(reponse.length()>0) {
								System.out.println(reponse);
								try {
									@SuppressWarnings("resource")
									Scanner sc = new Scanner(reponse);
									sc.useDelimiter("/");
									String r = sc.next();
									switch(r) {
										case MESSAGE_CONVERSATION :{
											conversation.setText(conversation.getText()+"\n\tJoueur2 : "+sc.next());
											break;
										}
										case MESSAGE_CARTE :{
											caseCarte.setText(sc.next());
											for(int i=0;i<22;i++) {
												caseCarte.setText(caseCarte.getText()+"\n"+reader.readLine());
											}
											break;
										}
										case MESSAGE_PA :{
											break;
										}
									}
								}catch(NullPointerException e) {
									System.out.println(reponse);
								}
							}
						}	
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
		   	}
		
		private void initComposants() {
			JPanel panPrincipal = new JPanel();
			panPrincipal.setLayout(new BorderLayout());
			this.add(panPrincipal);
			
			panPrincipal.add(panInfoPNJ(),BorderLayout.SOUTH);
			panPrincipal.add(panInfoJoueur(),BorderLayout.NORTH);
			panPrincipal.add(panCarte(),BorderLayout.CENTER);
			panPrincipal.add(panAction(),BorderLayout.EAST);
			panPrincipal.add(panConversaton(),BorderLayout.WEST);
		}
		public void initEcouteurs() {
			this.haut.addActionListener(new EcouteurBoutons(BOUTON_HAUT));
			this.bas.addActionListener(new EcouteurBoutons(BOUTON_BAS));
			this.droite.addActionListener(new EcouteurBoutons(BOUTON_DROITE));
			this.gauche.addActionListener(new EcouteurBoutons(BOUTON_GAUCHE));
			this.ramasser.addActionListener(new EcouteurBoutons(BOUTON_RAMASSER));
			this.deposer.addActionListener(new EcouteurBoutons(BOUTON_DEPOSER));
			this.attaquer.addActionListener(new EcouteurBoutons(BOUTON_ATTAQUER));
			this.boutonEnvoyer.addActionListener(new EcouteurBoutons(BOUTON_ENVOYER));
		}
		
		public JPanel panInfoJoueur() {
			
			JPanel pInfo=new JPanel(new GridLayout(0,1));
			
			JPanel pI=new JPanel();
			JLabel lI=new JLabel("Information du joueur");
			pI.add(lI);
			pInfo.add(pI);
			
			JPanel pI2=new JPanel(new GridLayout(0,3));
			labelADR=new JLabel("adr");
			pI2.add(labelADR);
			labelFRC=new JLabel("frc");
			pI2.add(labelFRC);
			labelRST=new JLabel("rst");
			pI2.add(labelRST);
			labelPA=new JLabel("Point d'action : ");
			pI2.add(labelPA);
			
			labelMainDroite=new JLabel("Main Droite : ");
			pI2.add(labelMainDroite);
			labelMainGauche=new JLabel("Main Gauche : ");
			pI2.add(labelMainGauche);
			
			pInfo.add(pI2);
			pInfo.setBorder(BorderFactory.createEtchedBorder());
			pInfo.setBackground(Color.GREEN);
			return pInfo;
		}
		
		public JPanel panInfoPNJ() {
			JPanel pInfo=new JPanel(new GridLayout(0,3));
			labelADR1=new JLabel("adr1");
			pInfo.add(labelADR1);
			labelFRC1=new JLabel("frc1");
			pInfo.add(labelFRC1);
			labelRST1=new JLabel("rst2");
			pInfo.add(labelRST1);
			
			pInfo.setBorder(BorderFactory.createEtchedBorder());
			return pInfo;
		}
		
		public JPanel panCarte() {
			JPanel pCarte = new JPanel(new BorderLayout());
			
			caseCarte=new JTextArea(50,10);
			caseCarte.setEditable(false);
			
			pCarte.add(caseCarte,BorderLayout.CENTER);
			pCarte.setBorder(BorderFactory.createEtchedBorder());
			return pCarte;
			
		}
		
		public JPanel panConversaton() {
			JPanel pan = new JPanel(new BorderLayout());
			
			JScrollPane panTexteConversation=new JScrollPane();
			conversation = new JTextArea(10,25);
			conversation.setEditable(false);
			panTexteConversation.setViewportView(conversation);
			pan.setBorder(BorderFactory.createEtchedBorder());
			pan.add(panTexteConversation,BorderLayout.CENTER);
			
			JPanel panEnvoyer = new JPanel();
			envoyerTexte= new JTextField(10);
			panEnvoyer.add(envoyerTexte);
			boutonEnvoyer= new JButton("envoyer");
			panEnvoyer.add(boutonEnvoyer);
			panEnvoyer.setBorder(BorderFactory.createEtchedBorder());
			pan.add(panEnvoyer,BorderLayout.SOUTH);
			
			pan.setBorder(BorderFactory.createEtchedBorder()); 
			return pan;
		}
		public JPanel panAction() {
			JPanel pan = new JPanel(new GridLayout(0,1));
			
			JPanel panB = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			haut=new JButton("▲");
			c.fill = GridBagConstraints.PAGE_START;
			c.gridx = 1;
			c.gridy = 0;
			panB.add(haut, c);
			bas = new JButton("▼");
			c.fill = GridBagConstraints.PAGE_END;
			c.gridx = 1;
			c.gridy = 2;
			panB.add(bas, c);
			droite= new JButton("►");
			c.fill = GridBagConstraints.LINE_END;
			c.gridx = 2;
			c.gridy = 1;
			panB.add(droite, c);
			gauche = new JButton("◄");
			c.fill = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.gridy = 1;
			
			panB.add(gauche, c);
			panB.setBorder(BorderFactory.createEtchedBorder()); 
			
			
			ramasser=new JButton("Ramasser");
			pan.add(ramasser);
			deposer=new JButton("Deposer");
			pan.add(deposer);
			
			attaquer=new JButton("Attaquer");
			pan.add(attaquer);
			pan.add(panB);
			
			
			pan.setBorder(BorderFactory.createEtchedBorder()); 
			return pan;
		}
		
		public void centrer(double d) {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dim = tk.getScreenSize();
			int largeur = (int) (d * dim.width);
			int longueur = (int) (d * dim.height);
			this.setBounds((dim.width - largeur) / 2, (dim.height - longueur) / 2, largeur, longueur);
		}
		class EcouteurBoutons implements ActionListener {
			 
			private int sens;
	 
			public EcouteurBoutons(int sens) {
				this.sens = sens;
			}
	 
			public void actionPerformed(ActionEvent e) {
				switch (sens) {
				case BOUTON_HAUT: {
					System.out.println("Bouton HAUT " + BOUTON_HAUT);
					System.out.println("message à envoyer : "+HAUT);
					System.out.println(MESSAGE_DEPLACEMENT+"/"+HAUT);
					writer.println(MESSAGE_DEPLACEMENT+"/"+HAUT);
					writer.flush();
					//carte.joueur.deplacerFEN(BOUTON_HAUT);
					break;
				}
				case BOUTON_BAS: {
					System.out.println("Bouton BAS " + BOUTON_BAS);
					System.out.println("message à envoyer : "+BAS);
					System.out.println(MESSAGE_DEPLACEMENT+"/"+BAS);
					writer.println(MESSAGE_DEPLACEMENT+"/"+BAS);
					writer.flush();
					//carte.joueur.deplacerFEN(BOUTON_BAS);
					break;
				}
				case BOUTON_DROITE:{
					System.out.println("Bouton DROITE " + BOUTON_DROITE);
					System.out.println("message à envoyer : "+DROITE);
					System.out.println(MESSAGE_DEPLACEMENT+"/"+DROITE);
					writer.println(MESSAGE_DEPLACEMENT+"/"+DROITE);
					writer.flush();
					//carte.joueur.deplacerFEN(BOUTON_DROITE);
					break;
				}case BOUTON_GAUCHE:{
					System.out.println("Bouton GAUCHE " + BOUTON_GAUCHE);
					System.out.println("message à envoyer : "+GAUCHE);
					System.out.println(MESSAGE_DEPLACEMENT+"/"+GAUCHE);
					writer.println(MESSAGE_DEPLACEMENT+"/"+GAUCHE);
					writer.flush();
					//carte.joueur.deplacerFEN(BOUTON_GAUCHE);
					break;
					}
				case BOUTON_RAMASSER:{
					System.out.println("Bouton RAMASSER " + BOUTON_RAMASSER);
					System.out.println("message à envoyer : "+RAMASSER);
					writer.println(MESSAGE_ACTION+"/"+RAMASSER);
					writer.flush();
					//carte.joueur.ramasser();
					//labelMainDroite.setText("Main Droite : "+carte.joueur.getMainDroite());
					break;
					}
				case BOUTON_DEPOSER:{
					System.out.println("Bouton DEPOSER " + BOUTON_DEPOSER);
					System.out.println("message à envoyer : "+DEPOSER);
					System.out.println(MESSAGE_ACTION+"/"+DEPOSER);
					writer.println(MESSAGE_ACTION+"/"+DEPOSER);
					writer.flush();
					break;
					}
				case BOUTON_ATTAQUER:{
					System.out.println("Bouton ATTAQUE " + BOUTON_ATTAQUER);
					System.out.println("message à envoyer : "+ATTAQUER);
					System.out.println(MESSAGE_ACTION+"/"+ATTAQUER);
					writer.println(MESSAGE_ACTION+"/"+ATTAQUER);
					writer.flush();
					//carte.joueur.attaquer();
					//labelFRC1.setText(carte.pnj.infoFRC());
					break;
					}
				case BOUTON_ENVOYER :{
					System.out.println("Bouton ENVOYER " + BOUTON_ENVOYER);
					msg = envoyerTexte.getText();
					if (msg!=null && msg.length()!=0) {
						System.out.println("message à envoyer : "+msg);
						System.out.println(MESSAGE_CONVERSATION+"/"+msg);
						writer.println(MESSAGE_CONVERSATION+"/"+msg);
						writer.flush();
						// on modifie le texte de la conversation
						conversation.setText(conversation.getText()+"\nMoi : "+msg);
					}
					// le texte écrit dans la zone est effacé pour permettre d'envoyer un autre message
					envoyerTexte.setText("");
					break;
					}
				default:
					break;
				}
				//caseCarte.setText(carte.afficherFen());
				//labelPA.setText("Point d'action : "+carte.joueur.getPointAction());
			}

		}
		
	}
//}