package ui;

import javax.swing.*;
import java.awt.*;

import modele.*;

public class MainFrame extends JFrame {
    private Hopital hopital;
    private GestionAlarmes gestionAlarmes;
    private CapteurConnecte capteurConnecte;

    public MainFrame() {
        hopital = new Hopital();
        gestionAlarmes = new GestionAlarmes();

        hopital.charger("capteurs.dat");
        gestionAlarmes.charger();

        setTitle("modele.Hopital Intelligent");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Ajouter le titre du menu
        JLabel titre = new JLabel("MENU - HOPITAL INTELLIGENT", SwingConstants.CENTER);
        titre.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        setLayout(new java.awt.BorderLayout());
        add(titre, BorderLayout.NORTH);

        // Ajouter les boutton
        JPanel panelBouttons = new JPanel();
        panelBouttons.setLayout(new java.awt.GridLayout(3, 3, 10, 10));
        JButton btnCapteur = new JButton("Gestion des capteurs");
        JButton btnAbonnements = new JButton("Gestion des abonnements");
        JButton btnAlarme = new JButton("Gestion des alarmes");
        JButton btnTousCapteur = new JButton("Afficher tous les capteurs");
        JButton btnExpires = new JButton("Capteurs expirés");
        JButton btnQuitter = new JButton("Quitter");
        JButton btnAlerte = new JButton("Gestion des alertes");
        panelBouttons.add(btnCapteur);
        panelBouttons.add(btnAbonnements);
        panelBouttons.add(btnAlarme);
        panelBouttons.add(btnAlerte);
        panelBouttons.add(btnTousCapteur);
        panelBouttons.add(btnExpires);
        panelBouttons.add(btnQuitter);
        add(panelBouttons, java.awt.BorderLayout.CENTER);

        btnTousCapteur.addActionListener(e -> {
            hopital.afficherCapteurs();
        });

        btnCapteur.addActionListener(e -> {
            new GestionCapteurui(hopital);
        });
        btnAbonnements.addActionListener(e -> {
            new GestionAbonnementui(hopital);
        });
        btnAlarme.addActionListener(e -> {
            new GestionAlarmesui(hopital, gestionAlarmes);
        });

        btnTousCapteur.addActionListener(e -> {
            new AfficherCapteursui(hopital);
        });
        btnQuitter.addActionListener(e -> {
            System.exit(0);
        });

        btnExpires.addActionListener(e -> {
            StringBuilder listeExpires = new StringBuilder();
            boolean alerteTrouvee = false;

            
            for (CapteurConnecte c : hopital.getCapteurs()) {
        
                 
                if (c.getAbonnement() != null && !c.getAbonnement().estValide()) {
                    listeExpires.append("• ").append(c.getNom()).append(" (Fin le : ").append(c.getAbonnement().getDateFin()).append(")\n");
                    alerteTrouvee = true;
                 }
            }

            if (alerteTrouvee) {
                
                JOptionPane.showMessageDialog(this, "⚠️ ATTENTION : Les abonnements suivants sont expirés :\n\n" + listeExpires.toString(),"Capteurs Expirés", JOptionPane.WARNING_MESSAGE);
            } 
            else {
                
                JOptionPane.showMessageDialog(this, "Tous les abonnements sont valides ! ✅");
            }
        });

    }

}
