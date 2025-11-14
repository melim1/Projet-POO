import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hopital hopital = new Hopital();
        Scanner sc = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n--- Menu Hôpital Intelligent ---");
            System.out.println("1. Ajouter un capteur");
            System.out.println("2. Mesurer tous les capteurs");
            System.out.println("3. Afficher toutes les alertes");
            System.out.println("4. Afficher tous les capteurs");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = sc.nextInt();

            switch (choix) {
                case 1:
                    System.out.println("Type de capteur à ajouter : 1-Balance, 2-Oxymètre, 3-Pilulier, 4-Tensiomètre");
                    int type = sc.nextInt();
                    sc.nextLine(); // consommer le retour à la ligne
                    System.out.print("Nom du capteur : ");
                    String nom = sc.nextLine();
                    String id = "C" + (int)(Math.random()*1000);

                    CapteurConnecte capteur = null;

                    switch(type) {
                        case 1: capteur = new Balance(id, nom); break;
                        case 2: capteur = new Oxymetre(id, nom); break;
                        case 3: capteur = new Pilulier(id, nom, 10); break;
                        case 4: capteur = new Tensiometre(id, nom); break;
                        default: System.out.println("Type invalide."); break;
                    }

                    if (capteur != null) {
                        // Ajouter un abonnement
                        System.out.println("Type d'abonnement : 1-Mensuel, 2-Annuel");
                        int typeAbonnement = sc.nextInt();
                        sc.nextLine(); // consommer le retour à la ligne
                        String typeAbo = (typeAbonnement == 1) ? "Mensuel" : "Annuel";

                        // Dates fixes pour test
                        Abonnement abonnement = new Abonnement(typeAbo, "01-11-2025", "30-11-2025");
                        capteur.setAbonnement(abonnement);

                        hopital.ajouterCapteur(capteur);
                    }

                    break;
                case 2:
                    hopital.mesurerTousLesCapteurs();
                    System.out.println("Mesures effectuées.");
                    break;
                case 3:
                    hopital.afficherAlertes();
                    break;
                case 4:
                    hopital.afficherCapteurs();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 0);

        sc.close();
    }
}
