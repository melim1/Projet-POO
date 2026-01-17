package modele; /* Le tensiomètre génère deux valeurs : systolique et diastolique.
Une alerte apparaît si la tension dépasse 140/90, ce qui correspond à une hypertension. */


public class Tensiometre extends CapteurConnecte {

        private double tensionSystolique;
        private double tensionDiastolique;

        public Tensiometre(String id, String nom) {
            super(id, nom);
        }
        public void mesurer(){
            tensionSystolique=90+Math.random()*70;
            tensionDiastolique=50+Math.random()*50;
            valeur=tensionSystolique;
        }
        public boolean verifierAlerte(){
            return(tensionSystolique > 140 || tensionDiastolique > 90);

        }
        public String toString(){
            return super.toString()+String.format("[Tension=%.1f/%.1f]", tensionSystolique, tensionDiastolique);
        }


}