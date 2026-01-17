package modele;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionAlarmes implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Alarme> historique;

    public GestionAlarmes() {
        historique = new ArrayList<>();
    }

    // ========== MENU AVEC MARQUER COMME TRAITÉE ==========
    public static void afficherMenuAlarmes(Scanner sc, GestionAlarmes gestionAlarmes) {
        System.out.println("\n========== GESTION DES ALARMES ==========");
        System.out.println(" 1. Afficher toutes les alarmes");
        System.out.println(" 2. Afficher les alarmes non traitées");  // AJOUTÉ
        System.out.println(" 3. Marquer une alarme comme traitée");    // AJOUTÉ
        System.out.println(" 4. Exporter les alarmes en CSV");
        System.out.println(" 0. Retour au menu principal");
        System.out.println("=========================================");
        System.out.print("Votre choix : ");

        int choix = sc.nextInt();
        sc.nextLine();

        if (choix == 1) {
            gestionAlarmes.afficherHistorique();
        } else if (choix == 2) {
            gestionAlarmes.afficherAlarmesNonTraitees();  // AJOUTÉ
        } else if (choix == 3) {
            gestionAlarmes.marquerTraitee(sc);            // AJOUTÉ
        } else if (choix == 4) {
            gestionAlarmes.exporterAlarmesCSV();
        } else if (choix == 0) {
            return;
        } else {
            System.out.println("Choix invalide !");
        }
    }

    // ========== CRÉER LES ALARMES ==========
    public void verifierEtCreerAlarmes(Hopital hopital) {
        for (CapteurConnecte c : hopital.getCapteurs()) {
            if (c.verifierAlerte()) {
                String typeAlarme = determinerTypeAlarme(c);
                Alarme alarme = new Alarme(c.getId(), c.getNom(), typeAlarme, c.getValeur());
                historique.add(alarme);
            }
        }
        sauvegarder();
    }

    // ========== DÉTERMINER LE TYPE D'ALARME ==========
    private String determinerTypeAlarme(CapteurConnecte capteur) {
        if (capteur instanceof Balance) {
            double poids = capteur.getValeur();
            return poids < 45 ? "Poids trop faible" : "Poids trop élevé";
        } else if (capteur instanceof Oxymetre) {
            return "Saturation oxygène faible";
        } else if (capteur instanceof Pilulier) {
            return "Doses épuisées";
        } else if (capteur instanceof Tensiometre) {
            return "Tension anormale";
        } else if (capteur instanceof Glucometre) {
            double glycemie = capteur.getValeur();
            return glycemie < 0.70 ? "Hypoglycémie" : "Hyperglycémie";
        } else if (capteur instanceof Holter_ECG) {
            int freq = (int) capteur.getValeur();
            return freq < 50 ? "Bradycardie" : "Tachycardie";
        }
        return "Alerte générique";
    }

    // ========== AFFICHER TOUTES LES ALARMES ==========
    private void afficherHistorique() {
        System.out.println("\n========== HISTORIQUE DES ALARMES ==========");

        if (historique.isEmpty()) {
            System.out.println("Aucune alarme enregistrée.");
        } else {
            System.out.println("Total d'alarmes : " + historique.size());
            System.out.println("--------------------------------------------");
            for (Alarme a : historique) {
                System.out.println(a);
            }
        }
        System.out.println("============================================\n");
    }

    // ========== AFFICHER ALARMES NON TRAITÉES ========== (NOUVEAU)
    private void afficherAlarmesNonTraitees() {
        System.out.println("\n====== ALARMES NON TRAITÉES ======");
        boolean trouve = false;

        for (Alarme a : historique) {
            if (!a.estTraitee()) {
                System.out.println(a);
                trouve = true;
            }
        }

        if (!trouve) {
            System.out.println("✓ Aucune alarme non traitée.");
        }
        System.out.println("==================================\n");
    }

    // ========== MARQUER COMME TRAITÉE ========== (NOUVEAU)
    private void marquerTraitee(Scanner sc) {
        // Afficher d'abord les alarmes non traitées
        System.out.println("\n====== ALARMES NON TRAITÉES ======");
        List<Alarme> nonTraitees = new ArrayList<>();
        int index = 1;

        for (Alarme a : historique) {
            if (!a.estTraitee()) {
                System.out.println(index + ". " + a);
                nonTraitees.add(a);
                index++;
            }
        }

        if (nonTraitees.isEmpty()) {
            System.out.println("✓ Aucune alarme à traiter.");
            System.out.println("==================================\n");
            return;
        }

        System.out.println("==================================");
        System.out.print("Entrez le numéro de l'alarme à marquer comme traitée (0 pour annuler) : ");
        int choix = sc.nextInt();
        sc.nextLine();

        if (choix > 0 && choix <= nonTraitees.size()) {
            nonTraitees.get(choix - 1).marquerTraitee();
            sauvegarder();
            System.out.println("✓ modele.Alarme marquée comme traitée !");
        } else if (choix != 0) {
            System.out.println("Numéro invalide !");
        }
    }

    // ========== EXPORTER EN CSV ==========
    private void exporterAlarmesCSV() {
        try (FileWriter writer = new FileWriter("alarmes.csv")) {
            writer.write("DateHeure;IDCapteur;NomCapteur;TypeAlarme;Valeur;Traitee\n");

            for (Alarme a : historique) {
                writer.write(String.format("%s;%s;%s;%s;%.2f;%s\n",
                        a.getDateHeure(), a.getIdCapteur(), a.getNomCapteur(),
                        a.getTypeAlarme(), a.getValeur(),
                        a.estTraitee() ? "Oui" : "Non"));  // MODIFIÉ
            }

            writer.flush();
            System.out.println("✓ Alarmes exportées dans alarmes.csv");

        } catch (IOException e) {
            System.out.println("Erreur export CSV : " + e.getMessage());
        }
    }

    // ========== SAUVEGARDER ==========
    public void sauvegarder() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("alarmes.dat"))) {
            oos.writeObject(historique);
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde alarmes : " + e.getMessage());
        }
    }

    // ========== CHARGER ==========
    public void charger() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("alarmes.dat"))) {
            historique = (List<Alarme>) ois.readObject();
            System.out.println("✓ Historique d'alarmes chargé (" + historique.size() + " alarmes).");
        } catch (FileNotFoundException e) {
            System.out.println("Aucun historique d'alarmes trouvé.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur chargement alarmes : " + e.getMessage());
        }
    }
}
