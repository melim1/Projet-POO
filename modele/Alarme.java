package modele;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alarme implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idCapteur;
    private String nomCapteur;
    private String typeAlarme;
    private double valeur;
    private String dateHeure;
    private boolean traitee;
    private CapteurConnecte capteur;
// AJOUTÉ

    public Alarme(String idCapteur, String nomCapteur, String typeAlarme, double valeur) {
        this.idCapteur = idCapteur;
        this.nomCapteur = nomCapteur;
        this.typeAlarme = typeAlarme;
        this.valeur = valeur;
        this.traitee = false;  // Par défaut : non traitée

        // Date et heure actuelles
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.dateHeure = LocalDateTime.now().format(formatter);
    }

    // AJOUTÉ
    public void marquerTraitee() {
        this.traitee = true;
    }

    public String getIdCapteur() { return idCapteur; }
    public String getNomCapteur() { return nomCapteur; }
    public String getTypeAlarme() { return typeAlarme; }
    public double getValeur() { return valeur; }
    public String getDateHeure() { return dateHeure; }
    public boolean estTraitee() { return traitee; }  // AJOUTÉ
    public CapteurConnecte getCapteur() { return capteur; }

    @Override
    public String toString() {
        String statut = traitee ? "[✓ TRAITÉE]" : "[⚠ NON TRAITÉE]";  // MODIFIÉ
        return String.format("[%s] %s (%s) - %s : %.2f %s",
                dateHeure, nomCapteur, idCapteur, typeAlarme, valeur, statut);
    }
}
