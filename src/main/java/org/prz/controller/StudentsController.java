/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.prz.dao.ConfirmStudentModel;
import org.prz.dao.SearchStudents;
import org.prz.dao.SearchStudentsAndPageInfo;
import org.prz.dao.StudentDataDao;
import org.prz.dao.StudentRoleDao;
import org.prz.entity.Account;
import org.prz.entity.AccountRole;
import org.prz.entity.Internship;
import org.prz.entity.StudentView;
import org.prz.exception.ModelException;
import org.prz.exception.NotFoundException;
import org.prz.service.StudentsService;
import org.prz.validators.StudentDataDaoValidator;
import org.prz.validators.StudentRoleDaoValidaotr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ola
 */
@SessionAttributes({"searchStudents"})
@Controller
@RequestMapping("/Studenci")
public class StudentsController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StudentDataDaoValidator studentDataDaoValidator;

    @Autowired
    private StudentRoleDaoValidaotr studentRoleDaoValidaotr;

    @Autowired
    private StudentsService service;

    @InitBinder("studentData")
    private void myProfileBinder(WebDataBinder binder) {
        binder.setValidator(studentDataDaoValidator);
    }

    @InitBinder("studentRole")
    private void studentRoleBinder(WebDataBinder binder) {
        binder.setValidator(studentRoleDaoValidaotr);
    }

    @GetMapping("/lista/{pageNumber}")
    public String listOfStudents(@PathVariable Integer pageNumber, ModelMap model) {
        SearchStudents searchValue;
        if (model.containsAttribute("searchStudents")) {
            logger.info("Model zawiera stary atrybus searchStudents");
            searchValue = (SearchStudents) model.get("searchStudents");
        } else {
            searchValue = new SearchStudents();
            logger.info("Model zawiera nowy atrybus searchStudents");
            model.addAttribute("searchStudents", searchValue);
        }
        // Page<Account> page = service.searchUsersQuery(searchValue, pageNumber);
        SearchStudentsAndPageInfo results = service.searchUsersQueryBeta(searchValue, pageNumber);
        List<StudentView> elements = results.getStudenci();
        int current = results.getCurrent();
        int begin = Math.max(1, current - 5);
        int end = results.getEnd();
        //List<Account> elements = page.getContent();
        //List<StudentView> elements = page.getContent();
        model.addAttribute("elements", elements);
        model.addAttribute("totalPages", results.getTotalPages());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        //Potrzebne do POST potwierdzenia danych:
        ConfirmStudentModel confirmStudent = new ConfirmStudentModel();
        model.addAttribute("confirmStudent", confirmStudent);
        return "students/list";
    }

    @PostMapping("/lista/{pageNumber}")
    public String searchElements(@ModelAttribute("searchStudents") final SearchStudents searchStudents) {
        logger.info("Pobieranie danych z formularza");
        return "redirect:../lista/1";
    }

    @GetMapping("/profil/{id}/{pageNumber}")
    public String view(@PathVariable Integer pageNumber, Model model, @PathVariable Integer id) {
        Account account = service.findOneStudent(id);
        if (account == null) {
            logger.error("Konto o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono konta.");
        }
        Page<Internship> page = service.findInternshipsByAccount(id, pageNumber);
        long totalElements = 0;
        if (page != null) {
            totalElements = page.getTotalElements();
            List<Internship> elements = page.getContent();
            model.addAttribute("elements", elements);
            int current = page.getNumber() + 1;
            int begin = Math.max(1, current - 5);
            int end = Math.min(begin + 10, page.getTotalPages());
            model.addAttribute("thePage", page);
            model.addAttribute("beginIndex", begin);
            model.addAttribute("endIndex", end);
            model.addAttribute("currentIndex", current);
            model.addAttribute("totalElements", totalElements);
        }

        model.addAttribute("account", account);
        
        return "students/view";
    }

    @GetMapping("/edycjaRoli/{id}")
    public String editStudentRole(@ModelAttribute("studentRole") StudentRoleDao studentRole,
            BindingResult result, Model model, @PathVariable Integer id) {
        Account account = service.findOneStudent(id);
        if (account == null) {
            logger.error("Konto o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono konta.");
        }
        AccountRole accountRole = service.findStudentAccountRole(id, 3); //rola studenta ma id = 3
        StudentRoleDao oldStudentRole = new StudentRoleDao(accountRole);
        model.addAttribute("studentRole", oldStudentRole);
        model.addAttribute("name", account.getUserProfile().getFirstName());
        model.addAttribute("lastName", account.getUserProfile().getLastName());
        return "students/editRole";
    }

    @PostMapping("/edycjaRoli/{id}")
    public String editStudentRolePost(@ModelAttribute("studentRole") @Valid StudentRoleDao studentRole,
            BindingResult result, Model model, @PathVariable Integer id,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "students/editRole";
        }
        try {
            service.editStudentRole(id, studentRole);
            redirectAttributes.addFlashAttribute("msg", "Operacja na uprawnieniach studenta wykonana pomyślnie.");
            return "redirect:/Studenci/edycjaRoli/" + id;
        } catch (Exception ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", "Błąd w trakcie operacji na danych studenta.");
            return "students/editRole";
        }
    }

    @PostMapping("/potwierdz/{id}")
    public String confirmAccountPost(@PathVariable Integer id,
            final RedirectAttributes redirectAttributes,
            @ModelAttribute("confirmStudent") ConfirmStudentModel confirmStudent) {
        try {
            service.confirm(id, true);
            redirectAttributes.addFlashAttribute("msg", "Potwierdzenie wykonane poprawnie");
            return "redirect:/Studenci/lista/" + confirmStudent.getPageNumber();
        } catch (Exception ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", "Błąd w trakcie potwierdzania");
            return "students/list";
        }
    }

    @PostMapping("/anulujPotwierdzenie/{id}")
    public String withdrawAccountConfirmationPost(@PathVariable Integer id,
            final RedirectAttributes redirectAttributes,
            @ModelAttribute("confirmStudent") ConfirmStudentModel confirmStudent) {
        try {
            service.confirm(id, false);
            redirectAttributes.addFlashAttribute("msg", "Anulowanie potwierdzenia wykonane poprawnie");
            return "redirect:/Studenci/lista/" + confirmStudent.getPageNumber();
        } catch (Exception ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", "Błąd w trakcie anulowania potwierdzania");
            return "students/list";
        }
    }

    //Admin/Opiekun może edytować imię, nazwisko i numer indeksu studenta
    @GetMapping("/edycjaDanych/{id}")
    public String editStudentData(@ModelAttribute("studentData") StudentDataDao studentData,
            BindingResult result, Model model, @PathVariable Integer id) {
        Account account = service.findOneStudent(id);
        if (account == null) {
            logger.error("Konto o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono konta.");
        }
        StudentDataDao oldStudentData = new StudentDataDao(account.getUserProfile());
        model.addAttribute("studentData", oldStudentData);
        return "students/editProfile";
    }

    @PostMapping("/edycjaDanych/{id}")
    public String editStudentDataPost(@ModelAttribute("studentData") @Valid StudentDataDao studentData,
            BindingResult result,
            final RedirectAttributes redirectAttributes,
            @PathVariable Integer id) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "students/editProfile";
        }
        try {
            service.editStudentData(id, studentData);
            redirectAttributes.addFlashAttribute("msg", "Edycja wykonana pomyślnie.");
            return "redirect:/Studenci/profil/" + id + "/1";
        } catch (ModelException ex) {
            result.rejectValue(ex.getField(), ex.getMessage());
            logger.info("Bledy: {}", result.getAllErrors().toString());
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "students/editProfile";
        } catch (Exception ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("errormsg", "Błąd w trakcie edycji danych studenta.");
            return "students/editProfile";
        }
    }
    /*
    @PostMapping("/edycjaProfilu")
    public String editMyProfilePost(@ModelAttribute("myProfile") @Valid MyProfileDao myProfile,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "account/editProfile";
        }
        //Linijka niżej do usunięcia
        logger.info("myProfile.getEmail() {}", myProfile.getEmail());
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account a = (Account) service.getAccountByLogin(customUser.getUsername()).get();
        try {
            logger.info("Edycja profilu o ID {}", a.getAccountId());
            service.editMyProfile(a, myProfile);
            redirectAttributes.addFlashAttribute("msg", "Profil został pomyślnie zapisany.");
            return "redirect:/Konto/profil";
        } catch (ModelException ex) {
            result.rejectValue(ex.getField(), ex.getMessage());
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "account/editProfile";
        }
    }*/

}
