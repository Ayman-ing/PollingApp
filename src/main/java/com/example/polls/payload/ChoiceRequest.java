package com.example.polls.payload;

import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChoiceRequest {
    @NotBlank
    @Size(max = 40)
    private String text;


}

