package fr.uphf.sae201;

public class Entrepot extends Batiment {

    public Entrepot(int id, int stock, Minerais type, Secteur secteur) {
        super(id, stock, type, secteur);
    }

    /* Méthode qui renvoie True si le robot possède une quantité à déposer supérieur à 0 */

    public boolean action(int quantite) {
        if (quantite <= 0) {
            return false;
        } else {
            stock += quantite;
            return true;
        }
    }

    public String affichageGrille() {
        return (type == Minerais.OR ? ANSI_YELLOW_BACKGROUND : ANSI_WHITE_BACKGROUND) + "E" + (id < 10 ? "0" + id : id) + ANSI_RESET;
    }

    public String affichageInformations() {
        return "";
    }
}
