import java.util.Scanner;

/**
 * Created by Nicholas on 23/12/13.
 */
public class Tester {
    public static void main(String[] args) {
        /*
        //Jeu jeu = new SimpleSimon();
        //Jeu jeu = new FortyAndEight();
        //Jeu jeu = new FortyAndSeven();
        Jeu jeu = new Bataille();
        JeuPiles jeuPiles = jeu.getJeuPiles();
        displayCartes(jeuPiles);
        move( jeu );
        */
        Display display = new Display();
    }


    private static void displayCartes(JeuPiles jeuPiles) {
        int type = 0;
        //for ( GroupePiles piles : jeuPiles ){
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
        type++;
        //}
    }

    private static void move(Jeu jeu) {

        //System.out.println(jeu.gagne());
        while (!jeu.gagne()) {
            Display display = new Display(jeu);
            Scanner scanner = new Scanner(System.in);
            System.out.print("enter pile to move cards from: ");
            int pileNum1 = scanner.nextInt();
            //System.out.print("pile no.1 ");
            //int pileNo1 = scanner.nextInt();
            System.out.print("enter pile to move cards to  : ");
            int pileNum2 = scanner.nextInt();
            //System.out.print("pile 2 no. ");
            //int pileNo2 = scanner.nextInt();
            System.out.print("enter card no.               : ");
            int cardNum = scanner.nextInt();
            boolean ok = jeu.bouge(pileNum1, pileNum2, cardNum).length > 0;
            if (!ok) System.out.println("move failed");
            displayCartes(jeu.getJeuPiles());
        }
    }

}
