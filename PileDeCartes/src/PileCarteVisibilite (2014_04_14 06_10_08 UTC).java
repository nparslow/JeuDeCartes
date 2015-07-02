/**
 * determine quels faces des cartes seront visibles dans une pile:
 * SHOWALL = montre les faces de toute carte
 * SHOWTOPONLY = ne montre que la face de la carte la plus haute
 * SHOWNONE = ne montre aucune face
 * SHOWODD = montre un carte sur deux dans la pile, avec la premiere carte montree
 * Created by Nicholas Parslow on 28/12/13.
 */
public enum PileCarteVisibilite {
    SHOWALL,
    SHOWTOPONLY,
    SHOWNONE,
    SHOWODD //,
    //SHOWNONEMIN,
    //SHOWTOPONLYMIN
}
