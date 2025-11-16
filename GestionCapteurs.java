import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionCapteurs {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // MODIFIÉ - Ajouter GestionAlarmes en paramètre
    public static void afficherMenuGestionCapteurs(Scanner sc, Hopital hopital, GestionAlarmes gestionAlarmes) {
        System.out.println("\n========== GESTION DES CAPTEURS ==========");
        System.out.println(" 1. Ajouter un capteur");
        System.out.println(" 2. Supprimer un capteur");
        System.out.println(" 3. Mesurer tous les capteurs");
        System.out.println(" 4. Rechercher un capteur");
        System.out.println(" 5. Filtrer les capteurs par type");
        System.out.println(" 6. Afficher les capteurs non abonnés");
        System.out.println(" 7. Modifier un capteur");
        System.out.println(" 8. Statistiques des capteurs");
        System.out.println(" 0. Retour au menu principal");
        System.out.println("==========================================");
        System.out.print("Votre choix : ");

        int choix = sc.nextInt();
        sc.nextLine();

        if (choix == 1) {
            ajouterCapteur(sc, hopital);
        } else if (choix == 2) {
            supprimerCapteur(sc, hopital);
        } else if (choix == 3) {
            mesurerTousLesCapteurs(hopital, gestionAlarmes);  // MODIFIÉ - avec gestionAlarmes
        } else if (choix == 4) {
            rechercherCapteur(sc, hopital);
        } else if (choix == 5) {
            filtrerParType(sc, hopital);
        } else if (choix == 6) {
            afficherCapteursNonAbonnes(hopital);
        } else if (choix == 7) {
            modifierCapteur(sc, hopital);
        } else if (choix == 8) {
            afficherStatistiques(hopital);
        } else if (choix == 0) {
            return;
        } else {
            System.out.println("Choix invalide !");
        }
    }

    // ===== AJOUTER UN CAPTEUR =====
    private static void ajouterCapteur(Scanner sc, Hopital hopital) {
        System.out.println("\n================ AJOUT D'UN CAPTEUR ================");
        System.out.println("Veuillez choisir le type de capteur à ajouter :");
        System.out.println(" 1 - Balance");
        System.out.println(" 2 - Oxymètre");
        System.out.println(" 3 - Pilulier");
        System.out.println(" 4 - Tensiomètre");
        System.out.println(" 5 - Glucomètre");
        System.out.println(" 6 - Holter ECG");
        System.out.println("====================================================");
        System.out.print("Votre choix : ");

        int type = sc.nextInt();
        sc.nextLine();

        System.out.print("Nom du capteur : ");
        String nom = sc.nextLine();
        String id = "C" + (int)(Math.random() * 1000);

        CapteurConnecte capteur = null;

        if (type == 1) {
            capteur = new Balance(id, nom);
        } else if (type == 2) {
            capteur = new Oxymetre(id, nom);
        } else if (type == 3) {
            capteur = new Pilulier(id, nom, 10);
        } else if (type == 4) {
            capteur = new Tensiometre(id, nom);
        } else if (type == 5) {
            capteur = new Glucometre(id, nom);
        } else if (type == 6) {
            capteur = new Holter_ECG(id, nom);
        } else {
            System.out.println("Type invalide.");
            return;
        }

        System.out.println("\nVoulez-vous ajouter un abonnement ? (1-Oui, 2-Non)");
        int avecAbo = sc.nextInt();
        sc.nextLine();

        if (avecAbo == 1) {
            System.out.println("Type d'abonnement : 1-Mensuel, 2-Annuel");
            int typeAbonnement = sc.nextInt();
            sc.nextLine();
            String typeAbo = (typeAbonnement == 1) ? "Mensuel" : "Annuel";

            System.out.print("Date de début de l'abonnement (dd-MM-yyyy) : ");
            String dateDebutStr = sc.nextLine();
            LocalDate dateDebut = LocalDate.parse(dateDebutStr, FORMAT);
            LocalDate dateFin = (typeAbo.equals("Mensuel")) ? dateDebut.plusMonths(1) : dateDebut.plusYears(1);

            Abonnement abonnement = new Abonnement(typeAbo, dateDebut.format(FORMAT), dateFin.format(FORMAT));
            capteur.setAbonnement(abonnement);

            System.out.println("✓ Capteur ajouté avec abonnement valide jusqu'au : " + abonnement.getDateFin());
        } else {
            System.out.println("✓ Capteur ajouté sans abonnement.");
        }

        hopital.ajouterCapteur(capteur);
        hopital.sauvegarder("capteurs.dat");
    }

    // ===== SUPPRIMER UN CAPTEUR =====
    private static void supprimerCapteur(Scanner sc, Hopital hopital) {
        System.out.print("Entrez l'ID du capteur à supprimer : ");
        String idSuppr = sc.nextLine();

        CapteurConnecte capteurASuppr = null;
        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getId().equals(idSuppr)) {
                capteurASuppr = c;
                break;
            }
        }

        if (capteurASuppr != null) {
            hopital.supprimerCapteur(capteurASuppr);
            hopital.sauvegarder("capteurs.dat");
            System.out.println("✓ Capteur supprimé avec succès !");
        } else {
            System.out.println("✗ Capteur introuvable !");
        }
    }

    // ===== MESURER TOUS LES CAPTEURS ===== (UNE SEULE VERSION)
    private static void mesurerTousLesCapteurs(Hopital hopital, GestionAlarmes gestionAlarmes) {
        if (hopital.getCapteurs().isEmpty()) {
            System.out.println("Aucun capteur à mesurer.");
            return;
        }

        System.out.println("\n========== MESURE DE TOUS LES CAPTEURS ==========");
        hopital.mesurerTousLesCapteurs();

        // CRÉER LES ALARMES AUTOMATIQUEMENT
        gestionAlarmes.verifierEtCreerAlarmes(hopital);

        hopital.sauvegarder("capteurs.dat");
        hopital.exporterCSV("capteurs.csv");

        // Afficher un résumé
        int total = hopital.getCapteurs().size();
        int alertes = 0;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.verifierAlerte()) {
                alertes++;
            }
        }

        System.out.println("✓ Tous les capteurs ont été mesurés !");
        System.out.println("Total de capteurs : " + total);
        System.out.println("Capteurs en alerte : " + alertes);
        System.out.println("CSV exporté dans capteurs.csv");
        System.out.println("=================================================\n");
    }

    // ===== RECHERCHER UN CAPTEUR =====
    private static void rechercherCapteur(Scanner sc, Hopital hopital) {
        System.out.print("Entrez l'ID ou le nom du capteur : ");
        String recherche = sc.nextLine();

        System.out.println("\n====== RÉSULTATS DE LA RECHERCHE ======");
        boolean trouve = false;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getId().contains(recherche) || c.getNom().contains(recherche)) {
                System.out.println(c);
                trouve = true;
            }
        }

        if (!trouve) {
            System.out.println("Aucun capteur trouvé.");
        }
        System.out.println("========================================\n");
    }

    // ===== FILTRER PAR TYPE =====
    private static void filtrerParType(Scanner sc, Hopital hopital) {
        System.out.println("\nChoisissez le type :");
        System.out.println("1 - Balance");
        System.out.println("2 - Oxymètre");
        System.out.println("3 - Pilulier");
        System.out.println("4 - Tensiomètre");
        System.out.println("5 - Glucomètre");
        System.out.println("6 - Holter ECG");
        System.out.print("Votre choix : ");

        int type = sc.nextInt();
        sc.nextLine();

        String nomClasse = "";
        if (type == 1) {
            nomClasse = "Balance";
        } else if (type == 2) {
            nomClasse = "Oxymetre";
        } else if (type == 3) {
            nomClasse = "Pilulier";
        } else if (type == 4) {
            nomClasse = "Tensiometre";
        } else if (type == 5) {
            nomClasse = "Glucometre";
        } else if (type == 6) {
            nomClasse = "Holter_ECG";
        }

        System.out.println("\n====== CAPTEURS DE TYPE " + nomClasse + " ======");
        boolean trouve = false;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getClass().getSimpleName().equals(nomClasse)) {
                System.out.println(c);
                trouve = true;
            }
        }

        if (!trouve) {
            System.out.println("Aucun capteur de ce type.");
        }
        System.out.println("==========================================\n");
    }

    // ===== AFFICHER CAPTEURS NON ABONNÉS =====
    private static void afficherCapteursNonAbonnes(Hopital hopital) {
        System.out.println("\n====== CAPTEURS NON ABONNÉS ======");
        boolean trouve = false;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (!c.estAbonne()) {
                System.out.println(c);
                trouve = true;
            }
        }

        if (!trouve) {
            System.out.println("Tous les capteurs sont abonnés.");
        }
        System.out.println("==================================\n");
    }

    // ===== MODIFIER UN CAPTEUR =====
    private static void modifierCapteur(Scanner sc, Hopital hopital) {
        System.out.print("Entrez l'ID du capteur à modifier : ");
        String id = sc.nextLine();

        CapteurConnecte capteur = null;
        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getId().equals(id)) {
                capteur = c;
                break;
            }
        }

        if (capteur == null) {
            System.out.println("Capteur introuvable !");
            return;
        }

        System.out.print("Nouveau nom (actuel: " + capteur.getNom() + ") : ");
        String nouveauNom = sc.nextLine();

        if (!nouveauNom.isEmpty()) {
            capteur.nom = nouveauNom;
            hopital.sauvegarder("capteurs.dat");
            System.out.println("✓ Capteur modifié avec succès !");
        }
    }

    // ===== STATISTIQUES =====
    private static void afficherStatistiques(Hopital hopital) {
        int total = hopital.getCapteurs().size();
        int abonnes = 0;
        int enAlerte = 0;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.estAbonne()) {
                abonnes++;
            }
            if (c.verifierAlerte()) {
                enAlerte++;
            }
        }

        System.out.println("\n========== STATISTIQUES ==========");
        System.out.println("Total de capteurs : " + total);
        System.out.println("Capteurs abonnés : " + abonnes);
        System.out.println("Capteurs non abonnés : " + (total - abonnes));
        System.out.println("Capteurs en alerte : " + enAlerte);
        System.out.println("==================================\n");
    }
}
