package modele;/* Le glucomètre hérite de modele.CapteurConnecte et simule une valeur de glycémie entre 0.6 et 2.2.
Il génère une alerte si la glycémie est en dessous de 0.70 ou au-dessus de 1.80.
*/

public class Glucometre extends CapteurConnecte {
     private double glycemie; // saturation en oxygène

    public Glucometre(String id, String nom) {    
    super(id, nom);
    }

    public void mesurer(){
        glycemie= 0.6 + Math.random() * 1.6;
        valeur=glycemie;
    }

    public boolean verifierAlerte() {
                                          // Hypoglycémie : < 0.70 g/L
                                          // Hyperglycémie : > 1.80 g/L

         return glycemie < 0.70 || glycemie > 1.80;
    }
    
     public double getGlycemie() {
        return glycemie;
    }

   public String toString() {
        return "Glucomètre [" + id + "] - " + nom +
                " | Glycémie : " + String.format("%.2f", glycemie) + " g/L" ;
              
    }
}



