package com.example.polls.repository;

import com.example.polls.domain.entities.ChoiceEntity;
import com.example.polls.domain.entities.PollEntity;
import com.example.polls.domain.entities.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface ChoiceRepository extends JpaRepository<ChoiceEntity,Long> {
    @Query("SELECT c.poll.id FROM ChoiceEntity c where c.id = :choiceId ")
    Long getPollIdByChoiceId(@Param("choiceId") Long choiceId);
    @Transactional
    @Modifying
    @Query("DELETE FROM ChoiceEntity c WHERE c.id = :choiceId")
    void deleteByChoiceId(Long choiceId);
    Optional<ChoiceEntity> getChoiceById(Long choiceId);
}
