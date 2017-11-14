/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.prz.entity.System;
import org.prz.exception.ModelException;
import org.prz.exception.NotFoundException;
import org.prz.service.SystemService;
import org.prz.validators.SystemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 *
 * @author Ola
 */
@Controller
@RequestMapping("/System")
public class SystemController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemService systemService;

    @Autowired
    private SystemValidator systemValidator;

    @ModelAttribute("system")
    public System construct() {
        return new System();
    }

    @InitBinder("system")
    private void systemBinder(WebDataBinder binder) {
        binder.setValidator(systemValidator);
    }

    /**
     * Wyświetlanie formularza edycji systemu
     *
     * @param model Model przechowujący zmienne
     * @param id Identyfikator systemu
     * @return Widok formularza
     */
    @GetMapping("/edycja/{id}")
    public String editForm(Model model, @PathVariable int id) {
        System system = systemService.findOne(id);
        if (system == null) {
            logger.error("System o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono systemu.");
        }
        model.addAttribute("system", system);
        return "system/edit";
    }

    /**
     * Edycja nazwy systemu
     *
     * @param id Identyfikator systemu
     * @param system Przechowuje dane z formularza
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu edycji
     * systemu
     * @return
     */
    @PostMapping("/edycja/{id}")
    public String editPost(@PathVariable int id,
            @ModelAttribute("system") @Valid System system,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "system/edit";
        }
        System oldSystem = systemService.findOne(id);
        try {
            logger.info("Zapis systemu");
            system.setSystemId(id);
            system.setEnabled(oldSystem.isEnabled());
            System s = systemService.save(system);
            redirectAttributes.addFlashAttribute("msg", "System został pomyślnie zmieniony.");
            return "redirect:../widok/" + id;
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji systemu. System o podanej nazwie już istnieje.");
            return "redirect:" + id;
        } catch (ModelException ex) {
            result.addError(new FieldError("name", ex.getField(), ex.getMessage()));
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "redirect:" + id;
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji systemu. System nie istnieje.");
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
        System system = systemService.findOne(id);
        if (system == null) {
            logger.error("System o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd blokowania systemu. System nie mógł zostać zablokowany, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis systemu");
            system.setSystemId(id);
            system.setEnabled(false);
            systemService.save(system);
            redirectAttributes.addFlashAttribute("msg", "System został zablokowany. Studenci nie mogą go wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji systemu. System nie istnieje.");
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
        System system = systemService.findOne(id);
        if (system == null) {
            logger.error("System o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd odblokowania systemu. System nie mógł zostać odblokowany, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis systemu");
            system.setEnabled(true);
            systemService.save(system);
            redirectAttributes.addFlashAttribute("msg", "System został odblokowany. Studenci mogą go wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji systemu. System nie istnieje.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Wyświetlanie formularza dodania nowego systemu.
     *
     * @param system
     * @param result
     * @param model
     * @return Formularz dodania nowego systemu.
     */
    @GetMapping("/nowy")
    public String systemForm(@ModelAttribute("system") System system,
            BindingResult result, ModelMap model) {
        model.addAttribute("system", system);
       // return "system/create";
       return "system/edit";
    }

    /**
     * Dodawanie nowego systemu.
     *
     * @param system Nowy system.
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu dodania
     * nowego systemu.
     * @return Przekierowanie na widok dodanego systemu.
     */
    @PostMapping("/nowy")
    public String formSubmit(@ModelAttribute("system") @Valid System system,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Blad zapisu systemu.");
           // return "system/create";
           return "system/edit";
        }
        try {
            logger.info("Zapis systemu");
            system.setEnabled(true);
            System s = systemService.save(system);
            redirectAttributes.addFlashAttribute("msg", "System został pomyślnie dodany!");
            return "redirect:widok/" + system.getSystemId();
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu systemu. System o tej nazwie już istnieje.");
            redirectAttributes.addFlashAttribute("system", system);
            return "redirect:/System/nowy";
        } catch (ModelException ex) {
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu systemu.");
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "system/edit";
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
        System system = systemService.findOne(id);
        if (system == null) {
            logger.error("System o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono systemu.");
        }
        model.addAttribute("system", system);
        return "system/view";
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
        System system = systemService.findOne(id);
        if (system == null) {
            logger.error("System o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania systemu. System nie mógł zostać usunięty, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Usuniecie systemu");
            systemService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "System został usunięty.");
            return "redirect:../lista/1";
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania systemu. System nie mógł zostać usunięty, ponieważ nie istniał.");
            return "redirect:../lista/1";
        }

    }

    /**
     * Wyświetlenie listy systemów, podzielonej na strony
     *
     * @param system Zmienna do wyszukiwania systemu.
     * @param bindingResult Powiązany rezultat.
     * @param pageNumber Numer obecnej strony.
     * @param model Model do przechowywania zmiennych.
     * @return Lista systemów.
     */
    @GetMapping("/lista/{pageNumber}")
    public String list(@ModelAttribute("systemSearch") final System system,
            final BindingResult bindingResult,
            @PathVariable Integer pageNumber, ModelMap model) {
        model.addAttribute("system", system);
        Page<System> page = systemService.getSystems(pageNumber, (system.getName() == null ? "" : system.getName()));
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        List<System> systemy = page.getContent();
        model.addAttribute("systemy", systemy);
        model.addAttribute("systemPage", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        return "system/list";
    }

    /**
     * Wyszukiwanie systemów, które zawierają w sobie podany ciąg znaków.
     *
     * @param system Zawiera pole z nazwą szukanego systemu.
     * @param bindingResult Powiązany rezultat.
     * @param redirectAttributes Przekazuje szukany system (jego nazwę) do
     * metody list().
     * @return Przekierowanie na listę systemów.
     */
    @PostMapping("/lista/{pageNumber}")
    public String searchSystem(@ModelAttribute("systemSearch") final System system,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        logger.info("Post method, system.name = {}", system.getName());
        redirectAttributes.addFlashAttribute("systemSearch", system);
        return "redirect:../lista/1";
    }

}
