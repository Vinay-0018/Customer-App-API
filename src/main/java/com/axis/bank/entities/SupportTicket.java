package com.axis.bank.entities;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp timestamp;
    private String accountId;
    private String status;
    private String subject;

    @Column(length = 2000)
    private String description;
    
    private String employeeComment;
    private String customerComment;
    private String level;

    @OneToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;
}

