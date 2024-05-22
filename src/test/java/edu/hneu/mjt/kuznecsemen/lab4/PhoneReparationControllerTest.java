package edu.hneu.mjt.kuznecsemen.lab4;

import edu.hneu.mjt.kuznecsemen.lab4.controller.PhoneReparationController;
import edu.hneu.mjt.kuznecsemen.lab4.model.PhoneReparationInfo;
import edu.hneu.mjt.kuznecsemen.lab4.repository.PhoneReparationInfoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Set;

import static jakarta.validation.Validation.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PhoneReparationControllerTest {
    private PhoneReparationInfoRepository repository;
    private Model model;
    private PhoneReparationController controller;
    private PhoneReparationInfo phoneReparationInfo;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PhoneReparationInfoRepository.class);
        model = Mockito.mock(Model.class);
        controller = new PhoneReparationController(repository);
        phoneReparationInfo = new PhoneReparationInfo();
        phoneReparationInfo = new PhoneReparationInfo();

        phoneReparationInfo.setTitle("Test");
        phoneReparationInfo.setManufacturer("Apple");
        phoneReparationInfo.setModel("iPhone 12");
        phoneReparationInfo.setPlatform("iOS");
        phoneReparationInfo.setCamera(true);
        phoneReparationInfo.setInternet("3G");
        phoneReparationInfo.setGpsModule(true);
        phoneReparationInfo.setRecorder(true);
        phoneReparationInfo.setPrice(1000.0);
        phoneReparationInfo.setOptPrice(900.0);
        phoneReparationInfo.setUserLastName("Doe");
        phoneReparationInfo.setUserEmail("doe@example.com");
    }

    @Test
    void index() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        String view = controller.index(model);

        verify(model, times(2)).addAttribute(anyString(), any());
        assertEquals("index", view);
    }

    @Test
    void add_withValidPhoneReparationInfo() {
        when(repository.save(any(PhoneReparationInfo.class))).thenReturn(phoneReparationInfo);
        ValidatorFactory factory = buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PhoneReparationInfo>> violations = validator.validate(phoneReparationInfo);

        if (violations.isEmpty()) {
            String returnString = controller.add(phoneReparationInfo, model);
            assertEquals(returnString, "redirect:/");
            verify(repository, times(1)).save(phoneReparationInfo);
        } else {
            fail("Validation errors occurred.");
        }
    }

    @Test
    void add_withInvalidPhoneReparationInfo() {
        PhoneReparationInfo invalidPhoneReparationInfo = new PhoneReparationInfo();
        invalidPhoneReparationInfo.setPrice(-10);
        invalidPhoneReparationInfo.setUserEmail("invalid-email");
        when(repository.save(any(PhoneReparationInfo.class))).thenReturn(invalidPhoneReparationInfo);
        ValidatorFactory factory = buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PhoneReparationInfo>> violations = validator.validate(invalidPhoneReparationInfo);

        if (violations.isEmpty()) {
            fail("Validation errors did not occur as expected.");
        } else {
            String returnString = controller.add(invalidPhoneReparationInfo, model);
            assertEquals(returnString, "index");
            verify(repository, times(0)).save(invalidPhoneReparationInfo);
        }
    }

    @Test
    void delete() {
        String id = "1";
        doNothing().when(repository).deleteById(id);

        String view = controller.delete(id);

        verify(repository).deleteById("1");
        assertEquals("redirect:/", view);
    }
}
