package com.axis.bank.entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String employeeId;

    @Column(unique=true)
    private String email;

    private String name;
    private String contactDetails;
    private String branchLocation;
    private String password;
    private String level;
}
