import java.util.Scanner;

public class Main {

    private static void afficherMenu() {
        System.out.println("\n==============================================");
        System.out.println("           MENU - HÔPITAL INTELLIGENT         ");
        System.out.println("==============================================");
        System.out.println(" 1. Gestion des capteurs");
        System.out.println(" 2. Gestion des abonnements");
        System.out.println(" 3. Gestion des alarmes");
        System.out.println(" 4. Afficher toutes les alertes");
        System.out.println(" 5. Afficher tous les capteurs");
        System.out.println(" 6. Afficher les capteurs avec abonnement expiré");
        System.out.println(" 0. Quitter");
        System.out.println("==============================================");
        System.out.print("Votre choix : ");
    }

    public static void main(String[] args) {
        Hopital hopital = new Hopital();
        GestionAlarmes gestionAlarmes = new GestionAlarmes();
        Scanner sc = new Scanner(System.in);

        hopital.charger("capteurs.dat");
        gestionAlarmes.charger();

        int choix;

        do {
            afficherMenu();

            while (!sc.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre valide !");
                sc.next();
                afficherMenu();
            }
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1: // Gestion des capteurs
                    GestionCapteurs.afficherMenuGestionCapteurs(sc, hopital, gestionAlarmes);
                    break;

                case 2: // Gestion des abonnements
                    GestionAbonnement.afficherMenuAbonnements(sc, hopital);
                    break;

                case 3: // Gestion des alarmes - SIMPLIFIÉ
                    GestionAlarmes.afficherMenuAlarmes(sc, gestionAlarmes);
                    break;

                case 4: // Afficher alertes + CRÉER ALARMES
                    System.out.println("\n================ ALERTES ACTIVES ================");
                    hopital.afficherAlertes();

                    // Créer les alarmes automatiquement
                    gestionAlarmes.verifierEtCreerAlarmes(hopital);

                    System.out.println("================================================");
                    break;

                case 5: // Afficher capteurs
                    System.out.println("\n================ LISTE DES CAPTEURS ================");
                    hopital.afficherCapteurs();
                    System.out.println("===================================================");
                    break;
                case 6:
                    hopital.afficherCapteursExpirés();
                    break;

                case 0: // Quitter
                    hopital.sauvegarder("capteurs.dat");
                    gestionAlarmes.sauvegarder();
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 0);

        sc.close();
    }
}
