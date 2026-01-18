package ui;

import modele.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GestionAlarmesui extends JFrame {

    private GestionAlarmes gestionAlarmes;
    private Hopital hopital;

    private JList<String> listAlarmes;
    private DefaultListModel<String> modelAlarmes;

    public GestionAlarmesui(Hopital hopital, GestionAlarmes gestionAlarmes) {
        this.hopital = hopital;
        this.gestionAlarmes = gestionAlarmes;

        setTitle("Gestion des Alarmes - Hôpital Intelligent");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ====== Titre ======
        JLabel titre = new JLabel("Gestion des Alarmes", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        // ====== Liste des alarmes ======
        modelAlarmes = new DefaultListModel<>();
        listAlarmes = new JList<>(modelAlarmes);
        listAlarmes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listAlarmes);
        add(scrollPane, BorderLayout.CENTER);

        // ====== Panneau des boutons ======
        JPanel panelBoutons = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton btnToutAfficher = new JButton("Tout afficher");
        JButton btnNonTraitees = new JButton("Afficher non traitées");
        JButton btnMarquer = new JButton("Marquer comme traitée");
        JButton btnExporterCSV = new JButton("Exporter CSV");

        panelBoutons.add(btnToutAfficher);
        panelBoutons.add(btnNonTraitees);
        panelBoutons.add(btnMarquer);
        panelBoutons.add(btnExporterCSV);

        add(panelBoutons, BorderLayout.SOUTH);

        // ====== Actions des boutons ======
        btnToutAfficher.addActionListener(e -> afficherToutes());

        btnNonTraitees.addActionListener(e -> afficherNonTraitees());

        btnMarquer.addActionListener(e -> {
            int index = listAlarmes.getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une alarme !");
                return;
            }

            List<Alarme> nonTraitees = gestionAlarmes.getAlarmesNonTraitees();
            if (index >= nonTraitees.size()) {
                JOptionPane.showMessageDialog(this, "Sélection invalide !");
                return;
            }

            Alarme a = nonTraitees.get(index);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Marquer l'alarme sélectionnée comme traitée ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                a.marquerTraitee();
                gestionAlarmes.sauvegarder();
                afficherNonTraitees();
                JOptionPane.showMessageDialog(this, "Alarme marquée comme traitée !");
            }
        });

        btnExporterCSV.addActionListener(e -> {
            gestionAlarmes.exporterAlarmesCSV();
            JOptionPane.showMessageDialog(this, "Export CSV terminé !");
        });

        // ====== Affichage initial ======
        afficherToutes();

        setVisible(true);
    }

    // ====== Méthodes pour remplir la liste ======
    private void afficherToutes() {
        modelAlarmes.clear();
        for (Alarme a : gestionAlarmes.getHistorique()) {
            modelAlarmes.addElement(a.toString());
        }
    }

    private void afficherNonTraitees() {
        modelAlarmes.clear();
        List<Alarme> nonTraitees = gestionAlarmes.getAlarmesNonTraitees();
        if (nonTraitees.isEmpty()) {
            modelAlarmes.addElement("✓ Aucune alarme non traitée");
        } else {
            for (Alarme a : nonTraitees) {
                modelAlarmes.addElement(a.toString());
            }
        }
    }
}
