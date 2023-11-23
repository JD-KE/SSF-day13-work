package sg.edu.nus.iss.day13work.repo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day13work.model.Employee;

@Repository
public class EmployeeRepo {

    final String dirPath = "c://data";

    final String filename = "employee.txt";

    private List<Employee> employees;

    public EmployeeRepo() throws ParseException  {
        if (employees == null) {
            employees = new ArrayList<Employee>();
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dt = df.parse("1965-08-09");
        Employee employee = new Employee("Hsien Loong", "Lee", "leehsienloong@gov.sg", "91234567", 8500, dt, 272210);
        employees.add(employee);

        employee = new Employee("Pritam", "Singh", "pritam@wp.sg", "92345678", 7500, dt, 272210);
        employees.add(employee);
    }
    
    public List<Employee> findAll() {
        return employees;
    }

    public Boolean delete (Employee employee) {
        Boolean result = false;

        int employeeIndex = employees.indexOf(employee);

        if (employeeIndex >= 0) {
            employees.remove(employeeIndex);
            result = true;
        }

        return result;
    }

    public Employee findByEmail(String email) {
        return employees.stream()
            .filter(e -> e.getEmail().equals(email)).findFirst().get();
    }

    public Boolean save(Employee employee) throws FileNotFoundException {
        Boolean result = false;

        result = employees.add(employee);

        File f = new File(dirPath + "/" + filename);
        OutputStream os = new FileOutputStream(f, true);
        PrintWriter pw = new PrintWriter(os);
        pw.println(employee.toString());
        pw.flush();
        pw.close();

        return result;
    }

    public Boolean updateEmployee(Employee employee) {
        Boolean result = false;

        // get original employee
        Employee selectedEmployee = employees.stream()
            .filter(e -> e.getEmail().equals(employee.getEmail())).findFirst().get();

        int employeeIndex = employees.indexOf(selectedEmployee);

        if (employeeIndex >= 0) {
            employees.get(employeeIndex).setBirthDay(employee.getBirthDay());
            employees.get(employeeIndex).setEmail(employee.getEmail());
            employees.get(employeeIndex).setFirstName(employee.getFirstName());
            employees.get(employeeIndex).setLastName(employee.getLastName());
            employees.get(employeeIndex).setPhoneNo(employee.getPhoneNo());
            employees.get(employeeIndex).setPostalCode(employee.getPostalCode());
            employees.get(employeeIndex).setSalary(employee.getSalary());

            result = true;
        }


        return result;
    }
}
