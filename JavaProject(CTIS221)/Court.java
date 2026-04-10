public abstract class Court implements Reservable {
    protected int courtId;
    protected String name;
    protected double hourlyRate;
    protected int openHour;
    protected int closeHour;
    protected boolean isIndoor;

    private static int totalCourts = 0;

    public Court() {
        totalCourts++;
    }

    public Court(int courtId, String name, double hourlyRate, int openHour, int closeHour, boolean isIndoor) {
        this.courtId = courtId;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.isIndoor = isIndoor;
        totalCourts++;
    }

    public static int getTotalCourts() {
        return totalCourts;
    }

    public void getInput() {
    	// GUI kısmı daha yapılmadığı için boş bırakıldı
    }

    public boolean isAvailable(String date, int startHour, double durationHours) {
        int endHour = (int) Math.ceil(startHour + durationHours);

        boolean withinWorkingHours = startHour >= openHour && endHour <= closeHour;
        
        if (!withinWorkingHours) 
        	return false;

        return true;
    }

    public abstract double calculateFee(double durationHours);

    @Override
    public String toString() {
        return "Court" +
                "\nCourt Id = " + courtId +
                "\nName = " + name +
                "\nHourly Rate = " + hourlyRate +
                "\nOpen Hour = " + openHour +
                "\nClose Hour = " + closeHour +
                "\nIs Indoor = " + isIndoor ;
    }

    // Getters (GUI için lazım olur)
    public int getCourtId() 
    { 
    	return courtId; 
    }
    
    public String getName() 
    { 
    	return name; 
    }
    public double getHourlyRate() 
    { 
    	return hourlyRate; 
    }
}