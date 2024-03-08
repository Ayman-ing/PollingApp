package com.example.polls.repository;

import com.example.polls.domain.entities.PollEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<PollEntity, Long> {
    Optional<PollEntity> findById(Long pollId);
    Page<PollEntity> findByCreatedBy(Long userId, Pageable pageable);
    long countByCreatedBy(Long userId);
    List<PollEntity> findByIdIn(List<Long> pollIds);
    List<PollEntity> findByIdIn(List<Long> pollIds, Sort sort);


}
