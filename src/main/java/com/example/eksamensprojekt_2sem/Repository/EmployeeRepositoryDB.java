package com.example.eksamensprojekt_2sem.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryDB implements EmployeeIRepository {

    @Value("jdbc:mysql://localhost:3306/pct_db2")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("1234")
    private String pwd;


    //---------------------------------EMPLOYEE JDBC METHODS-------------------------------------//

    /*
    //Get all employees from org
    public List<Employee> getEmployeesByOrgID(int organization_id) {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM employee WHERE organization_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, organization_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int employee_id = rs.getInt("employee_id");
                String employee_firstname = rs.getString("employee_firstname");
                String employee_lastname = rs.getString("employee_lastname");
                String email = rs.getString("email");

                employees.add(new Employee(employee_id, employee_firstname, employee_lastname, email, organization_id));
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Get one employee by org and emp id
    public Employee getEmployeeByIDs(int organization_id, int employee_id) {
        Employee employee = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "SELECT * FROM employee WHERE organization_id = ? AND employee_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, organization_id);
            pstmt.setInt(2, employee_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String employee_firstname = rs.getString("employee_firstname");
                String employee_lastname = rs.getString("employee_lastname");
                String email = rs.getString("email");

                employee = new Employee(employee_id, employee_firstname, employee_lastname, email, organization_id);
            }
            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //Create employee and add to organization
    public Employee createEmployee(Employee employee, int organization_id) {
        Employee emp = null;
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "INSERT INTO employee (employee_firstname, employee_lastname, email, organization_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, employee.getFirst_name());
            pstmt.setString(2, employee.getLast_name());
            pstmt.setString(3, employee.getEmail());
            pstmt.setInt(4, organization_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int employee_id = rs.getInt(1);
                emp = new Employee(employee_id, employee.getFirst_name(), employee.getLast_name(), employee.getEmail(), organization_id);
            }
            return emp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Edit employee
    public void editEmployee(Employee employee, int organization_id, int employee_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, uid, pwd);
            String SQL = "UPDATE employee SET employee_firstname = ?, employee_lastname = ?, email = ? WHERE organization_id = ? AND employee_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, employee.getFirst_name());
            pstmt.setString(2, employee.getLast_name());
            pstmt.setString(3, employee.getEmail());
            pstmt.setInt(4, organization_id);
            pstmt.setInt(5, employee_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     */


}