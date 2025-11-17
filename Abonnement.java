import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Abonnement implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type; // ex: "Mensuel", "Annuel"
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public Abonnement(String type, String debut, String fin){
        this.type = type;
        this.dateDebut = LocalDate.parse(debut, FORMAT);
        this.dateFin = LocalDate.parse(fin, FORMAT);
    
    }
    

    public boolean estValide(){
        // Vérifier la validité selon la date ou juste le booléen
        LocalDate aujourdHui = LocalDate.now();
        return !aujourdHui.isAfter(dateFin);
    }

    private LocalDate parseDate(String dateStr) {
    try {
        return LocalDate.parse(dateStr, FORMAT);
    } catch (Exception e) {
        throw new IllegalArgumentException("❌ Date invalide : " + dateStr);
    }
}


    public void desactiver() {
         dateFin = LocalDate.now().minusDays(1);
    }
    public String getType() { return type; }
    public String getDateDebut() { return dateDebut.format(FORMAT); }
    public String getDateFin() { return dateFin.format(FORMAT); }

    @Override
    public String toString() {
        return type + " (" + getDateDebut() + " → " + getDateFin() +
               ", actif=" + estValide() + ")";
    }
    public boolean estActif() {
    return DateUtils.estApresAujourdhui(this.dateFin);
}

    
}

