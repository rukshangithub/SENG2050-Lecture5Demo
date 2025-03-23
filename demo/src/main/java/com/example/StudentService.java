package com.example;

public class StudentService {
    
    private StudentDAO studentDAO = new StudentDAOImpl();
   
    /*
     * Business logic to authenticate a student
     * - Returns null if the student is not authenticated,
     * - Otherwise return the student object
     * */
    public Student authenticateStudent(String stdNo, String password)
    {
        Student student  = studentDAO.getStudentByStdNo(stdNo); // Finds the student based on stdNo
        
        if (student!=null)
        {
            PasswordSecurity pSec = new PasswordSecurity();
            if (pSec.verifyPassword(password, student))
                return student;

        }
        return null;
    }

    public void addStudent(String stdNo, String givenNames, String lastName, String password)
    {
        // Generate salt and password hash
        PasswordSecurity pSec = new PasswordSecurity();
        Double salt = pSec.generateSalt();
        String passwordHash = pSec.hashPassword(password, salt);

        // Create student object and add to database
        Student student  = new Student(stdNo, givenNames, lastName, passwordHash, salt);
        studentDAO.addStudent(student);
    }
}
