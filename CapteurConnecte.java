import java.io.Serializable;

public abstract class CapteurConnecte implements Serializable {

    private static final long serialVersionUID = 1L;

    String id;
    String nom;
    Abonnement abonnement;
    double valeur;

    public CapteurConnecte(String id, String nom) {
        this.id = id;
        this.nom = nom;
        this.abonnement = null;
    }

    public void setAbonnement(Abonnement a) {
        this.abonnement = a;
    }

    public boolean estAbonne() {
        return abonnement != null && abonnement.estValide();
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getValeur() {
        return valeur;
    }

    public abstract void mesurer();

    public abstract boolean verifierAlerte();

    public String toString() {
        return id + "   " + nom + " (abonné=" + estAbonne() + ")";
    }
}
