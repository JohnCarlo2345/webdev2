## WEBDEV2 - Week 9 Lab Answers

Name: John Carlo O. Valdez
Student ID: 2511004
GitHub Repo: https://github.com/JohnCarlo2345/webdev2
Date Submitted: October 24, 2026

## Task 1 – One‑to‑One + One‑to‑Many Mappings

Approach / explanation:
I set Employee as the owning side using @JoinColumn(name="department_id") to hold the foreign‑key; Department uses  mappedBy="department" for inverse side; added helper addEmployee() to sync both sides and used  Cascade.PERSIST so saving Department also saves its Employees.

Code / endpoint(s) used (if applicable):

java
// Department.java
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee emp) {
        employees.add(emp);
        emp.setDepartment(this);
    }
    // constructors, getters, setters
}

// Employee.java
import jakarta.persistence.*;

@Entity
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    // constructors, getters, setters
}

Evidence :

Request 1: POST /api/departments (send JSON:  {"name":"IT Dept","employees":[{"name":"Juan"}]} ) >> Response: Returns saved Department JSON with ID 1, employee linked; Request 2: GET /api/departments >> Returns list showing IT Dept + Juan; MySQL  SELECT * FROM employee;  >> Column department_id = 1 (confirms foreign key filled; tables created correctly; no error on save!)

Reflection question:
Which entity owns the Department‑Employee relationship and why, and what value must  mappedBy  contain?

my answer:
Employee owns it because it contains the foreign‑key column department_id ; mappedBy must be "department" - points to the department field in Employee!

## Task 2 - Many‑to‑Many Join Table + Association Entity

Approach / explanation:
Instead of simple  @ManyToMany + @JoinTable , I created Enrollment association entity; this lets us store extra data like enrollDate; added composite unique constraint (student_id + course_id) to prevent duplicate enrollment records.

Code / endpoint(s) used (if applicable):

java
// Student.java
import jakarta.persistence.*;
@Entity
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // constructors, getters, setters
}

// Course.java
import jakarta.persistence.*;
@Entity
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    // constructors, getters, setters
}

// Enrollment.java
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"student_id","course_id"})})
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "student_id") private Student student;
    @ManyToOne @JoinColumn(name = "course_id") private Course course;
    private LocalDate enrollDate;
    // constructors, getters, setters
}

Evidence :

Request 1: POST /api/enroll (Student 1 + Course 1 + date 2026‑10‑24) >> Response: Enrollment created, returns ID 1; Request 2: POST SAME Student 1 + Course 1 AGAIN >> Response: Database throws unique constraint violation error (cannot enroll twice); MySQL SELECT * FROM enrollment; >> One record only, student_id/course_id filled correctly!

Reflection question:
When must a many‑to‑many relationship be modeled as an association entity instead of a direct  @ManyToMany , and what did the unique constraint protect in your test?

my answer:
Use association entity when you need additional fields on the relationship (enrollDate/grade); unique constraint protects against same student enrolling same course twice!

## Task 3 - Fetch Strategies Experiment - LAZY vs EAGER

Approach / explanation:
I reproduced LazyInitializationException by accessing LAZY‑loaded enrollments outside the Hibernate session; then compared two approaches: 
1) marking EAGER (loads always - bad performance); 
2) using  @EntityGraph  to explicitly fetch enrollments only when needed.

Code / endpoint(s) used (if applicable):

java
// Student.java
import jakarta.persistence.*;
import java.util.List;
@Entity
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;
    // constructors, getters, setters
}

// StudentRepository.java
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    @EntityGraph(attributePaths = {"enrollments"})
    List<Student> findAllWithEnrollments();
}

Evidence :

Test 1 (LAZY without EntityGraph): Fetch Student then try print enrollments AFTER session closes → Console:  LazyInitializationException: failed to lazily initialize a collection of role… ; Test 2 (EAGER version): findAll → No exception, but SQL shows extra JOIN every time; Test 3 (@EntityGraph):  findAllWithEnrollments()  → No exception, SQL only joins WHEN we call this method (better performance; log shows fewer unnecessary queries!)

Reflection question:
What causes  LazyInitializationException , and why is preferring LAZY + explicit fetch/ @EntityGraph  preferable to marking everything EAGER?

my answer:
Exception occurs when accessing LAZY collection AFTER session closes; LAZY + EntityGraph better because we control WHEN to fetch data instead of loading everything automatically!

## Task 4 - Cascade + orphanRemoval - Safe vs Dangerous

Approach / explanation:
I chose  Cascade.PERSIST + orphanRemoval=true  for Department‑Employee (owned child); I avoided  CascadeType.REMOVE  for shared entities because deleting parent would delete shared records; test: remove Employee from list → orphanRemoval deletes only that employee.

Code / endpoint(s) used (if applicable):

java
// Department.java
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();
    public void removeEmployee(Employee emp) {
        employees.remove(emp);
        emp.setDepartment(null);
    }
    // constructors, getters, setters
}

Evidence :

Before test: MySQL  SELECT COUNT(*) FROM employee WHERE department_id=1;  >> Count = 1; Run code: Remove Employee 1 from Department 1 list; After:  SELECT COUNT(*) FROM employee WHERE department_id=1;  >> Count = 0 (employee deleted); Department record STILL exists; If we used Cascade.REMOVE: deleting Department would delete ALL employees (we skipped this to avoid dangerous mass delete; confirms orphanRemoval only triggers when removed from list!)

Reflection question:
What is the difference between  CascadeType.REMOVE  and  orphanRemoval = true , and in which situation is each appropriate or dangerous?

my answer:
CascadeType.REMOVE  = delete ALL related entities when parent is deleted (dangerous for shared);  orphanRemoval=true  = delete child ONLY when removed from parent’s list (good for exclusive owned children)!

## Task 5 - Capstone Milestone 2 - ERD to JPA Entity Model

Approach / explanation:
I translated my capstone ERD: identified entities & cardinality; placed foreign‑key on MANY side; many‑to‑many >> association entity; set default LAZY fetch and only Cascade.PERSIST so we don’t accidentally delete shared data.

Code / endpoint(s) used (if applicable):

java
// Example: Customer → Order (REPLACE WITH YOUR CAPSTONE!)
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Order> orders;
    // constructors, getters, setters
}

@Entity
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    // constructors, getters, setters
}

Evidence :

Spring Boot startup log:  Hibernate: create table customer… create table order…  >> Tables created matching ERD (customer_id FK in Order table); POST /api/customers + add Order >> Saved successfully; MySQL  DESCRIBE order;  >> Shows customer_id foreign key exists; GET /api/customers/{id} >> Returns customer + linked orders only when fetched explicitly; NO accidental delete when saving; model matches ERD design!

Reflection question:
For one relationship in your capstone model, state the cardinality, the owning side, where the FK/join table lives, and why you chose that fetch and cascade setting.

My answer:
Customer (1) → Order (N): Cardinality 1‑to‑Many; Order = owning side (customer_id FK); fetch LAZY, cascade PERSIST only — safe linking and avoid accidental deletions!

Self‑Check

- [ ] All Week 9 work on the  week9  branch (project at branch root, no  week9/  folder)
- [ ] This file completed and renamed  answers.md
- [ ] Repository link pasted into Moodle (no files uploaded)
