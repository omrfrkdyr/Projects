
public interface Reservable {

	boolean reserve(Reservation r);
    boolean cancel(int reservationId);
	
}
