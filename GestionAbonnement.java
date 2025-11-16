import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestionAbonnement {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Menu simplifié
    public static void afficherMenuAbonnements(Scanner sc, Hopital hopital) {
        System.out.println("\n========== GESTION DES ABONNEMENTS ==========");
        System.out.println(" 1. Afficher tous les abonnements");
        System.out.println(" 2. Renouveler un abonnement");
        System.out.println(" 3. Désactiver un abonnement");
        System.out.println(" 0. Retour au menu principal");
        System.out.println("=============================================");
        System.out.print("Votre choix : ");

        int choix = sc.nextInt();
        sc.nextLine();

        if (choix == 1) {
            afficherAbonnements(hopital);
        } else if (choix == 2) {
            renouvelerAbonnement(sc, hopital);
        } else if (choix == 3) {
            desactiverAbonnement(sc, hopital);
        } else if (choix == 0) {
            return;
        } else {
            System.out.println("Choix invalide !");
        }
    }

    // Afficher tous les abonnements
    private static void afficherAbonnements(Hopital hopital) {
        System.out.println("\n========== LISTE DES ABONNEMENTS ==========");

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.abonnement != null) {
                System.out.println("ID: " + c.getId());
                System.out.println("Nom: " + c.getNom());
                System.out.println("Type: " + c.abonnement.getType());
                System.out.println("Début: " + c.abonnement.getDateDebut());
                System.out.println("Fin: " + c.abonnement.getDateFin());
                System.out.println("Actif: " + (c.abonnement.estValide() ? "Oui" : "Non"));
                System.out.println("-------------------------------------------");
            }
        }
    }

    // Renouveler un abonnement
    private static void renouvelerAbonnement(Scanner sc, Hopital hopital) {
        System.out.print("Entrez l'ID du capteur : ");
        String id = sc.nextLine();

        // Chercher le capteur
        CapteurConnecte capteur = null;
        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getId().equals(id)) {
                capteur = c;
                break;
            }
        }

        // Vérifier si on a trouvé le capteur
        if (capteur == null) {
            System.out.println("Capteur introuvable !");
            return;
        }

        // Vérifier s'il a un abonnement
        if (capteur.abonnement == null) {
            System.out.println("Ce capteur n'a pas d'abonnement !");
            return;
        }

        // Demander le nouveau type
        System.out.println("Type d'abonnement : 1-Mensuel, 2-Annuel");
        int type = sc.nextInt();
        sc.nextLine();

        // Créer les dates
        LocalDate dateDebut = LocalDate.now();
        LocalDate dateFin;
        String typeAbo;

        if (type == 1) {
            typeAbo = "Mensuel";
            dateFin = dateDebut.plusMonths(1);
        } else {
            typeAbo = "Annuel";
            dateFin = dateDebut.plusYears(1);
        }

        // Créer le nouvel abonnement
        Abonnement nouveauAbo = new Abonnement(typeAbo,
                dateDebut.format(FORMAT),
                dateFin.format(FORMAT));

        capteur.setAbonnement(nouveauAbo);
        hopital.sauvegarder("capteurs.dat");

        System.out.println("✓ Abonnement renouvelé jusqu'au : " + nouveauAbo.getDateFin());
    }

    // Désactiver un abonnement
    private static void desactiverAbonnement(Scanner sc, Hopital hopital) {
        System.out.print("Entrez l'ID du capteur : ");
        String id = sc.nextLine();

        // Chercher le capteur
        CapteurConnecte capteur = null;
        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getId().equals(id)) {
                capteur = c;
                break;
            }
        }

        // Vérifier si on a trouvé le capteur et son abonnement
        if (capteur == null || capteur.abonnement == null) {
            System.out.println("Capteur ou abonnement introuvable !");
            return;
        }

        // Désactiver
        capteur.abonnement.desactiver();
        hopital.sauvegarder("capteurs.dat");
        System.out.println("✓ Abonnement désactivé !");
    }
}
