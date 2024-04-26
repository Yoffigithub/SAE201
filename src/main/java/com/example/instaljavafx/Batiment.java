package fr.uphf.sae201;

public abstract class Batiment implements Couleurs {

    protected int id;
    protected int stock;
    protected Minerais type;
    protected Secteur secteur;

    public Batiment(int id, int stock, Minerais type, Secteur secteur) {
        this.id = id;
        this.stock = stock;
        this.type = type;
        this.secteur = secteur;
    }

    /**
     * Méthode qui permet d'obtenir le stock actuel du bâtiment (mine ou entrepôt).
     *
     * @return stock Renvoie le nombre de minerais que possède le bâtiment.
     */
    public int obtenirStock() {
        return stock;
    }

    /**
     * Méthode qui permet d'obtenir le type du minerai (or ou nickel).
     *
     * @return type Renvoie le type de minerai associé au bâtiment.
     */
    public Minerais obtenirType() {
        return type;
    }

    /**
     * Méthode qui permet d'obtenir le secteur actuel du bâtiment.
     *
     * @return secteur Renvoie le secteur actuel du bâtiment provenant de la classe Secteur.
     */
    public Secteur obtenirSecteur() {
        return secteur;
    }

    /**
     * Méthode abstraite permettant d'effectuer une action (ajouter ou enlever des minerais) sur les bâtiments.
     *
     * @param quantite Correspond à la quantité de minerai.
     */
    public abstract boolean action(int quantite);

    /**
     * Méthode abstraite permettant d'afficher la grille.
     */
    public abstract String affichageGrille();

    /**
     * Méthode abstraite permettant d'afficher les informations relatives au bâtiment.
     */
    public abstract String affichageInformations();


}


