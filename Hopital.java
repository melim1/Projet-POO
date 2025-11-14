import java.util.ArrayList;
import java.util.List;

public class Hopital {
    private List<CapteurConnecte> capteurs;

    public Hopital() {
        capteurs = new ArrayList<>();
    }

    // Ajouter un capteur
    public void ajouterCapteur(CapteurConnecte c) {
        capteurs.add(c);
        System.out.println("Capteur ajouté : " + c.getNom());
    }

    // Supprimer un capteur
    public void supprimerCapteur(CapteurConnecte c) {
        capteurs.remove(c);
        System.out.println("Capteur supprimé : " + c.getNom());
    }

    // Mesurer tous les capteurs
    public void mesurerTousLesCapteurs() {
        for (CapteurConnecte c : capteurs) {
            c.mesurer();
        }
    }

    // Vérifier les alertes et afficher celles qui sont actives
    public void afficherAlertes() {
        boolean alerteActive = false;
        for (CapteurConnecte c : capteurs) {
            if (c.verifierAlerte()) {
                System.out.println("ALERTE ! Capteur : " + c);
                alerteActive = true;
            }
        }
        if (!alerteActive) {
            System.out.println("Aucune alerte pour le moment.");
        }
    }

    // Afficher tous les capteurs
    public void afficherCapteurs() {
        if (capteurs.isEmpty()) {
            System.out.println("Aucun capteur dans l'hôpital.");
        } else {
            for (CapteurConnecte c : capteurs) {
                System.out.println(c);
            }
        }
    }
}
