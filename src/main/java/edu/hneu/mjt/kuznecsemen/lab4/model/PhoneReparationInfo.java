package edu.hneu.mjt.kuznecsemen.lab4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PhoneReparationInfo {
    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Manufacturer cannot be blank")
    private String manufacturer;

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @NotBlank(message = "Platform cannot be blank")
    private String platform;

    private boolean camera;

    @NotBlank(message = "Internet description cannot be blank")
    private String internet;

    private boolean gpsModule;
    private boolean recorder;

    @PositiveOrZero(message = "Price must be positive or zero")
    private double price;

    @PositiveOrZero(message = "Optimal price must be positive or zero")
    private double optPrice;

    @NotBlank(message = "User last name cannot be blank")
    private String userLastName;

    @Email(message = "Invalid email address")
    private String userEmail;

    private LocalDate creationDate = LocalDate.now();
}
