package modele;/* L’oxymètre hérite de modele.CapteurConnecte. Il simule une saturation en oxygène entre 88 et 100%.
Une alerte est déclenchée sous 92%, ce qui correspond à une désaturation. */

public class Oxymetre extends CapteurConnecte {
    private double spo2; // saturation en oxygène

    public Oxymetre(String id, String nom) {    
    super(id, nom);
    }

    public void mesurer(){
        spo2= 88 + Math.random()*12;
        valeur=spo2;
    }

    public boolean verifierAlerte() {
        return spo2 < 92;  // desaturation d'oxygene
    }
    public String toString() {
    return super.toString() + String.format(" [SpO2=%.1f%%]", spo2);
    }

}