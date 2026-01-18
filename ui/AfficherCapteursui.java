package ui;

import modele.*;

import javax.swing.*;
import java.awt.*;

public class AfficherCapteursui extends JFrame {

    private Hopital hopital;
    private DefaultListModel<String> modelCapteurs;
    private JList<String> listCapteurs;

    public AfficherCapteursui(Hopital hopital) {
        this.hopital = hopital;

        setTitle("Tous les Capteurs");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ====== Titre ======
        JLabel titre = new JLabel("Liste des Capteurs", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        // ====== Liste des capteurs ======
        modelCapteurs = new DefaultListModel<>();
        listCapteurs = new JList<>(modelCapteurs);
        JScrollPane scrollPane = new JScrollPane(listCapteurs);
        add(scrollPane, BorderLayout.CENTER);

        // ====== Bouton pour rafraîchir ======
        JButton btnRafraichir = new JButton("Rafraîchir");
        btnRafraichir.addActionListener(e -> afficherCapteurs());
        add(btnRafraichir, BorderLayout.SOUTH);

        // Affichage initial
        afficherCapteurs();

        setVisible(true);
    }

    private void afficherCapteurs() {
        modelCapteurs.clear();
        for (CapteurConnecte c : hopital.getCapteurs()) {
            modelCapteurs.addElement(c.toString()); // ici, on affiche dans JList, pas console
        }
    }

}
