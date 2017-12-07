/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.prz.CustomUser;
import org.prz.dao.CurrentPage;
import org.prz.dao.Grade;
import org.prz.dao.InternshipArchived;
import org.prz.dao.InternshipConfirmation;
import org.prz.dao.Porozumienie;
import org.prz.dao.SearchInternship;
import org.prz.dao.Sprawozdanie;
import org.prz.dao.SprawozdanieSearch;
import org.prz.dao.StudentDismissed;
import org.prz.dao.Wykaz;
import org.prz.dao.WykazSearch;
import org.prz.entity.Account;
import org.prz.entity.Company;
import org.prz.entity.Faculty;
import org.prz.entity.Internship;
import org.prz.entity.InternshipType;
import org.prz.entity.Specialization;
import org.prz.entity.UserProfile;
import org.prz.exception.ModelException;
import org.prz.exception.NotFoundException;
import org.prz.exception.UnauthorizedException;
import org.prz.service.AccountService;
import org.prz.service.CompanyService;
import org.prz.service.FacultyService;
import org.prz.service.InternshipService;
import org.prz.service.InternshipTypeService;
import org.prz.service.SpecializationService;
import org.prz.service.SystemService;
import org.prz.validators.GradeValidator;
import org.prz.validators.InternshipValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author Ola
 */
@SessionAttributes({"internship", "systems", "faculties", "specializations",
    "internshipTypes", "grade", "searchInternship", "wykazSearch",
    "sprawozdanieSearch"})
