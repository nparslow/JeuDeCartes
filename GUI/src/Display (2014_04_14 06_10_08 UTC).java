import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * La classe Display sert a implementer l'element graphique du jeu.
 * Elle utilise les jpanels de Java Swift.
 * Pour bouger une carte, l'utilisateur dois clicquer sur la carte et puis sur la pile
 * dans laquelle il veut la deplacer. S'il fait un faut (un mouvement interdit) la derniere
 * carte clique est retenu et utilise comme si c'etait le premiere du prochaine mouvement
 * @author by Nicholas on 27/12/13.
 */
public class Display {

    /** variables de la class:
     * nomMap et couleurMap = des mappings aux noms de fichier pour les cartes
     * backNumber = le style de l'arriere face des cartes
     * lastCardClicked = la derniere carte clique par l'utilisateur
     * lastPileClicked = la derniere pile clique par l'utilisateur
     * jeu = l'instance du jeu (joue actuellement)
     * jLayeredPane = le niveau de display dans lequel se trouve le plan arriere et les cartes
     * touteLesPiles = le JPanel dans lequel se trouvent seulement des piles de cartes
     * jframe = le fenetre du logiciel
     */
    private HashMap<NomDeCarte, String> nomMap;
    private HashMap<Couleur, String> couleurMap;
    private int backNumber = 1;

    int lastCardClicked;
    int lastPileClicked;

    private Jeu jeu;
    private JPanel toutesLesPiles;
    private JLayeredPane jLayeredPane;
    private JFrame jframe;

    /**
     * Initialisation, le jeu commence par defaut avec un jeu de SimpleSimon
     */
    public Display() {
        this(new SimpleSimon());
    }

    /**
     * Initialisation: les variables de la classe sont mises a leur valeurs defauts
     * la fenetre du logiciel est etablit, et le menubar est attache.
     * @param jeu = une instance de l'objet jeu que l'on veut jouer
     */
    public Display(Jeu jeu) {

        lastCardClicked = -1;
        lastPileClicked = -1;
        this.jeu = jeu;

        jframe = new JFrame("Jeu de Cartes");
        jframe.setSize(1024, 768);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nomMap = getNumeroMap();
        couleurMap = getSuitMap();

        jframe.setJMenuBar(getMenuBar());
        jframe.add(backgroundPlusCartes());
        jframe.setVisible(true);
    }

    /**
     * getMenuBar: creer un menu bar pour la fenetre du logiciel
     * (Alt A) pour l'obtenir
     * les options sont ajoutes:
     * 1) commencer un nouveau jeu (alt a suivi par alt n)
     * 2) changer le type de jeu (avec chaque option)
     * 3) un checkbox pour debug mode (les cartes seront montres par System.out)
     * 4) une option pour sortir et terminer le jeu
     * (j'aurai aimee ajouter un 'undo' ou meme 'tricher', mais je n'avais pas assez de temps
     * si un action s'est effectue, 'reDisplayFrame' est appele pour renouveller le display
     * @return le menu bar
     */
    public JMenuBar getMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menuAction = new JMenu("Actions (Alt A)");
        menuAction.setMnemonic(KeyEvent.VK_A);

