package com.codecool.model;

import com.codecool.exception.InvalidEmailException;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Email {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    @NotEmpty
    @ManyToOne
    private Client client;

    @NotNull
    @NotEmpty
    private String subject;

    @ElementCollection
    @CollectionTable(name = "receivers", joinColumns = @JoinColumn(name = "email_id"))
    private Set<String> receivers = new HashSet<>();

    @NotNull
    @NotEmpty
    private String body;

    @org.hibernate.validator.constraints.Email
    private String cc;

    @org.hibernate.validator.constraints.Email
    private String bcc;

    public void setReceivers(String receiver) throws InvalidEmailException {
        if (!receiver.matches(EMAIL_PATTERN)) {
            throw new InvalidEmailException();
        }
        this.receivers.add(receiver);
        }
    }
