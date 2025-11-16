/*  CapteurConnecte est la classe mère de tous les capteurs.
Elle centralise les attributs communs : identifiant, nom, abonnement et valeur mesurée.
C’est une classe abstraite, ce qui oblige chaque capteur spécifique à redéfinir sa manière de mesurer et de détecter une alerte.
J’ai aussi ajouté la sérialisation pour pouvoir sauvegarder l’état des capteurs dans un fichier.
 */


import java.io.Serializable;

public abstract class CapteurConnecte implements Serializable {

    private static final long serialVersionUID = 1L;

    String id;
    String nom;
    Abonnement abonnement;
    double valeur;

    public CapteurConnecte(String id, String nom){
        this.id = id;
        this.nom = nom;
        this.abonnement = null;
    }

    public void setAbonnement(Abonnement a) {
        this.abonnement = a;   //Permet d’associer un abonnement à un capteur.
    }

    public boolean estAbonne() {
        return abonnement != null && abonnement.estValide();
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public double getValeur() { return valeur; }

    public abstract void mesurer();                 // Méthodes abstraites
    public abstract boolean verifierAlerte();

    public String toString() {
        return id + "   " + nom + " (abonné=" + estAbonne() + ")";
    }
}
