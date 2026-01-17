package ui;


import modele.*;

import javax.swing.*;

public class GestionCapteurui extends JFrame {
    private Hopital hopital;
    private CapteurConnecte capteurConnecte;
    private GestionCapteurs gestionCapteurs;
    private GestionAlarmes gestionAlarmes;
    public GestionCapteurui(Hopital hopital){
        this.hopital=hopital;
        this.gestionAlarmes = new GestionAlarmes();
        setTitle("Gestion des capteurs");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.GridLayout(9, 1, 10, 10));

        JButton btnAjouter = new JButton("Ajouter un capteur");
        JButton btnModifier = new JButton("Modifier un capteur ");
        JButton btnSupprimer = new JButton("Supprimer un capteur");
        JButton btnMesurer = new JButton("Mesurer tous les capteurs ");
        JButton btnRechercher = new JButton("Rechercher un capteur ");
        JButton btnFiltrer = new JButton("Filtrer les capteurs par type");
        JButton btnAfficher = new JButton("Afficher les capteurs non abonnés ");
        JButton btnStat = new JButton("Statistiques des capteurs ");
        JButton btnRetour = new JButton("Retour au menu principal");

        add(btnAjouter);
        add(btnModifier);
        add(btnSupprimer);
        add(btnMesurer);
        add(btnRechercher);
        add(btnFiltrer);
        add(btnAfficher);
        add(btnStat);
        add(btnRetour);

        btnRetour.addActionListener(e -> {
            dispose();
        });

        btnAjouter.addActionListener(e -> {
            // 1️⃣ Choisir le type de capteur
            String[] types = {"Balance","Glucometre","Holter_ECG","Oxymetre","Pilulier", "Tensiometre"};
            String type = (String) JOptionPane.showInputDialog(
                    this,
                    "Choisir le type de capteur :",
                    "Type de capteur",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    types,
                    types[0]
            );

            if (type != null) { // si l'utilisateur n'annule pas
                // 2️⃣ Demander le nom
                String nom = JOptionPane.showInputDialog(this, "Nom du capteur ?");
                if (nom != null && !nom.isEmpty()) {
                    // 3️⃣ Créer un ID automatique
                    String id = "C" + (hopital.getCapteurs().size() + 1);

                    // 4️⃣ Créer le capteur selon le type choisi
                    CapteurConnecte c;
                    switch (type) {
                        case "Balance":
                            c=new Balance(id,nom);
                            break;
                        case "Glucometre":
                            c = new Glucometre(id, nom);
                            break;
                        case "Holter_ECG":
                            c = new Holter_ECG(id,nom);
                            break;
                        case "Oxymetre":
                            c = new Oxymetre(id,nom);
                            break;
                        case "Pilulier":
                            int doseinitial = Integer.parseInt(JOptionPane.showInputDialog(this, "La dose initiale ?"));;
                            c = new Pilulier(id, nom,doseinitial);
                            break;
                        case "Tensiometre":
                            c = new Tensiometre(id, nom);
                            break;
                        default:
                            c = null;
                    }

                    // 5️⃣ Ajouter au hopital
                    if (c != null) {
                        hopital.ajouterCapteur(c);
                        JOptionPane.showMessageDialog(this, "Capteur ajouté : " + nom + " (ID: " + id + ")");
                    }
                }
            }
        });

        btnModifier.addActionListener( e ->{
            gestionCapteurs.modifierCapteurSwing(hopital);

        });
        btnSupprimer.addActionListener( e ->{
            gestionCapteurs.supprimerCapteurSwing(hopital);
        });
        btnMesurer.addActionListener(e -> {
            gestionCapteurs.mesurerTousLesCapteursSwing(hopital,gestionAlarmes);
        });
        btnRechercher.addActionListener( e -> {
            gestionCapteurs.rechercherCapteurSwing(hopital);
        });
        btnFiltrer.addActionListener(e ->{
            gestionCapteurs.filtrerParTypeSwing(hopital);
        });
        btnAfficher.addActionListener(e ->{
            gestionCapteurs.afficherCapteursNonAbonnesSwing(hopital);
        });
        btnStat.addActionListener(e ->{
            gestionCapteurs.afficherStatistiquesSwing(hopital);
        });

        setVisible(true);


    }
}
