package com.example.polls.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChoiceVoteCount {
    private Long choiceId;
    private Long voteCount;
}
