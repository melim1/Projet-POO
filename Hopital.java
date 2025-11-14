import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;






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

    System.out.println("\n==============================================");
    System.out.println("            ⚠️  ALERTES CAPTEURS  ⚠️           ");
    System.out.println("==============================================");

    boolean alerteActive = false;

    // En-tête du tableau
    System.out.printf("%-10s | %-15s | %-10s | %-30s%n",
            "ID", "Nom", "Valeur", "Description");
    System.out.println("--------------------------------------------------------------------------");

    for (CapteurConnecte c : capteurs) {
        if (c.verifierAlerte()) {
            alerteActive = true;

            System.out.printf("%-10s | %-15s | %-10s | %-30s%n",
                    c.getId(),
                    c.getNom(),
                    c.getValeur(),
                    c.toString());
        }
    }

    if (!alerteActive) {
        System.out.println("Aucune alerte pour le moment.");
    }

    System.out.println("==============================================\n");
}


    // Afficher tous les capteurs
    public void afficherCapteurs() {
    if (capteurs.isEmpty()) {
        System.out.println("Aucun capteur dans l'hôpital.");
        return;
    }

    System.out.println("\n================ LISTE DES CAPTEURS ================");

    // En-têtes du tableau
    System.out.printf("%-6s %-15s %-10s %-15s %-30s\n",
            "ID", "Nom", "Abonné", "Valeur", "Informations");

    System.out.println("---------------------------------------------------------------" +
                       "------------------------");

    // Affichage de chaque capteur
    for (CapteurConnecte c : capteurs) {

        String info = c.toString()
                       .replace(c.getId() + "   ", "")
                       .replace(c.getNom(), "")
                       .replace("(abonné=" + c.estAbonne() + ")", "")
                       .trim();

        System.out.printf("%-6s %-15s %-10s %-15s %-30s\n",
                c.getId(),
                c.getNom(),
                c.estAbonne() ? "Oui" : "Non",
                String.format("%.1f", c.getValeur()),
                info
        );
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



public void exporterCSV(String nomFichier) {
    try (FileWriter writer = new FileWriter(nomFichier)) {

        // En-têtes CSV
        writer.write("ID;Nom;Abonne;Valeur;Informations\n");

        // Parcourir tous les capteurs
        for (CapteurConnecte c : capteurs) {
            writer.write(
                c.getId() + ";" +
                c.getNom() + ";" +
                c.estAbonne() + ";" +
                c.getValeur() + ";" +
                c.toString() +
                "\n"
            );
        }

        writer.flush();
        System.out.println("Fichier CSV exporté avec succès : " + nomFichier);

    } catch (IOException e) {
        System.out.println("Erreur durant l'export CSV : " + e.getMessage());
    }
    
}
public List<CapteurConnecte> getCapteurs() {
    return capteurs;
}




}
