/**
 * Bataille = extension de class Jeu,
 * un deck de cartes
 * 2 joueurs, mais l'utilisateur doit faire bouger les cartes
 * Created by Nicholas on 23/12/13.
 */
public class Bataille extends Jeu {

    /**
     * initialisation: 4 types de piles, 2 pour chaque joueur
     */
    public Bataille() {

        int nombre_decks = 1;
        int[] distributionInitial = new int[]{26, 26, 0, 0};
        int[] regleTypes = new int[]{0, 0, 1, 1};
        PileProperties[] pilePropertieses = new PileProperties[]{
                new PileProperties(distributionInitial[0], regleTypes[0], PileCarteVisibilite.SHOWNONE, true, true, 0),
                new PileProperties(distributionInitial[1], regleTypes[1], PileCarteVisibilite.SHOWNONE, true, true, 1),
                new PileProperties(distributionInitial[2], regleTypes[2], PileCarteVisibilite.SHOWODD, true, true, 0),
                new PileProperties(distributionInitial[3], regleTypes[3], PileCarteVisibilite.SHOWODD, true, true, 1)

        };
        boolean shuffle = true;
        initialise(nombre_decks, shuffle, pilePropertieses);
        //LinkedList<Carte> deck = creerDeck( nombre_decks );
        //Collections.shuffle( deck );
        //jeuPiles = new JeuPiles( deck, distribution_initial );
    }

    /**
     * obtenir une pile a partir de son regle type et son joueur
     * @param typePile
     * @param joueur
     * @return le correspondant pile de cartes
     */
    public PileCartes getPileCartes(int typePile, int joueur) {
        return jeuPiles.getPileCartes(typePile * 2 + joueur);
    }

    // need version of bouge with 'joueur', atm only player 0?

    /**
     * bouge est complique pour la bataille
     * @param numPile1
     * @param numPile2
     * @param premiereCarte
     * @return
     */
    public int[] bouge(int numPile1, int numPile2, int premiereCarte) {
        if (jeuPiles.estTransferPossible(numPile1, numPile2, premiereCarte)) {
            PileCartes pile1 = jeuPiles.getPileCartes(numPile1);
            PileCartes pile2 = jeuPiles.getPileCartes(numPile2);
            int typePile1 = pile1.getRegleType();
            int typePile2 = pile2.getRegleType();
            // add cards case:
            if (typePile1 == 0 && numPile1 == 0 && typePile2 == 1 && numPile2 == (2 + 0)
                    && premiereCarte == pile1.size() - 1
                    && getPileCartes(0, 0).size() > 0 && getPileCartes(0, 1).size() > 0) { //both have cards
                if (getPileCartes(1, 1).size() == 0) {     // it's a first round
                    jeuPiles.transfer(numPile1, numPile2, premiereCarte); // human move
                    jeuPiles.transfer(1, 3, getPileCartes(0, 1).size() - 1);             // computer move
                    return new int[]{0, 1, 2, 3};
                } else if (getPileCartes(1, 1).size() % 2 == 0 ||
                        getPileCartes(1, 0).get(getPileCartes(1, 0).size() - 1).valeur ==
                                getPileCartes(1, 1).get(getPileCartes(1, 1).size() - 1).valeur) { // it's a bataille, you can only add cards if there's an even no. there or no-one has won
                    jeuPiles.transfer(numPile1, numPile2, premiereCarte); // human move
                    jeuPiles.transfer(1, 3, getPileCartes(0, 1).size() - 1);             // computer move
                    return new int[]{0, 1, 2, 3};
                }
            } else if (typePile1 == 1 && typePile2 == 0 &&
                    getPileCartes(1, 1).size() % 2 != 0) { // case of taking cards from middle piles
                // nb atm we ignore the card which is clicked on and just take them all, not sure if this is good or not
                if ((getPileCartes(1, 0).get(getPileCartes(1, 0).size() - 1).valeur >
                        getPileCartes(1, 1).get(getPileCartes(1, 1).size() - 1).valeur && numPile2 == 0) ||
                        (getPileCartes(1, 0).get(getPileCartes(1, 0).size() - 1).valeur <
                                getPileCartes(1, 1).get(getPileCartes(1, 1).size() - 1).valeur && numPile2 == 1)) {
                    jeuPiles.transfer(numPile1, numPile2, 0, 0); // to bottom of pile(order not reversed though)
                    int oppPileNum = calcOppJoueur(numPile1);     // calc opposite pile
                    jeuPiles.transfer(2 + oppPileNum, numPile2, 0, 0);
                    return new int[]{2, 3, numPile2};
                }
            }
        }
        return new int[]{};
    }

    /**
     * retirer ne fait rien dans ce jeu
     * mieux serait d'avoir un error je pense
     * @param typePile1
     * @param numPile1
     * @param premiereCarte
     * @return
     */
    public boolean retirer(int typePile1, int numPile1, int premiereCarte) {
        return true;
    }

    /**
     * le jeu est gagne si qqu a gagne ...
     * @return
     */
    public boolean gagne() {
        return (gagne(0) || gagne(1));
    }

    /**
     * si le pile de son adversaire est vide, qqu a gagne
     * @param joueur
     * @return
     */
    public boolean gagne(int joueur) {
        if (jeuPiles.estVide(0 + joueur)) {
            System.out.println("Joueur " + calcOppJoueur(joueur) + " a gagne");
            gagne = true;
            return gagne;
        }
        gagne = false;
        return gagne;
    }

    /**
     * retourne le numero du joueur oppose
     * @param joueur
     * @return
     */
    public int calcOppJoueur(int joueur) { // only works for 0 or 1
        return (joueur + 1) % 2;
    }

    /*
        public ArrayList<ArrayList<PileCartes>> getDisplayGroups() {
            ArrayList<ArrayList<PileCartes>> outList = new ArrayList<ArrayList<PileCartes>>();
            ArrayList<PileCartes> tmp0 = new ArrayList<PileCartes>();
            tmp0.add( getJeuPiles().get(1));
            ArrayList<PileCartes> tmp1 = new ArrayList<PileCartes>();
            tmp1.add( getJeuPiles().get(3));
            ArrayList<PileCartes> tmp2 = new ArrayList<PileCartes>();
            tmp2.add( getJeuPiles().get(2));
            ArrayList<PileCartes> tmp3 = new ArrayList<PileCartes>();
            tmp3.add( getJeuPiles().get(0));
            outList.add( tmp0 );
            outList.add( tmp1 );
            outList.add( tmp2 );
            outList.add( tmp3 );
            return  outList;
            }
            */

    /**
     *  obtenir les rangs pour le display, alors un int[][] avec les numeros de piles et leurs
     *  positions (column,row)
     * @return
     */
    public int[][] getDisplayGroups() {
        int[][] groups = new int[][]{{1}, {3}, {2}, {0}};
        return groups;
    }

    /**
     * mettre la direction de display des cartes
     */
    public void setDisplayDirections() {
        for (PileCartes pile : jeuPiles) {
            pile.setPileDisplayDirection(PileDisplayDirection.HORIZONTAL);
        }
    }

    /**
     * determiner le maximum taille possible d'un pile
     */
    public void setMaxPileSize() {
        for (PileCartes pile : jeuPiles) {
            switch (pile.getRegleType()) {
                case 0:
                    pile.setMaxPileSize(52);
                    break;
                case 1:
                    pile.setMaxPileSize(26);
                    break;
            }
        }
    }
}
