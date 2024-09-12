package org.example.demoapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NGJ_DEPOSITTYPE")
public class DepositType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String withdrawalTools;

    @Column(unique = true, nullable = false)
    private Integer code;

    // One deposit type can be used by multiple deposits
    @OneToMany(mappedBy = "depositType",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Deposit> deposits;
}
