import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static boolean estApresAujourdhui(LocalDate dateFin) {
    LocalDate today = LocalDate.now();
    return dateFin.isAfter(today) || dateFin.isEqual(today);
}

}
