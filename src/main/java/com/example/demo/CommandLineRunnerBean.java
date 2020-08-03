package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public void run(String...strings){

        //DEPARTMENT
        Department sales = new Department();
        sales.setName("Sales");
        departmentRepository.save(sales);

        //USER ROLE
        User user = new User();

        user.setUsername("mc");
        user.setPassword("mc");
        user.setEnabled(true);

        user.setFirstName("Emm");
        user.setLastName("Cee");
        user.setDepartment(sales);
        user.setJobTitle("Cashier");

        Role userRole = new Role("mc", "ROLE_USER");

        userRepository.save(user);
        roleRepository.save(userRole);

        //ADMIN ROLE
        User admin = new User();

        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEnabled(true);

        Role adminRole1 = new Role("admin", "ROLE_USER");
        Role adminRole2 = new Role("admin", "ROLE_ADMIN");

        userRepository.save(admin);
        roleRepository.save(adminRole1);
        roleRepository.save(adminRole2);


    }
}
