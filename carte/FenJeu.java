package carte;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;




public class FenJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Carte carte;
	
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
	
	
	
	//private JLabel[][] boutonCase;
	
	private JTextArea caseCarte;
	
	private Timer timerPA;
	
	static final int BOUTON_HAUT = 1;
	static final int BOUTON_BAS = 2;
	static final int BOUTON_DROITE = 3;
	static final int BOUTON_GAUCHE = 4;
	
	static final int BOUTON_RAMASSER = 5;
	static final int BOUTON_DEPOSER = 6;
	static final int BOUTON_ATTAQUER = 7;
	
	public FenJeu() {
		super("Jeu MMORPG");
		this.carte=new Carte();
		this.initComposants();
		this.initEcouteurs();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.centrer(0.6);
		this.setVisible(true);
		timerPA = new Timer();
		timerPA.schedule(new TimerTask() {
						public void run() {
							labelPA.setText("Point d'action : "+String.valueOf(carte.joueur1.getPointAction()));
						}
		},1, 5000);
		
		Timer tm = new Timer();
		tm.schedule(new TimerTask() {
						public void run() {
							carte.monstre.deplacer();
							caseCarte.setText(carte.afficherFen());
						}
		},1, 1000);
	}
	private void initComposants() {
		JPanel panPrincipal = new JPanel();
		panPrincipal.setLayout(new BorderLayout());
		this.add(panPrincipal);
		
		panPrincipal.add(panInfoPNJ(),BorderLayout.SOUTH);
		panPrincipal.add(panInfoJoueur(),BorderLayout.NORTH);
		panPrincipal.add(panCarte(),BorderLayout.CENTER);
		panPrincipal.add(panAction(),BorderLayout.EAST);
		panPrincipal.add(panFichier(),BorderLayout.WEST);
	}
	public void initEcouteurs() {
		this.haut.addActionListener(new EcouteurBoutons(BOUTON_HAUT));
		this.bas.addActionListener(new EcouteurBoutons(BOUTON_BAS));
		this.droite.addActionListener(new EcouteurBoutons(BOUTON_DROITE));
		this.gauche.addActionListener(new EcouteurBoutons(BOUTON_GAUCHE));
		this.ramasser.addActionListener(new EcouteurBoutons(BOUTON_RAMASSER));
		this.deposer.addActionListener(new EcouteurBoutons(BOUTON_DEPOSER));
		this.attaquer.addActionListener(new EcouteurBoutons(BOUTON_ATTAQUER));
		
//		this.addKeyListener(new EcouteurClavier(KeyEvent.VK_KP_UP));
//		this.addKeyListener(new EcouteurClavier(KeyEvent.VK_KP_DOWN));
//		this.addKeyListener(new EcouteurClavier(KeyEvent.VK_KP_LEFT));
//		this.addKeyListener(new EcouteurClavier(KeyEvent.VK_RIGHT));
	}
	public JPanel panInfoJoueur() {
		
		JPanel pInfo=new JPanel(new GridLayout(0,1));
		
		JPanel pI=new JPanel();
		JLabel lI=new JLabel("Information du joueur");
		pI.add(lI);
		pInfo.add(pI);
		
		JPanel pI2=new JPanel(new GridLayout(0,3));
		labelADR=new JLabel(this.carte.joueur1.infoADR());
		pI2.add(labelADR);
		labelFRC=new JLabel(this.carte.joueur1.infoFRC());
		pI2.add(labelFRC);
		labelRST=new JLabel(this.carte.joueur1.infoRST());
		pI2.add(labelRST);
		labelPA=new JLabel("Point d'action : "+this.carte.joueur1.getPointAction());
		pI2.add(labelPA);
		
		labelMainDroite=new JLabel("Main Droite : "+this.carte.joueur1.getMainDroite());
		pI2.add(labelMainDroite);
		labelMainGauche=new JLabel("Main Gauche : "+this.carte.joueur1.getMainGauche());
		pI2.add(labelMainGauche);
		
		pInfo.add(pI2);
		pInfo.setBorder(BorderFactory.createEtchedBorder());
		pInfo.setBackground(Color.GREEN);
		return pInfo;
	}
	
	public JPanel panInfoPNJ() {
		JPanel pInfo=new JPanel(new GridLayout(0,3));
		labelADR1=new JLabel(this.carte.pnj.infoADR());
		pInfo.add(labelADR1);
		labelFRC1=new JLabel(this.carte.pnj.infoFRC());
		pInfo.add(labelFRC1);
		labelRST1=new JLabel(this.carte.pnj.infoRST());
		pInfo.add(labelRST1);
		
		pInfo.setBorder(BorderFactory.createEtchedBorder());
		return pInfo;
	}
	
	public JPanel panCarte() {
		JPanel pCarte = new JPanel(new BorderLayout());
		
		pCarte.setMinimumSize(new Dimension(300,500));
		pCarte.setMaximumSize(new Dimension(300,500));

		caseCarte=new JTextArea(carte.afficherFen());
		
		pCarte.add(caseCarte);
		pCarte.setBorder(BorderFactory.createEtchedBorder());
		return pCarte;
		
	}
	
	public JPanel panFichier() {
		JPanel pan = new JPanel();
//		 

// 
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
	public static void main(String[] args) {
		new FenJeu();
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
				carte.joueur1.deplacerFEN(BOUTON_HAUT);
				break;
			}
			case BOUTON_BAS: {
				System.out.println("Bouton BAS " + BOUTON_BAS);
				carte.joueur1.deplacerFEN(BOUTON_BAS);
				break;
			}
			case BOUTON_DROITE:{
				System.out.println("Bouton DROITE " + BOUTON_DROITE);
				carte.joueur1.deplacerFEN(BOUTON_DROITE);
				break;
			}case BOUTON_GAUCHE:{
				System.out.println("Bouton GAUCHE " + BOUTON_GAUCHE);
				carte.joueur1.deplacerFEN(BOUTON_GAUCHE);
				break;
				}
			case BOUTON_RAMASSER:{
				System.out.println("Bouton RAMASSER " + BOUTON_RAMASSER);
				carte.joueur1.ramasser();
				labelMainDroite.setText("Main Droite : "+carte.joueur1.getMainDroite());
				break;
				}
			case BOUTON_DEPOSER:{
				System.out.println("Bouton DEPOSER " + BOUTON_DEPOSER);
				carte.joueur1.deposer();
				labelMainDroite.setText("Main Droite : "+carte.joueur1.getMainDroite());
				break;
				}
			case BOUTON_ATTAQUER:{
				System.out.println("Bouton ATTAQUE " + BOUTON_ATTAQUER);
				carte.joueur1.attaquer();
				labelFRC1.setText(carte.pnj.infoFRC());
				break;
				}
			default:
				break;
			}
			caseCarte.setText(carte.afficherFen());
			labelPA.setText("Point d'action : "+carte.joueur1.getPointAction());
		}

	}
	
}