        // traiter de nouveau
        ImageIcon newIcon = scaleImage(new ImageIcon(getClass().getResource("/resources/icons/cards-icon.png")), 18, 18);
        JMenuItem newMenuItem = new JMenuItem("Nouveau Jeu (Alt N)", newIcon);
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setToolTipText("Commencer un nouveau jeu");
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                jeu.reInitialise();
                reDisplayFrame();
            }
        });
        menuAction.add(newMenuItem);

        // changer le jeu
        JMenu changerJeu = new JMenu("Changer Jeu");
        changerJeu.setMnemonic(KeyEvent.VK_C);

        JMenuItem simpleSimon = new JMenuItem("Simple Simon");
        simpleSimon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean debugActuel = jeu.getDebugMode();
                jeu = new SimpleSimon();
                jeu.setDebugMode(debugActuel);
                reDisplayFrame();
            }
        });
        JMenuItem fortyAndEight = new JMenuItem("Forty and Eight");
        fortyAndEight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean debugActuel = jeu.getDebugMode();
                jeu = new FortyAndEight();
                jeu.setDebugMode(debugActuel);
                reDisplayFrame();
            }
        });
        JMenuItem fortyAndSeven = new JMenuItem("Forty and Seven");
        fortyAndSeven.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean debugActuel = jeu.getDebugMode();
                jeu = new FortyAndSeven();
                jeu.setDebugMode(debugActuel);
                reDisplayFrame();
            }
        });
        JMenuItem bataille = new JMenuItem("Bataille");
        bataille.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean debugActuel = jeu.getDebugMode();
                jeu = new Bataille();
                jeu.setDebugMode(debugActuel);
                reDisplayFrame();
            }
        });

        changerJeu.add(simpleSimon);
        changerJeu.add(fortyAndEight);
        changerJeu.add(fortyAndSeven);
        changerJeu.add(bataille);

        menuAction.add(changerJeu);
        menuAction.addSeparator();

        // debug mode
        JCheckBoxMenuItem debugMode = new JCheckBoxMenuItem("Debug Mode");
        debugMode.setState(jeu.getDebugMode());
        debugMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //System.out.println("debug activated");
                jeu.setDebugMode(!jeu.getDebugMode());
                reDisplayFrame();
            }
        });
        menuAction.add(debugMode);
        menuAction.addSeparator();

        // option :Sortir du jeu
        ImageIcon sortirIcon = scaleImage(new ImageIcon(getClass().getResource("/resources/icons/exit.png")), 18, 18);
        JMenuItem sortirMenuItem = new JMenuItem("Sortir (Alt S)", sortirIcon);
        sortirMenuItem.setMnemonic(KeyEvent.VK_S);
        sortirMenuItem.setToolTipText("Sortir de l'application");
        sortirMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        menuAction.add(sortirMenuItem);

        jMenuBar.add(menuAction);
        jMenuBar.setVisible(true);
        return jMenuBar;
    }

    /**
     * renouvler le display (par exemple quand un nouveau jeu est lance
     */
    public void reDisplayFrame() {
        jframe.remove(jLayeredPane);
        jframe.add(backgroundPlusCartes());
        jframe.revalidate();
        jframe.repaint();
    }

    /**
     * changer la taille d'un image (utilise pour les icons)
     * @param imageIcon l'image originale
     * @param sizeX le nouveau taille horizontal
     * @param sizeY le nouveau taille vertical
     * @return un ImageIcon (scaled)
     */
    public ImageIcon scaleImage(ImageIcon imageIcon, int sizeX, int sizeY) {
        Image img = imageIcon.getImage();
        Image newimg = img.getScaledInstance(sizeX, sizeY, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    /**
     * Combiner l'arriere fond avec les images de piles de cartes
     * @return un JLayeredPane avec les cartes plus eleve que l'arriere fond
     */
    public JLayeredPane backgroundPlusCartes() {
        jLayeredPane = new JLayeredPane();
        jLayeredPane.setPreferredSize(new Dimension(1024, 768));

        JPanel background = image("/resources/background/q02th.jpg");
        jLayeredPane.add(background, new Integer(-1));

        toutesLesPiles = toutePile();
        jLayeredPane.add(toutesLesPiles, new Integer(1));
        jLayeredPane.setSize(1024, 768);

        return jLayeredPane;
    }

    /**
     * Combiner toutes les Piles en un seul jpanel
     * @return le jpanel de toutes les piles
     */
    public JPanel toutePile( /*Jeu jeu */) {
        int[][] displayGroupPiles = jeu.getDisplayGroups();
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new GridBagLayout());
        for (int i = 0; i < displayGroupPiles.length; i++) {
            //System.out.println("i is "+ i);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = i;

            //ArrayList<PileCartes> displayGroup = jeu.getJeuPiles().getSubElements( displayGroupPiles[i] );
            combinedPanel.add(pileGrid(/*jeu,*/ displayGroupPiles[i]), c);
        }

        combinedPanel.setBounds(0, 0, 1024, 768);

        combinedPanel.setOpaque(false);
        //System.out.println( "size " + combinedPanel.getComponents().length);
        return combinedPanel;
    }

    /**
     * faire un rang de piles
     * @param pileNums = array d'int, avec les nombres identifiants des piles a utiliser dan ce rang
     * il y a beaucoup de comments ici parce que j'avais beaucoup de mal avec les jpanels
     * @return le rang en tant que jpanel
     */
    public JPanel pileGrid( /*Jeu jeu,*/ int[] pileNums) {
        JPanel jpanel = new JPanel();

        // the two layout options below have the same effect:
        //BoxLayout boxLayout = new BoxLayout(jpanel, BoxLayout.LINE_AXIS);
        //jpanel.setLayout(boxLayout);
        jpanel.setLayout( new GridLayout(0,pileNums.length) );

        //for ( PileCartes pile : groupePiles ) {
        int height = 0;
        int width = 0;
        for (int pileNum : pileNums) {
            //jpanel.add( pileDisplay( /*jeu,*/ pileNum, PileDisplayDirection.VERTICAL ) );
            // get the regle type and use it to determine the pile direction
            JPanel pd = pileDisplay( /*jeu,*/ pileNum, jeu.getJeuPiles().getPileCartes(pileNum).getPileDisplayDirection());
            jpanel.add( pd );
            height += pd.getPreferredSize().getHeight();
            width += pd.getPreferredSize().getWidth();
            //System.out.println(pd.getVisibleRect());
        }
        jpanel.setOpaque(false);
        //System.out.println("sizes " + jpanel.getHeight() + " " + jpanel.getWidth());
        //jpanel.computeVisibleRect(new Rectangle());
        //System.out.println("rect  " + jpanel.getVisibleRect());

        //JPanel checkSize = backCarte();
        //double minWidth = checkSize.getWidth();
        //double minHeight = checkSize.getHeight();
        //jpanel.setBounds(0,0,(int)minWidth,(int)minHeight);

        //jpanel.setBounds(0, 0, 1024, 768);
        //System.out.println(width );

        //jpanel.setBounds(0,0,width,height);
        //jpanel.setPreferredSize(new Dimension(width,height));
        return jpanel;
    }


    /**
     * combiner les cartes en forme de pile visuel.
     * on utilise les proprietes du pile pour determiner dans quel sens les cartes sont distribues etc.
     * un pile vide aura un rectangle gris
     * @param pileNum = le numero du pile dans le jeu
     * @param pileDisplayDirection = pour le moment horizontal ou vertical
     * @return un jpanel avec la pile de cartes
     */
    public JPanel pileDisplay(  /*Jeu jeu,*/ int pileNum, PileDisplayDirection pileDisplayDirection) {
        PileCartes pile = jeu.getJeuPiles().getPileCartes(pileNum);
        PileCarteVisibilite pileCarteVisibilite = pile.getPileCarteVisibilite();
        JPanel jpanel = new JPanel();
        //JPanel jpanel = new JPanel(new GridLayout(0,1)); // no space between tiles with this by default
        JLayeredPane jLayeredPane = new JLayeredPane();

        // l'arriere d'une carte nous donne la taille standard
        JPanel checkSize = backCarte();
        double minWidth = checkSize.getWidth();
        double minHeight = checkSize.getHeight();

        // determiner la position initial d'une carte et les deplacements horizontaux et verticaux
        double posX = 0;
        double posY = 0;
        double deltaHorizontal = 0;
        if (pileDisplayDirection == PileDisplayDirection.HORIZONTAL) {
            deltaHorizontal = 6;
        }
        double deltaVertical = 0;
        if (pileDisplayDirection == PileDisplayDirection.VERTICAL) {
            deltaVertical = 8;
        }

        /*
        if (pileCarteVisibilite == PileCarteVisibilite.SHOWALL) {
            deltaVertical *= 2; deltaHorizontal *= 2;
        } else if (pileCarteVisibilite == PileCarteVisibilite.SHOWNONEMIN ||
                pileCarteVisibilite == PileCarteVisibilite.SHOWTOPONLYMIN ) {
            deltaVertical *= 0.01; deltaHorizontal *= 0.01;
        }
        */
        if (pile.getMontreTailleDePile()) {
            if (pileCarteVisibilite == PileCarteVisibilite.SHOWALL) {
                deltaVertical *= 2;
                deltaHorizontal *= 2;
            }
        } else {
            deltaVertical *= 0.5;
            deltaHorizontal *= 0.5;
        }

        // cette partie n'est pas encore implementee
        if (!pile.getPileDisplayDirectionNatural() ) {
            deltaHorizontal = - deltaHorizontal;
            if (pileDisplayDirection == PileDisplayDirection.HORIZONTAL) {
                posX =  - deltaHorizontal*pile.size();
            }
            deltaVertical = - deltaVertical;
            if (pileDisplayDirection == PileDisplayDirection.VERTICAL) {
                posY =  - deltaVertical*pile.size();
            }
        }

        // si le pile est vide on montre un rectangle gris
        if (pile.size() == 0) {
            JPanel rectanglePanel = new JPanel(); // do i need a layout manager?
            /*JButton jButton = new JButton();
            jButton.setMaximumSize(new Dimension(minWidth, minHeight));
            jButton.setBackground(Color.gray);
            rectanglePanel.add(jButton);
            */
            rectanglePanel.repaint(0, 0, (int) minWidth, (int) minHeight);
            rectanglePanel.setOpaque(true);
            rectanglePanel.setBounds(0, 0, (int) minWidth, (int) minHeight);
            jLayeredPane.add(rectanglePanel, new Integer(0));
            makeActive(rectanglePanel, pileNum, -1);
        }

        // boucle sur les cartes dans les pile en les montrant ou les cachant selons les criterion du pile
        for (int i = 0; i < pile.size(); i++) {
            JPanel jPanelCarte;
            if (pileCarteVisibilite == PileCarteVisibilite.SHOWNONE ||
                    //pileCarteVisibilite == PileCarteVisibilite.SHOWNONEMIN ||
                    (pileCarteVisibilite == PileCarteVisibilite.SHOWTOPONLY && i != (pile.size() - 1)) ||
                    //(pileCarteVisibilite == PileCarteVisibilite.SHOWTOPONLYMIN && i != (pile.size() -1) ) ||
                    (pileCarteVisibilite == PileCarteVisibilite.SHOWODD && i % 2 != 0)) { // ODD counting from 1 not zero
                jPanelCarte = backCarte();
            } else {
                jPanelCarte = carte(pile.get(i));
            }
            makeActive(jPanelCarte, pileNum, i);
            //Dimension dimension = jPanelCarte.getSize();
            //int blah = jPanelCarte.
            //System.out.println( rectangle.getWidth() + " " + rectangle.getHeight() );

            //if (posX > 750) { deltaHorizontal = 0; }
            //if (posY > 750) { deltaVertical = 0; }
            //if (pile.size() > 26 && i > 25 && i != (pile.size()-1)) { deltaHorizontal = 0; deltaVertical = 0; }

            jPanelCarte.setBounds((int) (deltaHorizontal * i), (int) (deltaVertical * i), jPanelCarte.getWidth(), jPanelCarte.getHeight()); // still don't know how to best set these
            jPanelCarte.setBounds((int) posX, (int) posY, jPanelCarte.getWidth(), jPanelCarte.getHeight()); // still don't know how to best set these
            posX += deltaHorizontal;
            posY += deltaVertical;
            jLayeredPane.add(jPanelCarte, new Integer(i));
            minWidth += deltaHorizontal;
            minHeight += deltaVertical;
            //System.out.println(posX + " " + posY + " " + i);
        }
        //Rectangle rectangle = new Rectangle();
        //jLayeredPane.computeVisibleRect(rectangle);
        //System.out.println( minHeight );
        //jLayeredPane.setPreferredSize(new Dimension(minWidth,minHeight));
        if (pileCarteVisibilite == PileCarteVisibilite.SHOWTOPONLY ||
                pileCarteVisibilite == PileCarteVisibilite.SHOWALL) {
            // leave room for stacks of added cards
            minWidth += 12 * Math.abs(deltaHorizontal);
            minHeight += 12 * Math.abs(deltaVertical);
        }

        //minHeight = checkSize.getHeight() + deltaVertical * jeu.maxPileSize()[pile.getRegleType()];
        //minWidth = checkSize.getWidth() + deltaHorizontal * jeu.maxPileSize()[pile.getRegleType()];
        //minHeight = checkSize.getHeight() + Math.abs(deltaVertical) * pile.getMaxPileSize();
        //minWidth = checkSize.getWidth() + Math.abs(deltaHorizontal) * pile.getMaxPileSize();
        minWidth = posX + checkSize.getWidth() + 12 * Math.abs(deltaHorizontal);
        minHeight = posY + checkSize.getHeight() + 12 * Math.abs(deltaVertical);


        // cette ligne est indispensable, malheureusement je n'ai pas reussi le changer pour que ca marche dans
        // le cas de debug
        //if (minWidth > 400) { minWidth = 400; }
        jLayeredPane.setPreferredSize(new Dimension((int) Math.ceil(minWidth), (int) Math.ceil(minHeight)));

        jpanel.add(jLayeredPane);
        jpanel.setOpaque(false);

        //System.out.println(minHeight + " " + minWidth);
        return jpanel;
    }

    /**
     * renouveller le display si une carte a changer sa position ( seulement les piles affectes sont refaits)
     * @param pilesToUpdate = les numeros identifieurs de chaque pile a renouveller le display
     */
    public void updateDisplay(int[] pilesToUpdate) {
        for (int pileToUpdate : pilesToUpdate) {
            //System.out.println("updating pile: " + pileToUpdate);
            int[] coOrds = jeu.getDisplayGroup(pileToUpdate);
            //JPanel newPile = pileDisplay(pileToUpdate, PileDisplayDirection.VERTICAL);
            JPanel newPile = pileDisplay(pileToUpdate, jeu.getJeuPiles().getPileCartes(pileToUpdate).getPileDisplayDirection());
            JPanel oldPileGroup = (JPanel) toutesLesPiles.getComponent(coOrds[0]);
            //JPanel oldPile = oldPileGroup.getComponent(coOrds[1]);
            oldPileGroup.remove(coOrds[1]);
            oldPileGroup.add(newPile, coOrds[1]);
            //oldPileGroup.invalidate();
            oldPileGroup.validate();
            oldPileGroup.repaint();
            jeu.gagne();
        }
    }

    /**
     *
     * @return un Jpanel montrant l'arriere d'une carte
     */
    public JPanel backCarte() {
        // backNumber must be 1 or 2 atm
        return image("resources/cartes/b" + backNumber + "fv.png");
    }

    /**
     *
     * @param carte = la carte qui nous interesse
     * @return un Jpanel montrant la face d'une carte
     */
    public JPanel carte(Carte carte) {
        return image("resources/cartes/" + couleurMap.get(carte.couleur) + nomMap.get(carte.nom) + ".png");
    }

    /**
     * rendre une carte active : c'est a dire capable d'etre clique
     * @param cartePanel - le jpanel de la carte
     * @param pileNum = le numero de la pile de la carte
     * @param carteNum = le numero de la carte dans la pile
     */
    public void makeActive(JPanel cartePanel, final int pileNum, final int carteNum) {
        //JPanel cartePanel = carte( carte );
        cartePanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                //System.out.println( "clicked on " + pileNum + " carte " + carteNum);
                int[] alteredPiles = jeu.bougeDisplay(lastPileClicked, pileNum, lastCardClicked);
                if (alteredPiles.length > 0) {
                    //System.out.println("move successful");
                    updateDisplay(alteredPiles);
                    lastPileClicked = -1;
                    lastCardClicked = -1;
                }
                lastPileClicked = pileNum;
                lastCardClicked = carteNum;
            }
        });
        //return cartePanel;
    }

    /**
     * convertir un image en un jpanel
     * @param filename = le filename de l'image
     * @return un jpanel de l'image
     */
    public JPanel image(String filename) {
        JPanel jpanel = new JPanel(new BorderLayout(0, 0)); // need to change layout to avoid 5pixel line, 0,0 = hgap,vgap
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(getClass().getResource(filename));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            jpanel.add(picLabel);
            jpanel.setBounds(0, 0, myPicture.getWidth(), myPicture.getHeight());
            jpanel.setOpaque(false);
        } catch (IOException ioErr) {
            System.err.println("file not found" + ioErr);
        }
        return jpanel;
    }

    /**
     * creer le hashmap pour les couleurs cartes evers un string (dans le filename), a ce point il n'y a qu'une option
     * @return
     */
    public HashMap<Couleur, String> getSuitMap() {
        HashMap<Couleur, String> mapping = new HashMap<Couleur, String>();
        mapping.put(Couleur.TREFLE, "c");
        mapping.put(Couleur.CARREAU, "d");
        mapping.put(Couleur.COEUR, "h");
        mapping.put(Couleur.PIQUE, "s");
        return mapping;
    }

    /**
     * creer un hashpmap pour les numeros des cartes envers un string (dans le filename), a ce point il n'y a qu'unne option
      */
    public HashMap<NomDeCarte, String> getNumeroMap() {
        HashMap<NomDeCarte, String> mapping = new HashMap<NomDeCarte, String>();
        for (NomDeCarte nomDeCarte : NomDeCarte.values()) {
            switch (nomDeCarte) {
                case VALET:
                    mapping.put(nomDeCarte, "j");
                    break;
                case DAME:
                    mapping.put(nomDeCarte, "q");
                    break;
                case ROI:
                    mapping.put(nomDeCarte, "k");
                    break;
                default:
                    mapping.put(nomDeCarte, "" + nomDeCarte.getValeurDefault());
            }
        }
        return mapping;
    }


}
