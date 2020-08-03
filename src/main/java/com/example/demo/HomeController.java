package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    @RequestMapping("/login")
    public String login(){
        return "login";
    }



//    PUBLIC VIEWS
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/alldepartments")
    public String allDepartments(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "alldepartments";
    }

//    LOGGED IN VIEWS
    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }

    @RequestMapping("/directory")
    public String employeeDirectory(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeesbydepartment";
    }

    @RequestMapping("/employeedetail/{id}")
    public String employeeDetail(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", userRepository.findById(id).get());
        return "employeedetail";
    }


//    ADMIN VIEWS
    @RequestMapping("/admin")
    public String admin(Model model){
        model.addAttribute("employees", userRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin";
    }

    @GetMapping("/newdepartment")
    public String loadDepartmentForm(Model model){
        model.addAttribute("department", new Department());
        return "departmentform";
    }

    @PostMapping("/processdepartment")
    public String processDepartment(@ModelAttribute Department department){
        departmentRepository.save(department);
        return "redirect:/admin";
    }

    @RequestMapping("/updatedepartment/{id}")
    public String updateDepartment(@PathVariable("id") long id, Model model){
        model.addAttribute("department", departmentRepository.findById(id).get());
        return "departmentform";
    }

    @RequestMapping("/deletedepartment/{id}")
    public String deleteDepartment(@PathVariable("id") long id, Model model){
        departmentRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/newemployee")
    public String loadEmployeeForm(Model model){
        model.addAttribute("employee", new User());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeeform";
    }

    @PostMapping("/processemployee")
    public String processEmployee(@Valid @ModelAttribute("employee") User employee, BindingResult result, Model model){

        employee.setEnabled(true);
        userRepository.save(employee);

        Role employeeRole = new Role(employee.getUsername(), "ROLE_USER");
        roleRepository.save(employeeRole);

        return "redirect:/admin";
    }

    @RequestMapping("/updateemployee/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", userRepository.findById(id).get());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeeform";
    }

    @RequestMapping("/toggleemployee/{id}")
    public String toggleEmployee(@PathVariable("id") long id, Model model){
        User employee;
        employee = userRepository.findById(id).get();

        boolean employed = employee.isEnabled();
        employee.setEnabled(!employed);

        userRepository.save(employee);
        return "redirect:/admin";

    }

//    @RequestMapping("/deleteemployee/{id}")
//    public String deleteEmployee(@PathVariable("id") long id, Model model){
//        //get username
//        String username = userRepository.findById(id).get().getUsername();
//
//        //delete from user table
//        userRepository.deleteById(id);
//
//        roleRepository.deleteByUsername(username);
//
//        return "redirect:/admin";
//    }


}
