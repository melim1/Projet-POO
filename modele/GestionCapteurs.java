package modele;

import ui.GestionAlarmesui;

import javax.swing.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionCapteurs {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // MODIFIÉ - Ajouter modele.GestionAlarmes en paramètre
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
            mesurerTousLesCapteurs(hopital, gestionAlarmes); // MODIFIÉ - avec gestionAlarmes
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
        System.out.println(" 1 - modele.Balance");
        System.out.println(" 2 - Oxymètre");
        System.out.println(" 3 - modele.Pilulier");
        System.out.println(" 4 - Tensiomètre");
        System.out.println(" 5 - Glucomètre");
        System.out.println(" 6 - Holter ECG");
        System.out.println("====================================================");
        System.out.print("Votre choix : ");

        int type = sc.nextInt();
        sc.nextLine();

        System.out.print("Nom du capteur : ");
        String nom = sc.nextLine();
        String id = "C" + (int) (Math.random() * 1000);

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

            LocalDate dateDebut = null;

            while (dateDebut == null) {

                String dateDebutStr = sc.nextLine();

                try {
                    dateDebut = LocalDate.parse(dateDebutStr, FORMAT);
                    if (dateDebut.isBefore(LocalDate.now())) {
                        System.out.println("⚠️ La date ne peut PAS être dans le passé !");
                        dateDebut = null;
                        continue;
                    }
                    // Vérifier si date dans le futur
                    if (dateDebut.isAfter(LocalDate.now())) {
                        System.out.println("⚠️ La date ne peut pas être dans le futur !");
                        dateDebut = null;
                    }

                } catch (Exception e) {
                    System.out.println("⚠️ Date invalide ! Utilise le format dd-MM-yyyy.");
                }
            }

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
    // supprimer pour SWING
    public static void supprimerCapteurSwing(Hopital hopital) {

        String id = JOptionPane.showInputDialog(null, "ID du capteur à supprimer :");
        if (id == null || id.isEmpty()) return;

        CapteurConnecte capteurASupprimer = null;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getId().equals(id)) {
                capteurASupprimer = c;
                break;
            }
        }

        if (capteurASupprimer == null) {
            JOptionPane.showMessageDialog(null, "Capteur introuvable !");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Supprimer le capteur : " + capteurASupprimer.getNom() + " ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            hopital.supprimerCapteur(capteurASupprimer);
            hopital.sauvegarder("capteurs.dat");
            JOptionPane.showMessageDialog(null, "Capteur supprimé avec succès !");
        }
    }
    // ===== AFFICHER LES ALERTES EN TEMPS RÉEL (SWING) =====
    public static void afficherAlertesSwing(Hopital hopital) {

        StringBuilder alertes = new StringBuilder();
        boolean existeAlerte = false;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.verifierAlerte()) {
                existeAlerte = true;
                alertes.append("⚠️ ")
                        .append(c.getNom())
                        .append(" (ID: ")
                        .append(c.getId())
                        .append(")\n");
            }
        }

        if (existeAlerte) {
            JOptionPane.showMessageDialog(
                    null,
                    alertes.toString(),
                    "ALERTES EN COURS",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "✓ Aucune alerte détectée",
                    "Alertes",
                    JOptionPane.INFORMATION_MESSAGE
            );
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

    //mesurer pour SWING
    public static void mesurerTousLesCapteursSwing(Hopital hopital, GestionAlarmes gestionAlarmes) {

        if (hopital.getCapteurs().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun capteur à mesurer.");
            return;
        }

        // 1️⃣ Mesure
        hopital.mesurerTousLesCapteurs();

        // 2️⃣ Création des alarmes
        gestionAlarmes.verifierEtCreerAlarmes(hopital);
        gestionAlarmes.sauvegarder();



        // 3️⃣ Sauvegardes
        hopital.sauvegarder("capteurs.dat");
        hopital.exporterCSV("capteurs.csv");

        // 4️⃣ Construire le message complet pour la popup
        StringBuilder message = new StringBuilder();
        int alertes = 0;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            message.append("Capteur ").append(c.getNom())
                    .append(" (ID: ").append(c.getId()).append(")\n")
                    .append("Valeur : ").append(String.format("%.2f", c.getValeur())).append("\n");

            if (c.verifierAlerte()) {
                message.append("⚠️ ALERTE : ").append(determinerMessageAlerte(c)).append("\n");
                alertes++;
            } else {
                message.append("✔ État normal\n");
            }

            message.append("------------------------\n");
        }

        // 5️⃣ Ajouter résumé général
        message.append("\nTotal capteurs : ").append(hopital.getCapteurs().size())
                .append("\nCapteurs en alerte : ").append(alertes)
                .append("\nCSV exporté : capteurs.csv");

        // 6️⃣ Affichage popup
        JOptionPane.showMessageDialog(
                null,
                message.toString(),
                "Mesure des capteurs",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    public static String determinerMessageAlerte(CapteurConnecte c) {
        if (c instanceof Balance) {
            return c.getValeur() < 45 ? "Poids trop faible" : "Poids trop élevé";
        } else if (c instanceof Oxymetre) {
            return "Saturation oxygène faible";
        } else if (c instanceof Pilulier) {
            return "Doses épuisées";
        } else if (c instanceof Tensiometre) {
            return "Tension anormale";
        } else if (c instanceof Glucometre) {
            return c.getValeur() < 0.70 ? "Hypoglycémie" : "Hyperglycémie";
        } else if (c instanceof Holter_ECG) {
            return c.getValeur() < 50 ? "Bradycardie" : "Tachycardie";
        }
        return "Alerte générique";
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
    //Rechrche pour SWING
    public static void rechercherCapteurSwing(Hopital hopital) {

        String recherche = JOptionPane.showInputDialog(
                null,
                "Entrez l'ID ou le nom du capteur :"
        );

        if (recherche == null || recherche.isEmpty()) return;

        StringBuilder resultat = new StringBuilder();
        boolean trouve = false;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getId().toLowerCase().contains(recherche.toLowerCase())
                    || c.getNom().toLowerCase().contains(recherche.toLowerCase())) {

                resultat.append(c.toString()).append("\n");
                trouve = true;
            }
        }

        if (!trouve) {
            JOptionPane.showMessageDialog(
                    null,
                    "Aucun capteur trouvé.",
                    "Recherche",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    resultat.toString(),
                    "Résultat de la recherche",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ===== FILTRER PAR TYPE =====
    private static void filtrerParType(Scanner sc, Hopital hopital) {
        System.out.println("\nChoisissez le type :");
        System.out.println("1 - modele.Balance");
        System.out.println("2 - Oxymètre");
        System.out.println("3 - modele.Pilulier");
        System.out.println("4 - Tensiomètre");
        System.out.println("5 - Glucomètre");
        System.out.println("6 - Holter ECG");
        System.out.print("Votre choix : ");

        int type = sc.nextInt();
        sc.nextLine();

        String nomClasse = "";
        if (type == 1) {
            nomClasse = "modele.Balance";
        } else if (type == 2) {
            nomClasse = "modele.Oxymetre";
        } else if (type == 3) {
            nomClasse = "modele.Pilulier";
        } else if (type == 4) {
            nomClasse = "modele.Tensiometre";
        } else if (type == 5) {
            nomClasse = "modele.Glucometre";
        } else if (type == 6) {
            nomClasse = "modele.Holter_ECG";
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
    // filtrer pourSWING
    public static void filtrerParTypeSwing(Hopital hopital) {

        String[] types = {
                "Balance",
                "Oxymetre",
                "Pilulier",
                "Tensiometre",
                "Glucometre",
                "Holter_ECG"
        };

        String typeChoisi = (String) JOptionPane.showInputDialog(
                null,
                "Choisissez le type de capteur :",
                "Filtrer par type",
                JOptionPane.QUESTION_MESSAGE,
                null,
                types,
                types[0]
        );

        if (typeChoisi == null) return;

        StringBuilder resultat = new StringBuilder();
        boolean trouve = false;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getClass().getSimpleName().equals(typeChoisi)) {
                resultat.append(c.toString()).append("\n");
                trouve = true;
            }
        }

        if (!trouve) {
            JOptionPane.showMessageDialog(
                    null,
                    "Aucun capteur de type " + typeChoisi,
                    "Résultat",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    resultat.toString(),
                    "Capteurs : " + typeChoisi,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
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
    // afficher sans abonnement pour swing
    public static void afficherCapteursNonAbonnesSwing(Hopital hopital) {

        StringBuilder resultat = new StringBuilder();
        boolean trouve = false;

        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (!c.estAbonne()) {
                resultat.append(c.toString()).append("\n");
                trouve = true;
            }
        }

        if (!trouve) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tous les capteurs sont abonnés ✔",
                    "Capteurs non abonnés",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    resultat.toString(),
                    "Capteurs non abonnés",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ===== MODIFIER UN CAPTEUR =====
    public static void modifierCapteur(Scanner sc, Hopital hopital) {
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
    //modifier avec SWING
    public static void modifierCapteurSwing(Hopital hopital) {

        String id = JOptionPane.showInputDialog(null, "ID du capteur à modifier :");
        if (id == null || id.isEmpty()) return;

        CapteurConnecte capteur = null;
        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.getId().equals(id)) {
                capteur = c;
                break;
            }
        }

        if (capteur == null) {
            JOptionPane.showMessageDialog(null, "Capteur introuvable !");
            return;
        }

        String nouveauNom = JOptionPane.showInputDialog(
                null,
                "Nouveau nom :",
                capteur.getNom()
        );

        if (nouveauNom != null && !nouveauNom.isEmpty()) {
            capteur.setNom(nouveauNom);
            hopital.sauvegarder("capteurs.dat");
            JOptionPane.showMessageDialog(null, "Capteur modifié avec succès !");
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
    //stat pour SWING
    public static void afficherStatistiquesSwing(Hopital hopital) {

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

        int nonAbonnes = total - abonnes;

        String message =
                "📊 STATISTIQUES DES CAPTEURS\n\n"
                        + "Total de capteurs : " + total + "\n"
                        + "Capteurs abonnés : " + abonnes + "\n"
                        + "Capteurs non abonnés : " + nonAbonnes + "\n"
                        + "Capteurs en alerte : " + enAlerte;

        JOptionPane.showMessageDialog(
                null,
                message,
                "Statistiques",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
