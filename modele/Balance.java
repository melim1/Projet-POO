package modele;

public class Balance extends CapteurConnecte {
    private double poids;


    public Balance(String id, String nom) {
    super(id, nom);
    }
    public void mesurer() {
        poids = 40 + Math.random() * 60; 
        valeur = poids;
    }
    
    public boolean verifierAlerte() {
        return (poids < 45 || poids > 120);
    }

    public String toString() {
        return super.toString() + String.format(" [Poids=%.1f kg]", poids);
    }
}
