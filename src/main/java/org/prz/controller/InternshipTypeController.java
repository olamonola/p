/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import java.util.List;
import javax.validation.Valid;
import org.prz.entity.InternshipType;
import org.prz.exception.ModelException;
import org.prz.exception.NotFoundException;
import org.prz.service.InternshipTypeService;
import org.prz.validators.InternshipTypeValidator;
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
@RequestMapping("/TypPraktyk")
public class InternshipTypeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InternshipTypeService internshipTypeService;

    @Autowired
    private InternshipTypeValidator internshipTypeValidator;

    @ModelAttribute("internshipType")
    public InternshipType construct() {
        return new InternshipType();
    }

    @InitBinder("internshipType")
    private void internshipTypeBinder(WebDataBinder binder) {
        binder.setValidator(internshipTypeValidator);
    }

    /**
     * Wyświetlanie formularza edycji typu praktyk
     *
     * @param model Model przechowujący zmienne
     * @param id Identyfikator typu praktyk
     * @return Widok formularza
     */
    @GetMapping("/edycja/{id}")
    public String editForm(Model model, @PathVariable int id) {
        InternshipType internshipType = internshipTypeService.findOne(id);
        if (internshipType == null) {
            logger.error("Typ praktyk o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono typu praktyk.");
        }
        model.addAttribute("internshipType", internshipType);
        return "internshipType/edit";
    }

    /**
     * Edycja nazwy typu praktyk
     *
     * @param id Identyfikator typu praktyk
     * @param internshipType
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu edycji
     * typu praktyk
     * @return
     */
    @PostMapping("/edycja/{id}")
    public String editPost(@PathVariable int id,
            @ModelAttribute("internshipType") @Valid InternshipType internshipType,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "internshipType/edit";
        }
        InternshipType oldInternshipType = internshipTypeService.findOne(id);
        try {
            logger.info("Zapis typu praktyk");
            internshipType.setInternshipTypeId(id);
            internshipType.setEnabled(oldInternshipType.isEnabled());
            InternshipType f = internshipTypeService.save(internshipType);
            redirectAttributes.addFlashAttribute("msg", "Typ praktyk został pomyślnie zmieniony.");
            return "redirect:../widok/" + id;
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji typu praktyk. Typ praktyk o podanej nazwie już istnieje.");
            return "redirect:" + id;
        } catch (ModelException ex) {
            result.addError(new FieldError("name", ex.getField(), ex.getMessage()));
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "redirect:" + id;
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji typu praktyk. Typ praktyk nie istnieje.");
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
        InternshipType internshipType = internshipTypeService.findOne(id);
        if (internshipType == null) {
            logger.error("Typ praktyk o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd blokowania typu praktyk. Typ praktyk nie mógł zostać zablokowany, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis typu praktyk");
            internshipType.setInternshipTypeId(id);
            internshipType.setEnabled(false);
            internshipTypeService.save(internshipType);
            redirectAttributes.addFlashAttribute("msg", "Typ praktyk został zablokowany. Studenci nie mogą go wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd blokady typu praktyk. Typ praktyk nie istnieje.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Odblokowanie systemu, aby był on widoczny dla studentów. Studenci mogą
     * wybrać odblokowany system.
     *
     * @param id Identyfikator systemu.
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu odblokowania
     * systemu.
     * @return Przekierowanie na listę systemów.
     */
    @RequestMapping(value = "/{id}/odblokuj", method = RequestMethod.POST)
    public String enable(@PathVariable("id") int id,
            final RedirectAttributes redirectAttributes) {
        InternshipType internshipType = internshipTypeService.findOne(id);
        if (internshipType == null) {
            logger.error("Typ praktyk o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd odblokowania typu praktyk. Typ praktyk nie mógł zostać odblokowany, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis typu praktyk");
            internshipType.setEnabled(true);
            internshipTypeService.save(internshipType);
            redirectAttributes.addFlashAttribute("msg", "Typ praktyk został odblokowany. Studenci mogą go wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd oblokowania typu praktyk. Typ praktyk nie istnieje.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Wyświetlanie formularza dodania nowego systemu.
     *
     * @param internshipType
     * @param result
     * @param model
     * @return Formularz dodania nowego systemu.
     */
    @GetMapping("/nowy")
    public String internshipTypeForm(@ModelAttribute("internshipType") InternshipType internshipType,
            BindingResult result, ModelMap model) {
        model.addAttribute("internshipType", internshipType);
        // return "system/create";
        return "internshipType/edit";
    }

    /**
     * Dodawanie nowego systemu.
     *
     * @param internshipType
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu dodania
     * nowego systemu.
     * @return Przekierowanie na widok dodanego systemu.
     */
    @PostMapping("/nowy")
    public String formSubmit(@ModelAttribute("internshipType") @Valid InternshipType internshipType,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Blad zapisu typu praktyk.");
            // return "system/create";
            return "internshipType/edit";
        }
        try {
            logger.info("Zapis typu praktyk");
            internshipType.setEnabled(true);
            InternshipType s = internshipTypeService.save(internshipType);
            redirectAttributes.addFlashAttribute("msg", "Typ praktyk został pomyślnie dodany!");
            return "redirect:widok/" + internshipType.getInternshipTypeId();
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu typu praktyk. Typ praktyk o tej nazwie już istnieje.");
            redirectAttributes.addFlashAttribute("internshipType", internshipType);
            return "redirect:/TypPraktyk/nowy";
        } catch (ModelException ex) {
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu typu praktyk.");
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "internshipType/edit";
        }
    }

    /**
     * Wyświetlanie nazwy systemu.
     *
     * @param model Model ze zmienną reprezentującą dany system.
     * @param id Identyfikator systemu.
     * @return Widok z nazwą systemu.
     */
    @GetMapping("/widok/{id}")
    public String view(Model model, @PathVariable int id) {
        InternshipType internshipType = internshipTypeService.findOne(id);
        if (internshipType == null) {
            logger.error("Typ praktyk o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono typu praktyk.");
        }
        model.addAttribute("internshipType", internshipType);
        return "internshipType/view";
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
        InternshipType internshipType = internshipTypeService.findOne(id);
        if (internshipType == null) {
            logger.error("Typ praktyk o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania typu praktyk. Typ praktyk nie mógł zostać usunięty, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Usuniecie typu praktyk");
            internshipTypeService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Typ praktyk został usunięty.");
            return "redirect:../lista/1";
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania typu praktyk. Typ praktyk nie mógł zostać usunięty, ponieważ nie istniał.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Wyświetlenie listy systemów, podzielonej na strony
     *
     * @param internshipType
     * @param bindingResult Powiązany rezultat.
     * @param pageNumber Numer obecnej strony.
     * @param model Model do przechowywania zmiennych.
     * @return Lista systemów.
     */
    @GetMapping("/lista/{pageNumber}")
    public String list(@ModelAttribute("search") final InternshipType internshipType,
            final BindingResult bindingResult,
            @PathVariable Integer pageNumber, ModelMap model) {
        model.addAttribute("internshipType", internshipType);
        Page<InternshipType> page = internshipTypeService.getFaculties(pageNumber, (internshipType.getType() == null ? "" : internshipType.getType()));
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        List<InternshipType> elements = page.getContent();
        model.addAttribute("elements", elements);
        model.addAttribute("thePage", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        return "internshipType/list";
    }
    
    /**
     * Wyszukiwanie systemów, które zawierają w sobie podany ciąg znaków.
     *
     * @param internshipType
     * @param bindingResult Powiązany rezultat.
     * @param redirectAttributes Przekazuje szukany system (jego nazwę) do
     * metody list().
     * @return Przekierowanie na listę systemów.
     */
    @PostMapping("/lista/{pageNumber}")
    public String searchInternshipType(@ModelAttribute("search") final InternshipType internshipType,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        logger.info("Post method, internshipType.name = {}", internshipType.getType());
        redirectAttributes.addFlashAttribute("search", internshipType);
        return "redirect:../lista/1";
    }
}
