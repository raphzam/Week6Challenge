package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }

//    ADMIN VIEWS
    @RequestMapping("/admin")
    public String admin(Model model){
        model.addAttribute("employees", userRepository.findAll());
        return "admin";
    }

    @GetMapping("/newemployee")
    public String loadEmployeeForm(Model model){
        model.addAttribute("employee", new User());
        return "employeeform";
    }

    @PostMapping("/processemployee")
    public String processEmployee(@Valid @ModelAttribute("employee") User employee, BindingResult result, Model model){

        employee.setEnabled(true);
        userRepository.save(employee);

        Role employeeRole = new Role(employee.getUsername(), "ROLE_USER");
        roleRepository.save(employeeRole);
        return "employeeconfirmation";
    }


}
