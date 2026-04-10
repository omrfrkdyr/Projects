public class TennisCourt extends Court {
    private String surfaceType;        // Clay/Hard/Grass
    private boolean hasBallMachine;

    public TennisCourt() {
        super();
    }

    public TennisCourt(int courtId, String name, double hourlyRate,
                       int openHour, int closeHour, boolean isIndoor,
                       String surfaceType, boolean hasBallMachine) {
        super(courtId, name, hourlyRate, openHour, closeHour, isIndoor);
        this.surfaceType = surfaceType;
        this.hasBallMachine = hasBallMachine;
    }

    @Override
    public double calculateFee(double durationHours) {
        double base = hourlyRate * durationHours;
        double ballMachineFee = hasBallMachine ? 20.0 * durationHours : 0.0;
        return base + ballMachineFee;
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
        return "\nTennisCourt" +
                "\nCourt Id = " + courtId +
                "\nName = " + name +
                "\nHourly Rate = " + hourlyRate +
                "\nSurface Type = " + surfaceType +
                "\nHas Ball Machine = " + hasBallMachine;
    }
}