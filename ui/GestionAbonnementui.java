package ui;

import modele.GestionAbonnement;

import javax.swing.*;
import modele.*;
public class GestionAbonnementui extends JFrame {
    private Hopital hopital;
    private GestionAbonnement gestionAbonnement;

    public GestionAbonnementui(Hopital hopital){
        this.hopital=hopital;
        setTitle("Gestion d'abonnement");
        setSize(500,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(null);
        setVisible(true);

        JButton btnAfficher = new JButton("Afficher tous les abonnements");
        JButton btnAffichernon = new JButton("Afficher les capteurs non abonnés ");
        JButton btnRenouvler = new JButton("Renouveler un abonnement");
        JButton btnDesactiver = new JButton("Désactiver un abonnement");
        JButton btnRetour = new JButton("Retour au menu principal");

        add(btnAfficher);
        add(btnAffichernon);
        add(btnRenouvler);
        add(btnDesactiver);
        add(btnRetour);

        btnRetour.addActionListener(e -> {
            dispose();
        });
        btnAfficher.addActionListener(e ->{
            gestionAbonnement.afficherAbonnementsSwing(hopital);
        });
        btnRenouvler.addActionListener(e -> {
            gestionAbonnement.renouvelerAbonnementSwing(hopital);
        });
        btnDesactiver.addActionListener(e -> {
            gestionAbonnement.desactiverAbonnementSwing(hopital);
        });
        btnAffichernon.addActionListener(e ->{
            GestionAbonnement.afficherCapteursNonAbonnesSwing(hopital);
        });



    }
}
