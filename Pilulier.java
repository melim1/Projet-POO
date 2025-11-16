/*  Le pilulier garde en mémoire les doses restantes.
À chaque mesure, il y a 30% de chance qu’une dose soit consommée.
Une alerte est déclenchée lorsqu’il n’y a plus de doses, ce qui permet de notifier l’utilisateur.  */

public class Pilulier extends CapteurConnecte{
private int dosesRestantes;
  
    public Pilulier(String id, String nom, int dosesInitiales) {
        super(id, nom);
        this.dosesRestantes = dosesInitiales;
    }

    public void mesurer() {
        if (Math.random() < 0.3 && dosesRestantes > 0) {
            dosesRestantes -= 1;
        }
        valeur = dosesRestantes;
    }

    public boolean verifierAlerte() {
        return dosesRestantes <= 0;  //ya plus de doses 
    }

    public String toString() {
        return super.toString() + String.format(" [Doses restantes=%d]", dosesRestantes);
    }

}