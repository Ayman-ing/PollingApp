package com.example.polls.controller;

import com.example.polls.domain.CustomUserDetails;
import com.example.polls.domain.entities.PollEntity;
import com.example.polls.jwt.CurrentUser;
import com.example.polls.payload.*;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.service.PollService;
import com.example.polls.util.AppConstants;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/polls")

public class PollController {


    @Autowired
    private PollService pollService;
    private static final Logger logger = LoggerFactory.getLogger(PollController.class);
    @GetMapping
    public PagedResponse<PollResponse> getPolls(@CurrentUser CustomUserDetails currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getAllPolls(currentUser, page, size);

    }
    @PostMapping
    @PreAuthorize("hasRole('USER')")
            public ResponseEntity<?> createPoll(@RequestBody PollRequest pollRequest) throws BadCredentialsException {

            PollEntity poll = pollService.createPoll(pollRequest);


            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{pollId}")
                    .buildAndExpand(poll.getId()).toUri();

            return ResponseEntity.created(location)
                    .body(new MessageResponse(true, "Poll Created Successfully"));
    /*
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MessageResponse("invalid poll format"));
        }
*/
    }



    @GetMapping("/{pollId}")
    public PollResponse getPollById(@CurrentUser CustomUserDetails currentUser,
                                    @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }
    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    public PollResponse castVote(@CurrentUser CustomUserDetails currentUser,
                                 @PathVariable Long pollId,
                                 @Valid @RequestBody VoteRequest voteRequest) {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
    }

}
