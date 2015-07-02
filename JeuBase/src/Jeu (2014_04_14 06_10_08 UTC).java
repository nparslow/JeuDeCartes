import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * un jeu est une classe abstraite, avec les operations generale aux jeux du logiciel
 * Created by Nicholas on 23/12/13.
 */
public abstract class Jeu {
    protected JeuPiles jeuPiles;
    protected boolean gagne = false;
    protected int nombre_decks;
    protected boolean shuffle;
    protected PileProperties[] pilePropertieses; // caries default properties
    protected boolean debugMode;

    /**
     * creer le deck a partir des parametres du jeu
     * @param nombre_decks
     * @return
     */
    protected LinkedList<Carte> creerDeck(int nombre_decks) {

        //int n_cartes = Couleur.values().length * NomDeCarte.values().length * nombre_decks;
        LinkedList<Carte> decks = new LinkedList<Carte>();
        for (int i = 0; i < nombre_decks; i++) {
            for (Couleur couleur : Couleur.values()) {
                for (NomDeCarte nomDeCarte : NomDeCarte.values()) {
                    decks.add(new Carte(nomDeCarte, couleur));
                    //System.out.println( "in here ");
                }
            }
        }
        return decks;
    }

    /**
     * initialise les decks et les piles
     * @param nombre_decks combien de decks
     * @param shuffle faire un battage ou non
     * @param pilePropertieses des proprietes des piles au debut
     */
    protected void initialise(int nombre_decks, boolean shuffle, PileProperties[] pilePropertieses) {
        this.nombre_decks = nombre_decks;
        this.shuffle = shuffle;
        this.pilePropertieses = pilePropertieses;
        this.debugMode = false;

        LinkedList<Carte> deck = creerDeck(nombre_decks);
        if (shuffle) Collections.shuffle(deck);
        //jeuPiles = new JeuPiles( deck, groupePileInstructions );
        jeuPiles = new JeuPiles(deck, pilePropertieses);
        setDisplayDirections();
        setMaxPileSize();
    }

    /**
     * recommencer depuis le debut
     */
    public void reInitialise() {
        initialise(nombre_decks, shuffle, pilePropertieses);
    }

    //public abstract boolean bouge( int typePile1, int numPile1, int typePile2, int numPile2, int premiereCarte );
    //public abstract boolean bouge( int numPile1, int numPile2, int premiereCarte );
    /**
     * returns list of affected pile numbers
      */
    public abstract int[] bouge(int numPile1, int numPile2, int premiereCarte);

    /**
     * bouger et changer le display
     * @param numPile1
     * @param numPile2
     * @param premiereCarte
     * @return
     */
    public int[] bougeDisplay(int numPile1, int numPile2, int premiereCarte) {
        int[] outInts = bouge(numPile1, numPile2, premiereCarte);
        displayCartes();
        return outInts;
    }

    /**
     * retirer une carte du jeu
     * @param typePile1
     * @param numPile1
     * @param premiereCarte
     * @return
     */
    public abstract boolean retirer(int typePile1, int numPile1, int premiereCarte);

    /**
     * dire si qqu a gagne
     * @return
     */
    public abstract boolean gagne();

    /** retourne l'element jeupiles
     *
     * @return
     */
    public JeuPiles getJeuPiles() {
        return jeuPiles;
    } // change to protected later?

    //public abstract ArrayList<ArrayList<PileCartes>> getDisplayGroups();

    /**
     *
     * @return les groupes de display
     */
    public abstract int[][] getDisplayGroups();

    /** return les groupes de display pour une pile particulier
     *
     * @param pileNumber
     * @return
     */
    public int[] getDisplayGroup(int pileNumber) {
        int[][] displayGroups = getDisplayGroups();
        for (int i = 0; i < displayGroups.length; i++) {
            for (int j = 0; j < displayGroups[i].length; j++) {
                if (displayGroups[i][j] == pileNumber) {
                    return new int[]{i, j};
                }
            }
        }
        // should throw an exception rather than return this I guess
        return new int[]{};
    }

    /** donner les instructons pour le sens de display
    /*
     */
    public abstract void setDisplayDirections();

    /**
     *  determiner le taille max des piles
     */
    public abstract void setMaxPileSize();

    /** changer le mode de debug
     *
     * @param debugMode
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        for ( int i = 0; i < jeuPiles.size(); i ++) {
            if ( debugMode == false ) {
                jeuPiles.getPileCartes(i).setMontreTailleDePile( pilePropertieses[i].getPileTailleVisibilite() );
                jeuPiles.getPileCartes(i).setPileCarteVisibilite( pilePropertieses[i].getPileCarteVisibilite() );
            } else {
                //jeuPiles.getPileCartes(i).setMontreTailleDePile( true ); // pas encore implemente, ne marche pas encore pour 40 & 8/7
                jeuPiles.getPileCartes(i).setPileCarteVisibilite( PileCarteVisibilite.SHOWALL );
            }
        }
    }

    /** dire quel est le mode de debug
     *
     * @return
     */
    public boolean getDebugMode() {
        return this.debugMode;
    }

    /** montre les cartes par std;:out
     *
     */
    public void displayCartes() {
        if (debugMode) {
            int pileNo = 0;
            for (PileCartes pile : jeuPiles) {
                System.out.printf("[%2s] [%2s] : ", pile.getRegleType(), pileNo);
                for (Carte carte : pile) {
                    System.out.printf("%3s ", carte.toString());
                }
                System.out.print("   (" + pile.size() + " cards)");
                System.out.print("\n");
                pileNo++;
            }
        }
    }

    /**
     * plus utilise
     */
    private void move() {
        while (!gagne()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("enter pile to move cards from: ");
            int pileNum1 = scanner.nextInt();
            System.out.print("enter pile to move cards to  : ");
            int pileNum2 = scanner.nextInt();
            System.out.print("enter card no.               : ");
            int cardNum = scanner.nextInt();
            boolean ok = bougeDisplay(pileNum1, pileNum2, cardNum).length > 0;
            if (!ok) System.out.println("move failed");
        }
    }
}
