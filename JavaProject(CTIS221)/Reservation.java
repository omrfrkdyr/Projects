public class Reservation {
    private int reservationId;
    private String date;         // basit tutuyoruz: "2025-12-22"
    private int startHour;       // 0-23
    private double durationHours;
    private int playerCount;
    private double totalFee;

    // has-a
    private Court court;
    private Student student;

    public Reservation()
    {
    	
    }

    public Reservation(int reservationId, String date, int startHour,
                       double durationHours, int playerCount,
                       Court court, Student student) {
        this.reservationId = reservationId;
        this.date = date;
        this.startHour = startHour;
        this.durationHours = durationHours;
        this.playerCount = playerCount;
        this.court = court;
        this.student = student;
        this.totalFee = calculateTotalFee();
    }

    public double calculateTotalFee() {
        if (court == null) return 0.0;
        return court.calculateFee(durationHours);
    }

    public int getReservationId()
    { 
    	return reservationId;
    }
    
    public String getDate() 
    { 
    	return date; 
    }
    
    public int getStartHour() 
    {
    	return startHour;
    }
    
    public double getDurationHours() 
    { 
    	return durationHours;
    }
    
    public Court getCourt() 
    { 
    	return court; 
    }

    
    @Override
    public String toString() {
        return 
                "\nRreservation Id = " + reservationId +
                "\nDate = " + date +
                "\nStart Hour = " + startHour +
                "\nDuration Hours = " + durationHours +
                "\nPlayer Count = " + playerCount +
                "\nTotal Fee = " + totalFee +
                "\nCourt = " + (court != null ? court.getName() : "null") +
                "\nStudent = " + (student != null ? student.getFullName() : "null") ;
    }
}