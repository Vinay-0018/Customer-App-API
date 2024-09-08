package com.axis.bank.entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String accountId;

    @Column(unique = true)
    private String email;

    private String name;
    private String contactDetails;
    private String address;
    private String password;
}