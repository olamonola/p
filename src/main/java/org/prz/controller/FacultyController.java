/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import java.util.List;
import javax.validation.Valid;
import org.prz.entity.Faculty;
import org.prz.exception.ModelException;
import org.prz.exception.NotFoundException;
import org.prz.service.FacultyService;
import org.prz.validators.FacultyValidator;
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
@RequestMapping("/Fakultet")
public class FacultyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyValidator facultyValidator;

    @ModelAttribute("faculty")
    public Faculty construct() {
        return new Faculty();
    }

    @InitBinder("faculty")
    private void facultyBinder(WebDataBinder binder) {
        binder.setValidator(facultyValidator);
    }

    /**
     * Wyświetlanie formularza edycji kierunku
     *
     * @param model Model przechowujący zmienne
     * @param id Identyfikator kierunku
     * @return Widok formularza
     */
    @GetMapping("/edycja/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Faculty faculty = facultyService.findOne(id);
        if (faculty == null) {
            logger.error("Kierunek o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono kierunku.");
        }
        model.addAttribute("faculty", faculty);
        return "faculty/edit";
    }

    /**
     * Edycja nazwy kierunku
     *
     * @param id Identyfikator kierunku
     * @param faculty
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu edycji
     * kierunku
     * @return
     */
    @PostMapping("/edycja/{id}")
    public String editPost(@PathVariable int id,
            @ModelAttribute("faculty") @Valid Faculty faculty,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Powrót do formularza");
            return "faculty/edit";
        }
        Faculty oldFaculty = facultyService.findOne(id);
        try {
            logger.info("Zapis kierunku");
            faculty.setFacultyId(id);
            faculty.setEnabled(oldFaculty.isEnabled());
            Faculty f = facultyService.save(faculty);
            redirectAttributes.addFlashAttribute("msg", "Kierunek został pomyślnie zmieniony.");
            return "redirect:../widok/" + id;
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji kierunku. Kierunek o podanej nazwie już istnieje.");
            return "redirect:" + id;
        } catch (ModelException ex) {
            result.addError(new FieldError("name", ex.getField(), ex.getMessage()));
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "redirect:" + id;
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd edycji kierunku. Kierunek nie istnieje.");
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
        Faculty faculty = facultyService.findOne(id);
        if (faculty == null) {
            logger.error("Kierunek o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd blokowania kierunku. Kierunek nie mógł zostać zablokowany, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis kierunku");
            faculty.setFacultyId(id);
            faculty.setEnabled(false);
            facultyService.save(faculty);
            redirectAttributes.addFlashAttribute("msg", "Kierunek został zablokowany. Studenci nie mogą go wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd blokady kierunku. Kierunek nie istnieje.");
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
        Faculty faculty = facultyService.findOne(id);
        if (faculty == null) {
            logger.error("Kierunek o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd odblokowania kierunku. Kierunek nie mógł zostać odblokowany, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Zapis kierunku");
            faculty.setEnabled(true);
            facultyService.save(faculty);
            redirectAttributes.addFlashAttribute("msg", "Kierunek został odblokowany. Studenci mogą go wybrać.");
            return "redirect:../lista/1";
        } catch (NullPointerException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd oblokowania kierunku. Fakultet nie istnieje.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Wyświetlanie formularza dodania nowego systemu.
     *
     * @param faculty
     * @param result
     * @param model
     * @return Formularz dodania nowego systemu.
     */
    @GetMapping("/nowy")
    public String facultyForm(@ModelAttribute("faculty") Faculty faculty,
            BindingResult result, ModelMap model) {
        model.addAttribute("faculty", faculty);
        // return "system/create";
        return "faculty/edit";
    }

    /**
     * Dodawanie nowego systemu.
     *
     * @param faculty
     * @param result
     * @param redirectAttributes Przekazuje wiadomość o powodzeniu dodania
     * nowego systemu.
     * @return Przekierowanie na widok dodanego systemu.
     */
    @PostMapping("/nowy")
    public String formSubmit(@ModelAttribute("faculty") @Valid Faculty faculty,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Blad zapisu kierunku.");
            // return "system/create";
            return "faculty/edit";
        }
        try {
            logger.info("Zapis kierunku");
            faculty.setEnabled(true);
            Faculty s = facultyService.save(faculty);
            redirectAttributes.addFlashAttribute("msg", "Kierunek został pomyślnie dodany!");
            return "redirect:widok/" + faculty.getFacultyId();
        } catch (DataIntegrityViolationException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu kierunku. Kierunek o tej nazwie już istnieje.");
            redirectAttributes.addFlashAttribute("faculty", faculty);
            return "redirect:/Fakultet/nowy";
        } catch (ModelException ex) {
            redirectAttributes.addFlashAttribute("msg", "Wystąpił błąd przy dodawaniu kierunku.");
            logger.error("Wystąpił wyjątek {}", ex.toString());
            return "faculty/edit";
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
        Faculty faculty = facultyService.findOne(id);
        if (faculty == null) {
            logger.error("Kierunek o id: {} nie istnieje", id);
            throw new NotFoundException("Nie znaleziono kierunku.");
        }
        model.addAttribute("faculty", faculty);
        return "faculty/view";
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
        Faculty faculty = facultyService.findOne(id);
        if (faculty == null) {
            logger.error("Kierunek o id: {} nie istnieje", id);
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania kierunku. Kierunek nie mógł zostać usunięty, ponieważ nie istniał.");
            return "redirect:../lista/1";
            //throw new NotFoundException("Nie znaleziono systemu.");
        }
        try {
            logger.info("Usuniecie kierunku");
            facultyService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Kierunek został usunięty.");
            return "redirect:../lista/1";
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Wystąpił wyjątek {}", ex.toString());
            redirectAttributes.addFlashAttribute("msg", "Błąd usuwania kierunku. Kierunek nie mógł zostać usunięty, ponieważ nie istniał.");
            return "redirect:../lista/1";
        }
    }

    /**
     * Wyświetlenie listy systemów, podzielonej na strony
     *
     * @param faculty
     * @param bindingResult Powiązany rezultat.
     * @param pageNumber Numer obecnej strony.
     * @param model Model do przechowywania zmiennych.
     * @return Lista systemów.
     */
    @GetMapping("/lista/{pageNumber}")
    public String list(@ModelAttribute("facultySearch") final Faculty faculty,
            final BindingResult bindingResult,
            @PathVariable Integer pageNumber, ModelMap model) {
        model.addAttribute("faculty", faculty);
        Page<Faculty> page = facultyService.getFaculties(pageNumber, (faculty.getName() == null ? "" : faculty.getName()));
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        List<Faculty> fakultety = page.getContent();
        model.addAttribute("fakultety", fakultety);
        model.addAttribute("thePage", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        return "faculty/list";
    }
    
    /**
     * Wyszukiwanie systemów, które zawierają w sobie podany ciąg znaków.
     *
     * @param faculty
     * @param bindingResult Powiązany rezultat.
     * @param redirectAttributes Przekazuje szukany system (jego nazwę) do
     * metody list().
     * @return Przekierowanie na listę systemów.
     */
    @PostMapping("/lista/{pageNumber}")
    public String searchFaculty(@ModelAttribute("facultySearch") final Faculty faculty,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        logger.info("Post method, faculty.name = {}", faculty.getName());
        redirectAttributes.addFlashAttribute("facultySearch", faculty);
        return "redirect:../lista/1";
    }
}
