/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import java.util.List;
import javax.validation.Valid;
import org.prz.entity.Specialization;
import org.prz.exception.ModelException;
import org.prz.exception.NotFoundException;
import org.prz.service.SpecializationService;
import org.prz.validators.SpecializationValidator;
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
@RequestMapping("/Specjalizacja")
public class SpecializationController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SpecializationService service;

    @Autowired
    private SpecializationValidator validator;

    @ModelAttribute("specialization")
    public Specialization construct() {
        return new Specialization();
    }

    @InitBinder("specialization")
    private void specializationBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * Wyświetlanie formularza edycji specjalności
     *
     * @param model Model przechowujący zmienne
     * @param id Identyfikator specjalności
     * @return Widok formularza
     */
    @GetMapping("/edycja/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Specialization specialization = service.findOne(id);
        if (specialization == null) {
            logger.error("Specjalność o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono specjalności.");
        }
        model.addAttribute("specialization", specialization);
        return "specialization/edit";
    }

    /**
     * Edycja nazwy specjalności
     *
     * @param id Identyfikator specjalności
     * @param specialization
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu edycji
     * specjalności
     * @return
     */
    @PostMapping("/edycja/{id}")
    public String editPost(@PathVariable int id,
            @ModelAttribute("specialization") @Valid Specialization specialization,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "specialization/edit";
        }
        Specialization oldSpecialization = service.findOne(id);
        try {
            logger.info("Zapis specjalności");
            specialization.setSpecializationId(id);
            specialization.setEnabled(oldSpecialization.isEnabled());
            Specialization f = service.save(specialization);
            redirectAttributes.addFlashAttribute("msg", "Specjalność została pomyślnie zmieniona.");
            return "redirect:../widok/" + id;
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji specjalności. Specjalność o podanej nazwie już istnieje.");
            return "redirect:" + id;
        } catch (ModelException ex) {
            result.addError(new FieldError("name", ex.getField(), ex.getMessage()));
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "redirect:" + id;
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji specjalności. Specjalność nie istnieje.");
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
        Specialization specialization = service.findOne(id);
        if (specialization == null) {
            logger.error("Specjalność o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd blokowania specjalności. Specjalność nie mogła zostać zablokowana, ponieważ nie istniała.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis specjalności");
            specialization.setSpecializationId(id);
            specialization.setEnabled(false);
            service.save(specialization);
            redirectAttributes.addFlashAttribute("msg", "Specjalność została zablokowana. Studenci nie mogą jej wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd blokady specjalności. Specjalność nie istnieje.");
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
        Specialization specialization = service.findOne(id);
        if (specialization == null) {
            logger.error("Specjalność o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd odblokowania specjalności. Specjalność nie mogła zostać odblokowana, ponieważ nie istniała.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis specjalności");
            specialization.setEnabled(true);
            service.save(specialization);
            redirectAttributes.addFlashAttribute("msg", "Specjalność została odblokowana. Studenci mogą ją wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd oblokowania specjalności. Specjalność nie istnieje.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Wyświetlanie formularza dodania nowego systemu.
     *
     * @param specialization
     * @param result
     * @param model
     * @return Formularz dodania nowego systemu.
     */
    @GetMapping("/nowy")
    public String specializationForm(@ModelAttribute("specialization") Specialization specialization,
            BindingResult result, ModelMap model) {
        model.addAttribute("specialization", specialization);
        // return "system/create";
        return "specialization/edit";
    }

    /**
     * Dodawanie nowego systemu.
     *
     * @param specialization
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu dodania
     * nowego systemu.
     * @return Przekierowanie na widok dodanego systemu.
     */
    @PostMapping("/nowy")
    public String formSubmit(@ModelAttribute("specialization") @Valid Specialization specialization,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Blad zapisu specjalności.");
            // return "system/create";
            return "specialization/edit";
        }
        try {
            logger.info("Zapis specjalności");
            specialization.setEnabled(true);
            Specialization s = service.save(specialization);
            redirectAttributes.addFlashAttribute("msg", "Specjalność została pomyślnie dodana!");
            return "redirect:widok/" + specialization.getSpecializationId();
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu specjalności. Specjalność o tej nazwie już istnieje.");
            redirectAttributes.addFlashAttribute("specialization", specialization);
            return "redirect:/Specjalizacja/nowy";
        } catch (ModelException ex) {
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu specjalności.");
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "specialization/edit";
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
        Specialization specialization = service.findOne(id);
        if (specialization == null) {
            logger.error("Specjalność o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono specjalności.");
        }
        model.addAttribute("specialization", specialization);
        return "specialization/view";
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
        Specialization specialization = service.findOne(id);
        if (specialization == null) {
            logger.error("Specjalność o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania specjalności. Specjalność nie mogła zostać usunięta, ponieważ nie istniała.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Usuniecie specjalności");
            service.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Specjalność została usunięta.");
            return "redirect:../lista/1";
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania specjalności. Specjalność nie mogła zostać usunięta, ponieważ nie istniała.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Wyświetlenie listy systemów, podzielonej na strony
     *
     * @param specialization
     * @param bindingResult Powiązany rezultat.
     * @param pageNumber Numer obecnej strony.
     * @param model Model do przechowywania zmiennych.
     * @return Lista systemów.
     */
    @GetMapping("/lista/{pageNumber}")
    public String list(@ModelAttribute("search") final Specialization specialization,
            final BindingResult bindingResult,
            @PathVariable Integer pageNumber, ModelMap model) {
        model.addAttribute("specialization", specialization);
        Page<Specialization> page = service.getElements(pageNumber, (specialization.getName() == null ? "" : specialization.getName()));
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        List<Specialization> elements = page.getContent();
        model.addAttribute("elements", elements);
        model.addAttribute("thePage", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        return "specialization/list";
    }
    
    /**
     * Wyszukiwanie systemów, które zawierają w sobie podany ciąg znaków.
     *
     * @param specialization
     * @param bindingResult Powiązany rezultat.
     * @param redirectAttributes Przekazuje szukany system (jego nazwę) do
     * metody list().
     * @return Przekierowanie na listę systemów.
     */
    @PostMapping("/lista/{pageNumber}")
    public String searchSpecialization(@ModelAttribute("search") final Specialization specialization,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        logger.info("Post method, specialization.name = {}", specialization.getName());
        redirectAttributes.addFlashAttribute("search", specialization);
        return "redirect:../lista/1";
    }
}
