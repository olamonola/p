/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import java.util.List;
import javax.validation.Valid;
import org.prz.entity.Company;
import org.prz.exception.ModelException;
import org.prz.exception.NotFoundException;
import org.prz.service.CompanyService;
import org.prz.validators.CompanyValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ola
 */
@Controller
@RequestMapping("/Firma")
public class CompanyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CompanyService service;

    @Autowired
    private CompanyValidator validator;

    @ModelAttribute("company")
    public Company construct() {
        return new Company();
    }

    @InitBinder("company")
    private void companyBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
    
    /**
     * Wyświetlanie formularza dodania nowego systemu.
     *
     * @param company
     * @param result
     * @param model
     * @return Formularz dodania nowej specjalizacji.
     */
    @GetMapping("/nowy")
    public String newForm(@ModelAttribute("company") Company company,
            BindingResult result, ModelMap model) {
        model.addAttribute("company", company);
        return "company/edit";
    }
    
    /**
     * Dodawanie nowej firmy.
     *
     * @param company
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu dodania
     * nowego systemu.
     * @return Przekierowanie na widok dodanego systemu.
     */
    @PostMapping("/nowy")
    public String formSubmit(@ModelAttribute("company") @Valid Company company,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Blad zapisu firmy.");
            // return "system/create";
            return "company/edit";
        }
        try {
            logger.info("Zapis firmy");
            company.setEnabled(true);
            Company s = service.save(company);
            redirectAttributes.addFlashAttribute("msg", "Firma została pomyślnie dodana!");
            return "redirect:widok/" + company.getCompanyId();
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu firmy.");
            redirectAttributes.addFlashAttribute("company", company);
            return "redirect:/Firma/nowy";
        } catch (ModelException ex) {
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu firmy.");
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "company/edit";
        }
    }
    
    /**
     * Wyświetlanie danych firmy.
     *
     * @param model Model ze zmienną reprezentującą dany system.
     * @param id Identyfikator systemu.
     * @return Widok z nazwą systemu.
     */
    @GetMapping("/widok/{id}")
    public String view(Model model, @PathVariable int id) {
        Company company = service.findOne(id);
        if (company == null) {
            logger.error("Firma o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono specjalizacji.");
        }
        model.addAttribute("company", company);
        return "company/view";
    }
    
    @GetMapping("/edycja/{id}")
    public String editForm(Model model, @PathVariable int id) {
        logger.info("Edycja firmy");
        Company company = service.findOne(id);
        if (company == null) {
            logger.error("Firma o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono firmy.");
        }
        model.addAttribute("company", company);
        return "company/edit";
    }
    
     @PostMapping("/edycja/{id}")
    public String editPost(@PathVariable int id,
            @ModelAttribute("company") @Valid Company company,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "company/edit";
        }
        Company oldCompany = service.findOne(id);
        try {
            logger.info("Zapis firmy");
            company.setCompanyId(id);
            company.setEnabled(oldCompany.isEnabled());
            Company f = service.save(company);
            redirectAttributes.addFlashAttribute("msg", "Firma została pomyślnie zmieniona.");
            return "redirect:../widok/" + id;
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji firmy. Firma o podanej nazwie już istnieje.");
            return "redirect:" + id;
        } catch (ModelException ex) {
            result.addError(new FieldError("name", ex.getField(), ex.getMessage()));
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "redirect:" + id;
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji firmy. Firma nie istnieje.");
            return "redirect:../lista/1";
        }
    }
    
     /**
     * Usuwanie systemu.
     *
     * @param id Identyfikator systemu.
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu usunięcia
     * systemu.
     * @return Przekierowanie na listę systemów.
     */
    @RequestMapping(value = "/{id}/usun", method = RequestMethod.POST)
    public String delete(@PathVariable("id") int id,
            final RedirectAttributes redirectAttributes) {
        Company company = service.findOne(id);
        if (company == null) {
            logger.error("Firma o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania firmy. "
                    + "Firma nie mogła zostać usunięta, ponieważ nie istniała.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Usuniecie firmy");
            service.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Firma została usunięta.");
            return "redirect:../lista/1";
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania firmy. "
                    + "Firma nie mogła zostać usunięta, ponieważ nie istniała.");
            return "redirect:../lista/1";
        }
    }
    
    /**
     * Blokada systemu, aby nie był on widoczny dla studentów. Studenci nie mogą
     * wybrać zablokowanego systemu.
     *
     * @param id Identyfikator systemu.
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu blokady
     * systemu.
     * @return Przekierowanie na listę systemów.
     */
    @RequestMapping(value = "/{id}/zablokuj", method = RequestMethod.POST)
    public String disable(@PathVariable("id") int id,
            final RedirectAttributes redirectAttributes) {
        Company company = service.findOne(id);
        if (company == null) {
            logger.error("Firma o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd blokowania fimry. Firma nie mogła zostać zablokowana, ponieważ nie istniała.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis firmy");
            company.setCompanyId(id);
            company.setEnabled(false);
            service.save(company);
            redirectAttributes.addFlashAttribute("msg", "Firma została zablokowana. Studenci nie mogą jej wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd blokady firmy. Firma nie istnieje.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Odblokowanie firmy, aby była ona widoczna dla studentów. Studenci mogą
     * wybrać odblokowaną firmę.
     *
     * @param id Identyfikator systemu.
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu odblokowania
     * systemu.
     * @return Przekierowanie na listę firm.
     */
    @RequestMapping(value = "/{id}/odblokuj", method = RequestMethod.POST)
    public String enable(@PathVariable("id") int id,
            final RedirectAttributes redirectAttributes) {
        Company company = service.findOne(id);
        if (company == null) {
            logger.error("Firma o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd odblokowania firmy. Firma nie mogła zostać odblokowana, ponieważ nie istniała.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis firmy");
            company.setEnabled(true);
            service.save(company);
            redirectAttributes.addFlashAttribute("msg", "Firma została odblokowana. Studenci mogą ją wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd oblokowania firmy. Firma nie istnieje.");
            return "redirect:../lista/1";
        }
    }
    
    /**
     * Wyświetlenie listy systemów, podzielonej na strony
     *
     * @param company
     * @param bindingResult Powiązany rezultat.
     * @param pageNumber Numer obecnej strony.
     * @param model Model do przechowywania zmiennych.
     * @return Lista systemów.
     */
    @GetMapping("/lista/{pageNumber}")
    public String list(@ModelAttribute("search") final Company company,
            final BindingResult bindingResult,
            @PathVariable Integer pageNumber, ModelMap model) {
        model.addAttribute("company", company);
        Page<Company> page = service.getElements(pageNumber, (company.getName() == null ? "" : company.getName()));
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        List<Company> elements = page.getContent();
        model.addAttribute("elements", elements);
        model.addAttribute("thePage", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        return "company/list";
    }
    
    /**
     * Wyszukiwanie firm, które zawierają w sobie podany ciąg znaków.
     *
     * @param company
     * @param bindingResult Powiązany rezultat.
     * @param redirectAttributes Przekazuje szukany system (jego nazwę) do
     * metody list().
     * @return Przekierowanie na listę systemów.
     */
    @PostMapping("/lista/{pageNumber}")
    public String searchElements(@ModelAttribute("search") final Company company,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        logger.info("Post method, company.name = {}", company.getName());
        redirectAttributes.addFlashAttribute("search", company);
        return "redirect:../lista/1";
    }

}
