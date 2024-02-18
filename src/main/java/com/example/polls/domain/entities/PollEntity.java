package com.example.polls.domain.entities;


import com.example.polls.domain.entities.audit.UserDateAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="polls")
public class PollEntity extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max=140)
    private String question;
    @OneToMany(
            mappedBy="polls",
            cascade = CascadeType.ALL,//changes made to the poll entity should be cascaded to its associated choice entities
            fetch = FetchType.EAGER,//the choices will be loaded immediately with the poll when accessing the  poll
            orphanRemoval = true//when a choice is deleted from a poll entity the choice get removed also from the db
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)//this for the choice entity when it's accessed (separate select statement )(lazy fetching)
    @BatchSize(size = 30)
    private List<ChoiceEntity> choices = new ArrayList<>();
    @NotNull
    private Instant expirationDateTime;
    public void addChoice(ChoiceEntity choice) {
        choices.add(choice);
        choice.setPoll(this);
    }

    public void removeChoice(ChoiceEntity choice) {
        choices.remove(choice);
        choice.setPoll(null);

    }
}
