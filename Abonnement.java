public class Abonnement {
    private String type; // ex: "Mensuel", "Annuel"
    private String dateDebut;
    private String dateFin;
    private boolean actif;

    public Abonnement(String type, String dateDebut, String dateFin){
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.actif = true;
    }

    public boolean estValide(){
        // Vérifier la validité selon la date ou juste le booléen
        return actif;
    }

    public void desactiver() {
        actif = false;
    }
    public String getType() {
    return type;
}
    public String getDateDebut() {
        return dateDebut;
    }
    public String getDateFin() {
        return dateFin;
    }
    
}

