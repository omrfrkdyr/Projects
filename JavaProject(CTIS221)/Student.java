import java.util.Objects;

public class Student {
    private int studentId;
    private String fullName;
    private String phone;

    public Student() 
    {
    	
    }

    public Student(int studentId, String fullName, String phone) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.phone = phone;
    }

    public void getInput() {
        
    }

    public int getStudentId() 
    { 
    	return studentId; 
    }
    
    public String getFullName() 
    {
    	return fullName; 
    }

    @Override
    public String toString() {
        return "Student" +
                "\nStudent Id = " + studentId +
                "\nFull Name = " + fullName + 
                "\nPhone = " + phone ;
    }

      @Override
    public boolean equals(Object o) {
        if (this == o) 
        	return true;
        
        if (!(o instanceof Student)) 
        	return false;
        
        Student student = (Student) o;
        return studentId == student.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}