@Controller
@RequestMapping("/Praktyka")
public class InternshipController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InternshipService service;

    @Autowired
    private InternshipTypeService internshipTypeService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private InternshipValidator validator;

    @Autowired
    private GradeValidator gradeValidator;

    @ModelAttribute("internship")
    public Internship construct() {
        return new Internship();
    }

    @InitBinder("internship")
    private void internshipBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @InitBinder("grade")
    private void gradeBinder(WebDataBinder binder) {
        binder.setValidator(gradeValidator);
    }

    @GetMapping("/mojePraktyki/{pageNumber}")
    public String studentsMainPage(@PathVariable Integer pageNumber, ModelMap model) {
        //znajduje użytkownika który teraz jest zalogowany
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> account = accountService.getAccountByLogin(customUser.getUsername());
        UserProfile userProfile = account.get().getUserProfile();
        Page<Internship> page = service.findByUser(pageNumber, userProfile);
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        List<Internship> elements = page.getContent();
        model.addAttribute("elements", elements);
        model.addAttribute("thePage", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        return "internship/internship_index";
    }

    /**
     * Wyświetlanie formularza dodania nowej praktyki.
     *
     * @param internship
     * @param result
     * @param model
     * @return Formularz dodania nowej specjalizacji.
     */
    @GetMapping("/nowy")
    public String newForm(@ModelAttribute("internship") Internship internship,
            BindingResult result, ModelMap model) {
        model.addAttribute("internship", internship);
        return "internship/edit";
    }

    @GetMapping("/wybrana_firma/{id}")
    public String wybranaFirma(Model model, @PathVariable int id) {

        Company company = companyService.findOne(id);
        if (company == null) {
            logger.error("Firma o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono firmy.");
        }
        model.addAttribute("internshipTypes", internshipTypeService.findAll());
        model.addAttribute("systems", systemService.findAll());
        model.addAttribute("faculties", facultyService.findAll());
        model.addAttribute("specializations", specializationService.findAll());
        model.addAttribute("internship", new Internship(company));
        return "internship/new";
    }

    @PostMapping("/wybrana_firma/{id}")
    public String formSubmit(@PathVariable int id,
            @ModelAttribute("internship") @Valid Internship internship,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Blad zapisu praktyk.");
            return "internship/new";
        }
        logger.info("Zapis praktyki");
        internship.setConfirmation(false);
        internship.setArchived(false);
        Company c = companyService.findOne(id);
        internship.setCompanyId(c);

        //znajduje użytkownika który teraz jest zalogowany
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> account = accountService.getAccountByLogin(customUser.getUsername());
        if (account == null) {
            return "/";
        } else {
            logger.info("account.id: {}", account.get().getAccountId());
            internship.setAccountId(new UserProfile(account.get().getAccountId()));

            try {
                service.save(internship);
                redirectAttributes.addFlashAttribute("msg", "Praktyka zapisana pomyślnie.");
                return "redirect:/Praktyka/widok/" + internship.getInternshipId();
            } catch (DataIntegrityViolationException e) {
                if (c == null) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd zapisu praktyki. Firma nie istnieje.");
                    logger.error("Firma nie istnieje.");
                } else {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd zapisu praktyki.");
                }
                logger.info("Blad: " + e.toString());
                logger.info("getLocalizedMessage: " + e.getLocalizedMessage());
                logger.info("getMessage: " + e.getMessage());
                return "redirect:/Firma/lista/1";

            } catch (ModelException ex) {
                result.addError(new FieldError("year", ex.getField(), ex.getMessage()));
                result.addError(new FieldError("name", ex.getField(), ex.getMessage()));
                logger.error("Wystąpił wyjątek {}", ex.toString());
                return "redirect:/Firma/lista/1";
            }
        }
    }

    @GetMapping("/widok/{id}")
    public String widokPoPrzekierowaniu(ModelMap model,
            @PathVariable int id,
            HttpServletRequest request) {

        Internship internship = service.findOne(id);
        if (internship == null) {
            logger.error("Praktyka o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono praktyki.");
        }
        CustomUser customUser = (CustomUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        Optional<Account> account = accountService
                .getAccountByLogin(customUser.getUsername());
        if (account != null) {
            if (account.get().getAccountId() == internship
                    .getAccountId().getAccountId()
                    || request.isUserInRole("ROLE_ADMIN")
                    || request.isUserInRole("ROLE_OPIEKUN_PRAKTYK")) {
                model.addAttribute("internship", internship);
                return "internship/view";
            } else {
                model.addAttribute("errormsg", "Brak dostępu.");
            }
        }
        return "internship/view";
    }

    @GetMapping("/edycja/{id}")
    public String editForm(Model model, @PathVariable int id, HttpServletRequest request) {
        Internship internship = service.findOne(id);
        logger.info("internship.getAccountId().getAccountId() {}", internship.getAccountId().getAccountId());

        if (internship == null) {
            logger.error("Praktyka o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono praktyki.");
        }

        CustomUser customUser = (CustomUser) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        Optional<Account> account
                = accountService.getAccountByLogin(customUser.getUsername());
        if (!Objects.equals(account.get().getAccountId(),
                internship.getAccountId().getAccountId())
                || internship.getGrade() != null
                || internship.isConfirmation()) {
            if (!request.isUserInRole("ROLE_ADMIN")
                    || !request.isUserInRole("ROLE_OPIEKUN_PRAKTYK")) {
                logger.error("Praktyka nie powinna być edytowalna poniważ jest"
                        + " ocniona, potwierdzona lub nie należy co ciebie.");
                throw new UnauthorizedException("Brak dostępu.");
            }
        }
        if (account != null) {
            if (account.get().getAccountId().equals(internship.getAccountId().getAccountId())
                    || request.isUserInRole("ROLE_ADMIN")
                    || request.isUserInRole("ROLE_OPIEKUN_PRAKTYK")) {
                model.addAttribute("internship", internship);
                model.addAttribute("internshipTypes", internshipTypeService.findAll());
                model.addAttribute("systems", systemService.findAll());
                model.addAttribute("faculties", facultyService.findAll());
                model.addAttribute("specializations", specializationService.findAll());
            } else if (!account.get().getAccountId().equals(internship.getAccountId().getAccountId())) {
                model.addAttribute("accessDenied", "Brak dostępu.");
            }
        }
        return "internship/new";
    }

    @PostMapping("/edycja/{id}")
    public String editPost(@PathVariable int id,
            @ModelAttribute("internship") @Valid Internship internship,
            BindingResult result,
            final RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "internship/edit";
        }
        logger.info("Nastąpi zapis praktyki");

        Company c = companyService.findOne(id);
        //znajduje użytkownika który teraz jest zalogowany
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> account = accountService.getAccountByLogin(customUser.getUsername());
        if (account == null) {
            logger.info("account == null");
            return "/";
        } else if (account.get().getAccountId() == internship.getAccountId().getAccountId()
                || request.isUserInRole("ROLE_ADMIN")
                || request.isUserInRole("ROLE_OPIEKUN_PRAKTYK")) {
            try {
                service.update(internship);
                redirectAttributes.addFlashAttribute("msg", "Praktyka zmieniona pomyślnie.");
                logger.info("trying....");
                return "redirect:/Praktyka/widok/" + internship.getInternshipId();
            } catch (DataIntegrityViolationException e) {
                if (c == null) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd zapisu praktyki. Firma nie istnieje.");
                    logger.error("Firma nie istnieje.");
                } else {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd zapisu praktyki.");
                }
                logger.error("Blad: " + e.toString());
                return "redirect:/Firma/lista/1";

            } catch (ModelException ex) {
                result.addError(new FieldError("name", ex.getField(), ex.getMessage()));
                logger.error("Wystąpił wyjątek {}", ex.toString());
                return "redirect:/Firma/lista/1";
            }
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "Brak dostępu.");
            logger.info("account.get().getAccountId() != internship.getAccountId().getAccountId()");
            return "internship/edit";
        }
    }

    /*usuwanie praktyki przez studenta*/
    @RequestMapping(value = "/{id}/usun", method = RequestMethod.POST)
    public String delete(@PathVariable("id") int id,
            final RedirectAttributes redirectAttributes) {
        Internship internship = service.findOne(id);
        if (internship == null) {
            logger.error("Praktyka o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("errormsg", "Błąd usuwania praktyki. Praktyka nie mogła zostać usunięta, ponieważ nie istniała.");
            return "redirect:../mojePraktyki/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> account = accountService.getAccountByLogin(customUser.getUsername());
        if (Objects.equals(account.get().getAccountId(), internship.getAccountId().getAccountId())
                && (internship.getGrade() == null && !internship.isConfirmation())) {
            try {
                logger.info("Usuniecie praktyki");
                service.delete(id);
                redirectAttributes.addFlashAttribute("msg", "Praktyka została usunięta.");
                return "redirect:../mojePraktyki/1";
            } catch (EmptyResultDataAccessException ex) {
                logger.error("Wystąpił wyjątek {}", ex.toString());
                redirectAttributes.addFlashAttribute("errormsg", "Błąd usuwania praktyki. Praktyka nie mogła zostać usunięta, ponieważ nie istniała.");
                return "redirect:../mojePraktyki/1";
            }
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "Błąd usuwania praktyki. Praktyka nie mogła zostać usunięta, ponieważ nie należy do ciebie, jest już potwierdzona lub oceniona.");
            return "redirect:../mojePraktyki/1";
        }
    }

    @GetMapping("/umowaPraktyk/{id}")
    public ModelAndView umowaPraktyk(@PathVariable Integer id,
            ModelMap modelMap,
            ModelAndView modelAndView) {
        List<Porozumienie> daneNaPorozumienie = new ArrayList<>();
        Internship i = service.findOne(id);
        if (i != null) {
            //znajduje użytkownika który teraz jest zalogowany
            CustomUser customUser = (CustomUser) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Optional<Account> account = accountService.getAccountByLogin(customUser.getUsername());
            UserProfile userProfile = account.get().getUserProfile();

            Porozumienie porozumienie = service.setAgreement(i);
            if (Objects.equals(userProfile.getAccountId(),
                    service.findOne(id).getAccountId().getAccountId())) {
                daneNaPorozumienie.add(porozumienie);
                daneNaPorozumienie.add(porozumienie);
                JRDataSource jRdataSource = new JRBeanCollectionDataSource(daneNaPorozumienie);
                modelMap.put("datasource", jRdataSource);
                modelMap.put("format", "pdf");
                //modelAndView = new ModelAndView("r_test2", modelMap);
                modelAndView = new ModelAndView("r_umowa", modelMap);
                return modelAndView;
            } else {
                modelAndView = new ModelAndView("/error", modelMap);
                modelAndView.addObject("msg", "Odmowa dostępu.");
                return modelAndView;
            }
        } else {
            modelAndView = new ModelAndView("/error", modelMap);
            modelAndView.addObject("msg", "Praktyka, której dotyczy umowa, nie istnieje.");
            return modelAndView;
        }
    }

    @GetMapping("/sprawozdaniePDF")
    public ModelAndView sprawozdaniePDF(ModelMap modelMap, ModelAndView modelAndView) {
        logger.info("SPRAWOZDANIE Opiekuna Praktyk Studenckich z przebiegu realizacji praktyki");
        SprawozdanieSearch spr = new SprawozdanieSearch();
        if (modelMap.containsAttribute("sprawozdanieSearch")) {
            spr = (SprawozdanieSearch) modelMap.get("sprawozdanieSearch");
        }
        ArrayList<Sprawozdanie> result = service.listaSprawozdaniePDF(spr);
        JRDataSource jRdataSource = new JRBeanCollectionDataSource(result);
        modelMap.put("datasource", jRdataSource);
        modelMap.put("format", "pdf");
        modelAndView = new ModelAndView("r_sprawozdanie", modelMap);
        return modelAndView;
    }

    @GetMapping("/sprawozdanieCSV")
    public void sprawozdanieCSV(HttpServletResponse response, ModelMap modelMap) throws IOException {

        String csvFileName = "sprawozdanie.csv";
        response.setContentType("text/csv;charset=utf-8");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                csvFileName);
        response.setHeader(headerKey, headerValue);
        SprawozdanieSearch spr = new SprawozdanieSearch();
        if (modelMap.containsAttribute("sprawozdanieSearch")) {
            spr = (SprawozdanieSearch) modelMap.get("sprawozdanieSearch");
        }
        ArrayList<Sprawozdanie> result = service.listaSprawozdaniePDF(spr);
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE)) {
            String[] header = {"kierunek", "specjalnosc", "system", "rokStudiow",
                "nazwaPraktyki", "dlugoscPraktyki", "opiekun", "studenciNaPraktykach",
                "studenciZwolnieni", "studenciOdwolani", "uwagi", "lp", "instytucja",
                "studenciWInstytucji"};
            csvWriter.writeHeader(header);
            for (Sprawozdanie aSprawozdanie : result) {
                csvWriter.write(aSprawozdanie, header);
            }
        }
    }

    @GetMapping("/wykazPDF")
    public ModelAndView wykazPDF(ModelMap modelMap, ModelAndView modelAndView) {
        logger.info("Czy znajduje się tu atrybus sesji wykazSearch? {}", modelMap.containsAttribute("wykazSearch"));
        WykazSearch w = new WykazSearch();
        if (modelMap.containsAttribute("wykazSearch")) {
            w = (WykazSearch) modelMap.get("wykazSearch");
            logger.info("getInternshipType {}", w.getInternshipType());
        }
        List<Internship> result = service.wykazDaneDoPDF(w);
        ArrayList<Wykaz> wykazy = service.listaWykaz(result, w);
        JRDataSource jRdataSource = new JRBeanCollectionDataSource(wykazy);
        modelMap.put("datasource", jRdataSource);
        modelMap.put("format", "pdf");
        WykazSearch nowy = (WykazSearch) modelMap.get("wykazSearch");
        nowy.setSpecializations(null);
        modelMap.addAttribute("wykazSearch", nowy);
        modelAndView = new ModelAndView("r_wykaz_studentow_beta", modelMap);
        return modelAndView;
    }

    /*Lista praktyk widziana przez opiekuna praktyk*/
    @GetMapping("/listaPraktyk/{pageNumber}")
    public String internshipRegister(@PathVariable Integer pageNumber,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "schoolyear", required = false) Integer schoolyear,
            @RequestParam(name = "specId", required = false) Integer specId,
            ModelMap model) {
        Specialization specializationFromParam;
        SearchInternship searchValue;
        SearchInternship a = (SearchInternship) model.get("searchInternship");
        if (model.containsAttribute("searchInternship") && a.getFirstName() != null) {
            logger.info("searchInternship jest w sesji i nie jest nullem");
            searchValue = (SearchInternship) model.get("searchInternship");
            logger.info("W ifie searchValue.getLastName {}", searchValue.getLastName());
        } else {
            searchValue = new SearchInternship();
            logger.info("W else searchValue.getLastName {}", searchValue.getLastName());
            model.addAttribute("searchInternship", searchValue);
        }
        logger.info("Poza ifami searchValue.getLastName {}", searchValue.getLastName());
        if (specId != null) {
            specializationFromParam = specializationService.findOne(specId);
        } else {
            specializationFromParam = null;
        }
        Page<Internship> page;
        if (year != null && schoolyear != null) {
            page = service.internshipsRegister(pageNumber, schoolyear, year, specializationFromParam, searchValue);
            logger.info("page.getTotalElements() {}", page.getTotalElements());

            int current = page.getNumber() + 1;
            int begin = Math.max(1, current - 5);
            int end = Math.min(begin + 10, page.getTotalPages());
            List<Internship> elements = page.getContent();
            List<BigDecimal> grades = new ArrayList<BigDecimal>();
            grades.add(BigDecimal.valueOf(5.0));
            grades.add(BigDecimal.valueOf(4.5));
            grades.add(BigDecimal.valueOf(4.0));
            grades.add(BigDecimal.valueOf(3.5));
            grades.add(BigDecimal.valueOf(3.0));
            grades.add(BigDecimal.valueOf(2.0));
            model.addAttribute("grades", grades);
            model.addAttribute("elements", elements);
            model.addAttribute("thePage", page);
            model.addAttribute("beginIndex", begin);
            model.addAttribute("endIndex", end);
            model.addAttribute("currentIndex", current);
        } else if (year == null && schoolyear == null) {
            page = service.internshipsSearchByStudent(pageNumber, searchValue);
            if (page != null) {
                logger.info("page.getTotalElements() {}", page.getTotalElements());
                int current = page.getNumber() + 1;
                int begin = Math.max(1, current - 5);
                int end = Math.min(begin + 10, page.getTotalPages());
                List<Internship> elements = page.getContent();
                List<BigDecimal> grades = new ArrayList<BigDecimal>();
                grades.add(BigDecimal.valueOf(5.0));
                grades.add(BigDecimal.valueOf(4.5));
                grades.add(BigDecimal.valueOf(4.0));
                grades.add(BigDecimal.valueOf(3.5));
                grades.add(BigDecimal.valueOf(3.0));
                grades.add(BigDecimal.valueOf(2.0));
                model.addAttribute("grades", grades);
                model.addAttribute("elements", elements);
                model.addAttribute("thePage", page);
                model.addAttribute("beginIndex", begin);
                model.addAttribute("endIndex", end);
                model.addAttribute("currentIndex", current);
                if (page.getTotalElements() == 0 && searchValue.getLastName() != null) {
                    logger.info("page == null && searchValue.getLastName() == null");
                    model.addAttribute("errormsg", "Brak wyników wyszukiwania");
                }
            }
        }

        model.addAttribute("allSchoolYears", service.allSchoolYears());
        model.addAttribute("specializations", specializationService.findEnabledAndDisabled());
        Grade grade = new Grade();
        model.addAttribute("grade", grade);
        InternshipConfirmation internshipConfirmation = new InternshipConfirmation();
        model.addAttribute("internshipConfirmation", internshipConfirmation);
        InternshipArchived internshipArchived = new InternshipArchived();
        model.addAttribute("internshipArchived", internshipArchived);
        StudentDismissed studentDismissed = new StudentDismissed();
        model.addAttribute("studentDismissed", studentDismissed);
        CurrentPage currentPage = new CurrentPage();
        model.addAttribute("currentPage", currentPage);

        //tw dwie linie miały czyścić parametr searchInternship po wyszukiwaniu (żeby formularz wyszukiwania był pusty po wyszukaniu), ale zamiast tego jest przycisk czyszczący formularz
        //model.replace("searchInternship", new SearchInternship());
        //logger.info("model.replace(\"searchInternship\", new SearchInternship());");
        return "internship/teachers_list";
    }

    @PostMapping(path = "/listaPraktyk/{pageNumber}", params = {"search", "!erase"})
    public String searchElements(@ModelAttribute("searchInternship") final SearchInternship searchInternship) {
        /*Sprawdzam parametry z linku wysłane formularzem szukania w modelu searchInternship. 
        SearchInternship extends CurrentPage, gdzie są właśnie pola parametrów (schoolyear, year, specId)*/
        if (searchInternship.getYear() != null && searchInternship.getSchoolyear() != null) {
            if (searchInternship.getSpecId() != null) {
                return "redirect:../listaPraktyk/1?year=" + searchInternship.getYear()
                        + "&schoolyear=" + searchInternship.getSchoolyear()
                        + "&specId=" + searchInternship.getSpecId();
            } else {
                return "redirect:../listaPraktyk/1?year=" + searchInternship.getYear()
                        + "&schoolyear=" + searchInternship.getSchoolyear();
            }
        } else {
            return "redirect:../listaPraktyk/1";
        }
    }

    @PostMapping(path = "/listaPraktyk/{pageNumber}", params = {"!search", "erase"})
    public String eraseSearchForm(@ModelAttribute("searchInternship") final SearchInternship searchInternship) {
        logger.info(" eraseSearchForm");
        logger.info("POST searchInternship.getLastName {}", searchInternship.getLastName());
        /*Sprawdzam parametry z linku wysłane formularzem szukania w modelu searchInternship. 
        SearchInternship extends CurrentPage, gdzie są właśnie pola parametrów (schoolyear, year, specId)*/
        Integer schoolyear = searchInternship.getSchoolyear();
        Integer year = searchInternship.getYear();
        Integer specId = searchInternship.getSpecId();
        searchInternship.setFirstName(null);
        searchInternship.setLastName(null);
        searchInternship.setInternshipId(null);
        searchInternship.setPageNumber(null);
        searchInternship.setSchoolyear(null);
        searchInternship.setSpecId(null);
        searchInternship.setYear(null);
        if (year != null && schoolyear != null) {
            if (specId != null) {
                return "redirect:../listaPraktyk/1?year=" + year
                        + "&schoolyear=" + schoolyear
                        + "&specId=" + specId;
            } else {
                return "redirect:../listaPraktyk/1?year=" + year
                        + "&schoolyear=" + schoolyear;
            }
        } else {
            return "redirect:../listaPraktyk/1";
        }
    }

    private String przekierowaniaWLisciePraktyk(Grade grade) {
        if (grade.getInternshipId() != null || grade.getInternshipId() != 0) {
            if (grade.getSpecId() == null && grade.getYear() == null && grade.getSchoolyear() == null) {
                return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber();
            }
            if (grade.getSpecId() == null) {
                return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear();
            } else {
                return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear() + "&specId=" + grade.getSpecId();
            }
        }
        else return "redirect:/Praktyka/listaPraktyk/1";
    }

    @PostMapping("/ocen")
    public String evaluateInternship(@ModelAttribute("grade") @Valid Grade grade,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        logger.info("grade.grade {}", grade.getGrade());
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            redirectAttributes.addFlashAttribute("errormsg", "Wystąpił błąd. Sprawdź, czy wybrana została ocena.");
            return przekierowaniaWLisciePraktyk(grade);
        }
        logger.info("Nastąpi zapis praktyki");
        if (grade.getInternshipId() != null || grade.getInternshipId() != 0) {
            Internship i = service.findOne(grade.getInternshipId());
            if (i != null) {
                try {
                    i.setGrade(grade.getGrade());
                    service.update(i);
                    redirectAttributes.addFlashAttribute("msg", "Praktyka oceniona poprawnie.");
                    if (grade.getSpecId() == null && grade.getYear() == null && grade.getSchoolyear() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber();
                    }
                    if (grade.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear() + "&specId=" + grade.getSpecId();
                    }

                } catch (DataIntegrityViolationException e) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd oceny praktyki.");
                    logger.error("Blad: " + e.toString());
                    if (grade.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear() + "&specId=" + grade.getSpecId();
                    }
                } catch (ModelException ex) {
                    result.addError(new FieldError("name", ex.getField(), ex.getMessage()));
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd oceny praktyki.");
                    logger.error("Wystąpił wyjątek {}", ex.toString());
                    if (grade.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear() + "&specId=" + grade.getSpecId();
                    }
                }
            } else {
                redirectAttributes.addFlashAttribute("errormsg", "Operacja nie powiodła się. Praktyka nie istnieje.");
                if (grade.getSpecId() == null) {
                    return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear();
                } else {
                    return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear() + "&specId=" + grade.getSpecId();
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "Operacja nie powiodła się. Praktyka nie istnieje.");
            if (grade.getSpecId() == null) {
                return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear();
            } else {
                return "redirect:/Praktyka/listaPraktyk/" + grade.getPageNumber() + "?year=" + grade.getYear() + "&schoolyear=" + grade.getSchoolyear() + "&specId=" + grade.getSpecId();
            }
        }
    }

    @PostMapping("/potwierdz")
    public String confirmInternship(@ModelAttribute("internshipConfirmation") InternshipConfirmation internshipConfirmation,
            final RedirectAttributes redirectAttributes) {
        /*if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "internship/teachers_list";
        }*/
        logger.info("Nastąpi zapis praktyki");
        logger.info("internshipConfirmation.getConfirmation() {}", internshipConfirmation.getConfirmation());
        logger.info("internshipConfirmation.getInternshipId() {}", internshipConfirmation.getInternshipId());
        logger.info("internshipConfirmation.getSchoolyear()() {}", internshipConfirmation.getSchoolyear());
        logger.info("internshipConfirmation.getYear() {}", internshipConfirmation.getYear());
        logger.info("internshipConfirmation.getSpecId() {}", internshipConfirmation.getSpecId());
        logger.info("internshipConfirmation.getPageNumber() {}", internshipConfirmation.getPageNumber());
        if (internshipConfirmation.getInternshipId() != null || internshipConfirmation.getInternshipId() != 0) {
            Internship i = service.findOne(internshipConfirmation.getInternshipId());
            if (i != null) {
                try {
                    if (internshipConfirmation.getConfirmation() != null) {
                        i.setConfirmation(!internshipConfirmation.getConfirmation());
                    } else {
                        i.setConfirmation(true);
                    }
                    service.update(i);
                    redirectAttributes.addFlashAttribute("msg", "Operacja powiodła się.");
                    if (internshipConfirmation.getSpecId() == null
                            && internshipConfirmation.getYear() == null
                            && internshipConfirmation.getSchoolyear() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber();
                    }
                    if (internshipConfirmation.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear() + "&specId=" + internshipConfirmation.getSpecId();
                    }

                } catch (DataIntegrityViolationException e) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd potwierdzenia praktyki.");
                    logger.error("Blad: " + e.toString());
                    if (internshipConfirmation.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear() + "&specId=" + internshipConfirmation.getSpecId();
                    }
                } catch (ModelException ex) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd potwierdzenia praktyki.");
                    logger.error("Wystąpił wyjątek {}", ex.toString());
                    if (internshipConfirmation.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear() + "&specId=" + internshipConfirmation.getSpecId();
                    }
                }
            } else {
                redirectAttributes.addFlashAttribute("errormsg", "Operacja nie powiodła się. Praktyka nie istnieje.");
                if (internshipConfirmation.getSpecId() == null) {
                    return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear();
                } else {
                    return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear() + "&specId=" + internshipConfirmation.getSpecId();
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "Operacja nie powiodła się. Praktyka nie istnieje.");
            if (internshipConfirmation.getSpecId() == null) {
                return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear();
            } else {
                return "redirect:/Praktyka/listaPraktyk/" + internshipConfirmation.getPageNumber() + "?year=" + internshipConfirmation.getYear() + "&schoolyear=" + internshipConfirmation.getSchoolyear() + "&specId=" + internshipConfirmation.getSpecId();
            }
        }
    }

    @PostMapping("/odwolanie")
    public String dismissFromInternship(@ModelAttribute("studentDismissed") StudentDismissed studentDismissed,
            final RedirectAttributes redirectAttributes) {
        if (studentDismissed.getInternshipId() != null || studentDismissed.getInternshipId() != 0) {
            Internship i = service.findOne(studentDismissed.getInternshipId());
            if (i != null) {
                try {
                    if (studentDismissed.getDismissed() != null) {
                        i.setDismissed(!studentDismissed.getDismissed());
                    } else {
                        i.setDismissed(true);
                    }
                    service.update(i);
                    redirectAttributes.addFlashAttribute("msg", "Operacja powiodła się.");
                    if (studentDismissed.getSpecId() == null
                            && studentDismissed.getYear() == null
                            && studentDismissed.getSchoolyear() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber();
                    }
                    if (studentDismissed.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear() + "&specId=" + studentDismissed.getSpecId();
                    }

                } catch (DataIntegrityViolationException e) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd operacji odwoływania.");
                    logger.error("Blad: " + e.toString());
                    if (studentDismissed.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear() + "&specId=" + studentDismissed.getSpecId();
                    }
                } catch (ModelException ex) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd operacji odwoływania.");
                    logger.error("Wystąpił wyjątek {}", ex.toString());
                    if (studentDismissed.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear() + "&specId=" + studentDismissed.getSpecId();
                    }
                }
            } else {
                redirectAttributes.addFlashAttribute("errormsg", "Operacja nie powiodła się. Praktyka nie istnieje.");
                if (studentDismissed.getSpecId() == null) {
                    return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear();
                } else {
                    return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear() + "&specId=" + studentDismissed.getSpecId();
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "Operacja nie powiodła się. Praktyka nie istnieje.");
            if (studentDismissed.getSpecId() == null) {
                return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear();
            } else {
                return "redirect:/Praktyka/listaPraktyk/" + studentDismissed.getPageNumber() + "?year=" + studentDismissed.getYear() + "&schoolyear=" + studentDismissed.getSchoolyear() + "&specId=" + studentDismissed.getSpecId();
            }
        }
    }

    @PostMapping("/archiwum")
    public String archiveInternship(@ModelAttribute("internshipArchived") InternshipArchived internshipArchived,
            final RedirectAttributes redirectAttributes) {
        logger.info("Nastąpi zapis praktyki");
        if (internshipArchived.getInternshipId() != null || internshipArchived.getInternshipId() != 0) {
            Internship i = service.findOne(internshipArchived.getInternshipId());
            if (i != null) {
                try {
                    if (internshipArchived.getArchived() != null) {
                        i.setArchived(!internshipArchived.getArchived());
                    } else {
                        i.setArchived(true);
                    }
                    service.update(i);
                    redirectAttributes.addFlashAttribute("msg", "Operacja powiodła się.");
                    if (internshipArchived.getSpecId() == null
                            && internshipArchived.getYear() == null
                            && internshipArchived.getSchoolyear() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber();
                    }
                    if (internshipArchived.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear() + "&specId=" + internshipArchived.getSpecId();
                    }

                } catch (DataIntegrityViolationException e) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd archiwizowania praktyki.");
                    logger.error("Blad: " + e.toString());
                    if (internshipArchived.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear() + "&specId=" + internshipArchived.getSpecId();
                    }
                } catch (ModelException ex) {
                    redirectAttributes.addFlashAttribute("errormsg", "Błąd archiwizowania praktyki.");
                    logger.error("Wystąpił wyjątek {}", ex.toString());
                    if (internshipArchived.getSpecId() == null) {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear();
                    } else {
                        return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear() + "&specId=" + internshipArchived.getSpecId();
                    }
                }
            } else {
                redirectAttributes.addFlashAttribute("errormsg", "Operacja nie powiodła się. Praktyka nie istnieje.");
                if (internshipArchived.getSpecId() == null) {
                    return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear();
                } else {
                    return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear() + "&specId=" + internshipArchived.getSpecId();
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "Operacja nie powiodła się. Praktyka nie istnieje.");
            if (internshipArchived.getSpecId() == null) {
                return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear();
            } else {
                return "redirect:/Praktyka/listaPraktyk/" + internshipArchived.getPageNumber() + "?year=" + internshipArchived.getYear() + "&schoolyear=" + internshipArchived.getSchoolyear() + "&specId=" + internshipArchived.getSpecId();
            }
        }
    }

    /*Usuwanie przez administratora lub opiekuna praktyk. Usuwanie przez studenta jest zrobione osobno*/
    @RequestMapping(value = "/{id}/usunPraktyke", method = RequestMethod.POST)
    public String deleteByAdmin(@PathVariable("id") int id,
            final RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            @ModelAttribute("currentPage") CurrentPage currentPage) {
        Internship internship = service.findOne(id);
        if (internship == null) {
            logger.error("Praktyka o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("errormsg", "Błąd usuwania praktyki. Praktyka nie mogła zostać usunięta, ponieważ nie istniała.");
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        if ((request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_OPIEKUN_PRAKTYK")) && internship.getArchived()) {
            try {
                logger.info("Usuniecie praktyki");
                service.delete(id);
                redirectAttributes.addFlashAttribute("msg", "Praktyka została usunięta.");

            } catch (EmptyResultDataAccessException ex) {
                logger.error("Wystąpił wyjątek {}", ex.toString());
                redirectAttributes.addFlashAttribute("errormsg", "Błąd usuwania praktyki. Praktyka nie mogła zostać usunięta, ponieważ nie istniała.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errormsg", "Błąd usuwania praktyki. Praktyka nie mogła zostać usunięta.");
        }
        if (currentPage.getSpecId() == null
                && currentPage.getYear() == null
                && currentPage.getSchoolyear() == null) {
            return "redirect:/Praktyka/listaPraktyk/" + currentPage.getPageNumber();
        }
        if (currentPage.getSpecId() == null) {
            return "redirect:/Praktyka/listaPraktyk/1?year=" + currentPage.getYear() + "&schoolyear=" + currentPage.getSchoolyear();
        } else {
            return "redirect:/Praktyka/listaPraktyk/1?year=" + currentPage.getYear() + "&schoolyear=" + currentPage.getSchoolyear() + "&specId=" + currentPage.getSpecId();
        }
    }

    /*
    @GetMapping("/listaPraktyk/{pageNumber}")
    public String internshipList(@PathVariable Integer pageNumber, ModelMap model) {
        Page<Internship> internList = service.findInternships(pageNumber, null);
        model.addAttribute("allSchoolYears", service.allSchoolYears());
        model.addAttribute("specializations", specializationService.findAll());
        logger.info("internList.getTotalElements(){}", internList.getTotalElements());
        return "internship/teachers_list";
    }*/
 /*Pomocnicza funkcja, zwraca listę lat: 1,2,3,4*/
    List<Integer> yearsOneToFour() {
        ArrayList<Integer> years = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++) {
            years.add(i);
        }
        return years;
    }

    @GetMapping("/sprawozdanie/{pageNumber}")
    public String sprawozdanie(@PathVariable Integer pageNumber, ModelMap model) {
        SprawozdanieSearch searchValue = new SprawozdanieSearch();
        List<Faculty> faculties = facultyService.findBothEnabledAndBisabled();
        List<Specialization> specializations = specializationService.findBothDisabledAndEnabled();
        List<org.prz.entity.System> systems = systemService.findBothEnabledAndDisabled();
        List<Integer> years = this.yearsOneToFour();
        List<InternshipType> internshipTypes = internshipTypeService.findBothDisabledAndEnabled();
        List<Integer> schoolYears = service.allSchoolYears();
        if (model.containsAttribute("sprawozdanieSearch")) {
            logger.info("Model zawiera stary atrybut sprawozdanieSearch");
            searchValue = (SprawozdanieSearch) model.get("sprawozdanieSearch");
        } else {
            searchValue = new SprawozdanieSearch();
            logger.info("Model zawiera nowy atrybut sprawozdanieSearch");
            //ustawienie wartości początkowych/defaultowych wyszukiwania
            searchValue.setInternshipTypeId(internshipTypes.get(0).getInternshipTypeId());
            searchValue.setSchoolYear(schoolYears.get(0));
            searchValue.setSystemId(systems.get(0).getSystemId());
            searchValue.setFacultyId(faculties.get(0).getFacultyId());
            searchValue.setSpecializationId(specializations.get(0).getSpecializationId());
            searchValue.setYear(years.get(0));
            model.addAttribute("sprawozdanieSearch", searchValue);
        }
        model.addAttribute("faculties", faculties);
        model.addAttribute("specializations", specializations);
        model.addAttribute("systems", systems);
        model.addAttribute("years", years);
        model.addAttribute("internshipTypes", internshipTypes);
        model.addAttribute("schoolYears", schoolYears);
        Page<Object[]> result = service.sprawozdanie(pageNumber, searchValue);
        List<Object[]> elements = result.getContent();
        int current = result.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, result.getTotalPages());
        model.addAttribute("elements", elements);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentIndex", current);
        return "internship/raport_sprawozdanie";
    }

    @PostMapping(path = "/sprawozdanie/{pageNumber}", params = {"search", "!generatePDF", "!export"})
    public String reportSprawozdanieSearch(@ModelAttribute("sprawozdanieSearch") final SprawozdanieSearch sprawozdanieSearch) {
        logger.info("Pobieranie danych z formularza");
        return "redirect:../sprawozdanie/1";
    }

    @PostMapping(path = "/sprawozdanie/{pageNumber}", params = {"!search", "generatePDF", "!export"})
    public String reportSprawozdaniePDF(@ModelAttribute("sprawozdanieSearch") final SprawozdanieSearch sprawozdanieSearch) {
        logger.info("Pobieranie danych z formularza");
        return "redirect:../sprawozdaniePDF";
    }

    @PostMapping(path = "/sprawozdanie/{pageNumber}", params = {"!search", "!generatePDF", "export"})
    public String reportSprawozdanieCSV(@ModelAttribute("sprawozdanieSearch") final SprawozdanieSearch sprawozdanieSearch) {
        logger.info("Pobieranie danych z formularza");
        return "redirect:../sprawozdanieCSV";
    }

    @GetMapping("/wykaz/{pageNumber}")
    public String reportStudentsInCompanies(@PathVariable Integer pageNumber, ModelMap model) {
        WykazSearch searchValue;
        List<InternshipType> internshipTypes = internshipTypeService.findBothDisabledAndEnabled();
        List<Integer> schoolYears = service.allSchoolYears();
        List<org.prz.entity.System> systems = systemService.findBothEnabledAndDisabled();
        if (model.containsAttribute("wykazSearch")) {
            logger.info("Model zawiera stary atrybut wykazSearch");
            searchValue = (WykazSearch) model.get("wykazSearch");
        } else {
            searchValue = new WykazSearch();
            logger.info("Model zawiera nowy atrybut wykazSearch");
            //ustawienie wartości początkowych/defaultowych wyszukiwania
            searchValue.setInternshipType(internshipTypes.get(0).getInternshipTypeId());
            searchValue.setSchoolYear(schoolYears.get(0));
            searchValue.setSystem(systems.get(0).getSystemId());
            model.addAttribute("wykazSearch", searchValue);
        }
        Page<Internship> result = service.wykaz(pageNumber, searchValue);
        List<Internship> elements = result.getContent();
        int current = result.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, result.getTotalPages());
        model.addAttribute("elements", elements);
        model.addAttribute("thePage", result);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentIndex", current);
        model.addAttribute("internshipTypesForReport", internshipTypes);
        model.addAttribute("allSchoolYearsForReport", schoolYears);
        model.addAttribute("allSpecializations", specializationService.findBothDisabledAndEnabled());
        model.addAttribute("preselectedSepcializations", service.specializationsInWykazResult(searchValue));
        ArrayList<String> selectedSpecializations = new ArrayList<String>();
        model.addAttribute("selectedSpecializations", selectedSpecializations);
        model.addAttribute("systems", systems);
        return "internship/raport_wykaz_studentow";
    }

    @PostMapping(path = "/wykaz/{pageNumber}", params = {"search", "!generatePDF", "!export"})
    public String reportStudentsInCompaniesSearch(@ModelAttribute("wykazSearch") final WykazSearch wykazSearch) {
        logger.info("Pobieranie danych z formularza");
        return "redirect:../wykaz/1";
    }

    @PostMapping(path = "/wykaz/{pageNumber}", params = {"!search", "generatePDF", "!export"})
    public String reportStudentsInCompaniesPDF(@ModelAttribute("wykazSearch") final WykazSearch wykazSearch) {
        logger.info("Pobieranie danych z formularza");
        return "redirect:../wykazPDF";
    }

    @PostMapping(path = "/wykaz/{pageNumber}", params = {"!search", "!generatePDF", "export"})
    public String reportStudentsInCompaniesCSV(@ModelAttribute("wykazSearch") final WykazSearch wykazSearch) {
        logger.info("Pobieranie danych z formularza");
        return "redirect:../wykazCSV";
    }

    @GetMapping("/wykazCSV")
    public void wykazCSV(HttpServletResponse response, ModelMap modelMap)
            throws IOException {

        String csvFileName = "wykaz.csv";
        response.setContentType("text/csv;charset=utf-8");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                csvFileName);
        response.setHeader(headerKey, headerValue);
        WykazSearch wykazS = new WykazSearch();
        if (modelMap.containsAttribute("wykazSearch")) {
            logger.info("Model zawiera atrybut wykazSearch");
            wykazS = (WykazSearch) modelMap.get("wykazSearch");
        }
        List<Internship> result = service.wykazDaneDoPDF(wykazS);
        ArrayList<Wykaz> wykazy = service.listaWykaz(result, wykazS);
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE)) {
            String[] header = {"kierunek", "system", "nazwaPraktyki",
                "czasPraktyki", "opiekunUczelniany", "lp", "specializations",
                "student", "firma", "opiekunFirmowy", "termin"};
            csvWriter.writeHeader(header);
            for (Wykaz aWykaz : wykazy) {
                csvWriter.write(aWykaz, header);
            }
        }

        //po skończeniu tej metody specjalizacje znów będą puste
        WykazSearch nowy = (WykazSearch) modelMap.get("wykazSearch");
        nowy.setSpecializations(null);
        modelMap.addAttribute("wykazSearch", nowy);
    }

}
