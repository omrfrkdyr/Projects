public class BasketballCourt extends Court {
    private boolean hasScoreboard;
    private boolean halfCourtAllowed;

    public BasketballCourt() {
        super();
    }

    public BasketballCourt(int courtId, String name, double hourlyRate,
                           int openHour, int closeHour, boolean isIndoor,
                           boolean hasScoreboard, boolean halfCourtAllowed) {
        super(courtId, name, hourlyRate, openHour, closeHour, isIndoor);
        this.hasScoreboard = hasScoreboard;
        this.halfCourtAllowed = halfCourtAllowed;
    }

    @Override
    public double calculateFee(double durationHours) {
        double base = hourlyRate * durationHours;
        double scoreboardFee = hasScoreboard ? 10.0 * durationHours : 0.0; 
        return base + scoreboardFee;
    }

    @Override
    public boolean reserve(Reservation r) {
        return ReservationSystem.addReservation(r);
    }

    @Override
    public boolean cancel(int reservationId) {
        return ReservationSystem.deleteReservation(reservationId);
    }

    @Override
    public String toString() {
        return "\nBasketballCourt" +
                "\nCourt Id = " + courtId +
                "\nName = " + name +
                "\nHourly Rate = " + hourlyRate +
                "\nHas Scoreboard = " + hasScoreboard +
                "\nHalf Court Allowed=" + halfCourtAllowed ;
    }
}