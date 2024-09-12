package org.example.demoapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data // Lombok will generate getters, setters, toString, equals, and hashcode
@NoArgsConstructor // Lombok will generate a no-arg constructor
@AllArgsConstructor
@Table(name = "NGJ_CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nationalId;

    @Column(unique = true, nullable = false)
    private String customerNumber;

    private String firstName;
    private String lastName;
    private String fathersName;
    private String cityOfBirth;
    private String phoneNumber;


    // One customer can have multiple deposits
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Deposit> deposits;

}
