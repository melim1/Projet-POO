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
        return dosesRestantes <= 0; 
    }

    public String toString() {
        return super.toString() + String.format(" [Doses restantes=%d]", dosesRestantes);
    }

}