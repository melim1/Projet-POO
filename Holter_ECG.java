public class Holter_ECG extends CapteurConnecte{
     private int frequenceCardiaque; // en battements par minute (bpm)

    public Holter_ECG(String id, String nom) {
        super(id, nom);
    }

    @Override
    public void mesurer() {
        // Simuler une mesure réaliste : entre 40 et 150 bpm
        frequenceCardiaque = 40 + (int)(Math.random() * 110);
        valeur = frequenceCardiaque;
    }

    @Override
    public boolean verifierAlerte() {
        // Bradycardie : < 50 bpm
        // Tachycardie : > 120 bpm
        return frequenceCardiaque < 50 || frequenceCardiaque > 120;
    }

    public int getFrequenceCardiaque() {
        return frequenceCardiaque;
    }

    @Override
    public String toString() {
        return "Holter ECG [" + id + "] - " + nom +
                " | Fréquence cardiaque : " + frequenceCardiaque + " bpm" ;
                
    }
}
