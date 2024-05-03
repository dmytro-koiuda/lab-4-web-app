package edu.hneu.mjt.kuznecsemen.lab4.controller;

import edu.hneu.mjt.kuznecsemen.lab4.model.PhoneReparationInfo;
import edu.hneu.mjt.kuznecsemen.lab4.repository.PhoneReparationInfoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
@AllArgsConstructor
public class PhoneReparationController {

    private PhoneReparationInfoRepository repository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("phoneReparationInfos", repository.findAll());
        model.addAttribute("newPhoneReparationInfo", new PhoneReparationInfo());
        return "index";
    }

    @PostMapping("/add")
    public String add(PhoneReparationInfo phoneReparationInfo, Model model) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PhoneReparationInfo>> violations = validator.validate(phoneReparationInfo);

        if (!violations.isEmpty()) {
            model.addAttribute("violations", violations);
            model.addAttribute("phoneReparationInfos", repository.findAll());
            model.addAttribute("newPhoneReparationInfo", phoneReparationInfo);
            return "index";
        }

        repository.save(phoneReparationInfo);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(String id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        PhoneReparationInfo phoneReparationInfo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid repair id: " + id));
        model.addAttribute("phoneReparationInfo", phoneReparationInfo);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateRepair(@PathVariable("id") String id, PhoneReparationInfo updatedRepair, Model model) {
        PhoneReparationInfo existingRepair = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid repair id: " + id));

        existingRepair.setTitle(updatedRepair.getTitle());
        existingRepair.setManufacturer(updatedRepair.getManufacturer());
        existingRepair.setModel(updatedRepair.getModel());
        existingRepair.setPlatform(updatedRepair.getPlatform());
        existingRepair.setCamera(updatedRepair.isCamera());
        existingRepair.setInternet(updatedRepair.getInternet());
        existingRepair.setGpsModule(updatedRepair.isGpsModule());
        existingRepair.setRecorder(updatedRepair.isRecorder());
        existingRepair.setPrice(updatedRepair.getPrice());
        existingRepair.setOptPrice(updatedRepair.getOptPrice());
        existingRepair.setUserLastName(updatedRepair.getUserLastName());
        existingRepair.setUserEmail(updatedRepair.getUserEmail());

        repository.save(existingRepair);
        return "redirect:/";
    }


}
