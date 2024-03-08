package com.example.polls.repository;

import com.example.polls.domain.ChoiceVoteCount;
import com.example.polls.domain.entities.VoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity,Long> {
    @Query("SELECT NEW com.example.polls.domain.ChoiceVoteCount(v.choice.id, count(v.id)) " +
            "FROM VoteEntity v WHERE v.poll.id in :pollIds GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);

    @Query("SELECT NEW com.example.polls.domain.ChoiceVoteCount(v.choice.id, count(v.id))" +
            " FROM VoteEntity v WHERE v.poll.id = :pollId GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") Long pollId);

    @Query("SELECT v FROM VoteEntity v where v.user.id = :userId and v.poll.id in :pollIds")
    List<VoteEntity> findByUserIdAndPollIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);


    List<VoteEntity> findVoteEntitiesByPollId(Long pollId);

    @Query("SELECT v FROM VoteEntity v where v.user.id = :userId and v.poll.id = :pollId")
    VoteEntity findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("SELECT COUNT(v.id) from VoteEntity v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.poll.id FROM VoteEntity v WHERE v.user.id = :userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);
    @Query("SELECT v FROM VoteEntity v WHERE v.choice.id = :choiceId")
    List<VoteEntity> findVoteEntitiesByChoiceId(@Param("choiceId") Long choiceId);
    @Transactional
    @Modifying
    void deleteByUserIdAndPollId( Long id,Long pollId);
}
