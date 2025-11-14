import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    // Menu principal
    private static void afficherMenu() {
        System.out.println("\n==============================================");
        System.out.println("           MENU - HÔPITAL INTELLIGENT         ");
        System.out.println("==============================================");
        System.out.println(" 1. Ajouter un capteur");
        System.out.println(" 2. Mesurer tous les capteurs");
        System.out.println(" 3. Afficher toutes les alertes");
        System.out.println(" 4. Afficher tous les capteurs");
        System.out.println(" 5. Supprimer un capteur");
        System.out.println(" 0. Quitter");
        System.out.println("==============================================");
        System.out.print("Votre choix : ");
    }

    // Sous-menu pour l'ajout d'un capteur
    private static int afficherMenuAjouterCapteur(Scanner sc) {
        System.out.println("\n================ AJOUT D'UN CAPTEUR ================");
        System.out.println("Veuillez choisir le type de capteur à ajouter :");
        System.out.println(" 1 - Balance");
        System.out.println(" 2 - Oxymètre");
        System.out.println(" 3 - Pilulier");
        System.out.println(" 4 - Tensiomètre");
        System.out.println("====================================================");
        System.out.print("Votre choix : ");
        return sc.nextInt();
    }

    public static void main(String[] args) {
        Hopital hopital = new Hopital();
        Scanner sc = new Scanner(System.in);

        // Charger les capteurs depuis le fichier au démarrage
        hopital.charger("capteurs.dat");
        int choix;

        do {
            afficherMenu();

            // Vérification de saisie
            while (!sc.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre valide !");
                sc.next();
                afficherMenu();
            }
            choix = sc.nextInt();

            switch (choix) {
                case 1: // Ajouter un capteur
                    int type = afficherMenuAjouterCapteur(sc);
                    sc.nextLine(); // consommer le retour à la ligne
                    System.out.print("Nom du capteur : ");
                    String nom = sc.nextLine();
                    String id = "C" + (int)(Math.random() * 1000);

                    CapteurConnecte capteur = null;

                    switch(type) {
                        case 1: capteur = new Balance(id, nom); break;
                        case 2: capteur = new Oxymetre(id, nom); break;
                        case 3: capteur = new Pilulier(id, nom, 10); break;
                        case 4: capteur = new Tensiometre(id, nom); break;
                        default: System.out.println("Type invalide."); break;
                    }

                    if (capteur != null) {
                        // Choix du type d'abonnement
                        System.out.println("Type d'abonnement : 1-Mensuel, 2-Annuel");
                        int typeAbonnement = sc.nextInt();
                        sc.nextLine(); // consommer le retour à la ligne
                        String typeAbo = (typeAbonnement == 1) ? "Mensuel" : "Annuel";

                        // Saisie de la date de début
                        System.out.print("Date de début de l'abonnement (dd-MM-yyyy) : ");
                        String dateDebutStr = sc.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate dateDebut = LocalDate.parse(dateDebutStr, formatter);
                        LocalDate dateFin = (typeAbo.equals("Mensuel")) ? dateDebut.plusMonths(1) : dateDebut.plusYears(1);

                        // Création de l'abonnement
                        Abonnement abonnement = new Abonnement(typeAbo, dateDebut.format(formatter), dateFin.format(formatter));
                        capteur.setAbonnement(abonnement);

                        hopital.ajouterCapteur(capteur);
                        hopital.sauvegarder("capteurs.dat");
                        System.out.println("Capteur ajouté avec succès ! Abonnement valide jusqu'au : " + abonnement.getDateFin());
                    }
                    break;

                case 2: // Mesurer tous les capteurs
                    hopital.mesurerTousLesCapteurs();
                    hopital.sauvegarder("capteurs.dat");
                    hopital.exporterCSV("capteurs.csv");
                    System.out.println("Mesures effectuées et CSV mis à jour !");
                    break;

                case 3: // Afficher les alertes
                    System.out.println("\n================ ALERTES ACTIVES ================");
                    hopital.afficherAlertes();
                    System.out.println("================================================");
                    break;

                case 4: // Afficher tous les capteurs
                    System.out.println("\n================ LISTE DES CAPTEURS ================");
                    hopital.afficherCapteurs();
                    System.out.println("===================================================");
                    break;

                case 5: // Supprimer un capteur
                    sc.nextLine(); // consommer le retour à la ligne
                    System.out.print("Entrez l'ID du capteur à supprimer : ");
                    String idSuppr = sc.nextLine();

                    CapteurConnecte capteurASuppr = null;
                    for (CapteurConnecte c : hopital.getCapteurs()) { // Assure-toi que getCapteurs() existe dans Hopital
                        if (c.getId().equals(idSuppr)) {
                            capteurASuppr = c;
                            break;
                        }
                    }

                    if (capteurASuppr != null) {
                        hopital.supprimerCapteur(capteurASuppr);
                        hopital.sauvegarder("capteurs.dat");
                        System.out.println("Capteur supprimé avec succès !");
                    } else {
                        System.out.println("Capteur introuvable !");
                    }
                    break;

                case 0: // Quitter
                    hopital.sauvegarder("capteurs.dat");
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 0);

        sc.close();
    }
}
