/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.controller;

import javax.validation.Valid;
import org.prz.entity.SchoolRepresentative;
import org.prz.entity.SecretCode;
import org.prz.entity.Server;
import org.prz.exception.NotFoundException;
import org.prz.service.ManagementService;
import org.prz.validators.SecretCodeValidator;
import org.prz.validators.ServerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ola
 */
@Controller
@RequestMapping("/Zarzadzanie")
public class ManagementController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ManagementService managementService;

    @Autowired
    private SecretCodeValidator validator;
    
    @Autowired
    private ServerValidator serverValidator;
    
    @InitBinder("server")
    private void serverBinder(WebDataBinder binder) {
        binder.setValidator(serverValidator);
    }

    @InitBinder("secretCode")
    private void codeChangeBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
    
    @GetMapping("/reprezentant")
    public String schoolRepresentative(Model model){
        SchoolRepresentative representative = managementService.getSchoolRepresentative();
        if(representative == null){
            logger.error("Błąd odczytu nazwy reprezentatna uczelni.");
            throw new NotFoundException("Nie znaleziono reprezentanta uczelni.");
        }
        model.addAttribute("representative", representative);
        return "management/representative";
    }
    
    @PostMapping("/reprezentant")
    public String schoolRepresentative(
            @ModelAttribute("representative") SchoolRepresentative representative,
            final RedirectAttributes redirectAttributes) {
        try {
            SchoolRepresentative newRepresentative = managementService.changeSchoolRepresentative(representative);
            redirectAttributes.addFlashAttribute("msg", "Nazwa reprezentanta została poprawnie zapisana.");
            return "redirect:/Zarzadzanie/reprezentant";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errormsg", "Błąd zmiany nazwy reprezentanta. ");
            return "redirect:/Zarzadzanie/reprezentant";
        }
    }
    
    @GetMapping("/nazwaSerwera")
    public String nazwaSerwera(Model model){
        Server server = managementService.getServer();
        if (server == null) {
            logger.error("Błąd odczytu nazwy serwera.");
            throw new NotFoundException("Nie znaleziono nazwy serwera.");
        }
        model.addAttribute("server", server);
        return "management/editServerName";
    }
    
    @PostMapping("/nazwaSerwera")
    public String nazwaSerweraPost(
            @ModelAttribute("server")@Valid Server server,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
         if (result.hasErrors()) {
            logger.info("Blad zmiany linka.");
            return "management/editServerName";
        }
        try {
            Server newName = managementService.changeServerName(server);
            redirectAttributes.addFlashAttribute("msg", "Nazwa serwera została poprawnie zapisana.");
            return "redirect:/Zarzadzanie/nazwaSerwera";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errormsg", "Błąd zmiany nazwy serwera. ");
            return "redirect:/Zarzadzanie/nazwaSerwera";
        }
    }

    @GetMapping("/kodRejestracji")
    public String editSecretRegistrationCodeForm(Model model) {
        SecretCode secretCode = managementService.getSecretCode();
        if (secretCode == null) {
            logger.error("Błąd odczytu kodu rejestracji.");
            throw new NotFoundException("Nie znaleziono kodu rejestracji.");
        }
        model.addAttribute("secretCode", secretCode);
        return "management/editSecretRegistrationCode";
    }

    @PostMapping("/kodRejestracji")
    public String editSecretRegistrationCodeFormSubmit(
            @ModelAttribute("secretCode") @Valid SecretCode secretCode,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("Blad zmiany kodu rejestracji.");
            return "management/editSecretRegistrationCode";
        }
        try {
            SecretCode newCode = managementService.changeSecretCode(secretCode);
            redirectAttributes.addFlashAttribute("msg", "Kod rejestracji został poprawnie zapisany.");
            return "redirect:/Zarzadzanie/kodRejestracji";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errormsg", "Błąd zmiany kodu rejestracji. ");
            return "redirect:/Zarzadzanie/kodRejestracji";
        }
    }

}
