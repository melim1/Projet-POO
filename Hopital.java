import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Hopital implements Serializable{
    private static final long serialVersionUID = 1L;
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

     // Sauvegarder les capteurs dans un fichier
    public void sauvegarder(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(capteurs);
            System.out.println("Sauvegarde effectuée !");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }
     // Charger les capteurs depuis un fichier
    public void charger(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            capteurs = (List<CapteurConnecte>) ois.readObject();
            System.out.println("Chargement effectué !");
        } catch (FileNotFoundException e) {
            System.out.println("Aucun fichier de sauvegarde trouvé, démarrage avec liste vide.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
        }
    }



}
