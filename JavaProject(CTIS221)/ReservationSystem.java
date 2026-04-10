import java.util.ArrayList;
import java.util.HashSet;

public class ReservationSystem {


    private static final ArrayList<Reservation> reservations = new ArrayList<>();
    private static final ArrayList<Court> courts = new ArrayList<>();
    private static final HashSet<Student> students = new HashSet<>();

    private ReservationSystem() {
        // static utility class gibi kullanacağız, instance yok
    }

    public static void addCourt(Court c) {
        if (c != null) 
        	courts.add(c);
    }

    public static void addStudent(Student s) {
        if (s != null)
        	students.add(s); // HashSet duplicate engellemek için
    }

    public static boolean addReservation(Reservation r) {
        if (r == null) 
        	return false;

        Court court = r.getCourt();
        if (court == null) 
        	return false;

        // Çakışma kontrolü
        if (!isCourtAvailable(court.getCourtId(), r.getDate(), r.getStartHour(), r.getDurationHours())) {
            return false;
        }

        reservations.add(r);
        return true;
    }

    public static String displayCourts() {
        StringBuilder sb = new StringBuilder();
        for (Court c : courts) 
        	sb.append(c).append("\n");
        return sb.toString();
    }

    public static String displayReservations() {
        StringBuilder sb = new StringBuilder();
        
        for (Reservation r : reservations) 
        	sb.append(r).append("\n");
        
        return sb.toString();
    }

    public static Reservation searchReservationById(int id) //student id 
    { 	
        for (Reservation r : reservations) {
            if (r.getReservationId() == id) return r;
        }
        return null;
    }

    public static Court searchCourtById(int id) //reservation id
    {
        for (Court c : courts) {
            if (c.getCourtId() == id) return c;
        }
        return null;
    }

    public static boolean deleteReservation(int id) {
        Reservation found = searchReservationById(id);
        if (found == null) 
        	return false;
        return reservations.remove(found);
    }

    public static double calculateRevenueForDate(String date) {
        double sum = 0.0;
        for (Reservation r : reservations) {
            if (r.getDate().equals(date)) {
                sum += r.calculateTotalFee();
            }
        }
        return sum;
    }

    public static double calculateTotalRevenue() {
        double sum = 0.0;
        for (Reservation r : reservations) 
        	sum += r.calculateTotalFee();
        return sum;
    }

    public static boolean isCourtAvailable(int courtId, String date, int startHour, double durationHours) {
        int endHour = (int) Math.ceil(startHour + durationHours);  //baz saati yukrıdaki inte yuvarlamak için

        // Önce court çalışma saati kontrolü
        Court c = searchCourtById(courtId);
        if (c == null) 
        	return false;
        
        if (!c.isAvailable(date, startHour, durationHours)) 
        	return false;

        // Aynı court + aynı gün için çakışma var mı?
        for (Reservation r : reservations) {
            if (r.getCourt() == null)
            	continue;

            boolean sameCourt = r.getCourt().getCourtId() == courtId;
            boolean sameDate = r.getDate().equals(date);

            if (sameCourt && sameDate) {
                int existingStart = r.getStartHour();
                int existingEnd = (int) Math.ceil(existingStart + r.getDurationHours());

                boolean overlap = startHour < existingEnd && endHour > existingStart;
                if (overlap) 
                	return false;
            }
        }
        return true;
    }
}