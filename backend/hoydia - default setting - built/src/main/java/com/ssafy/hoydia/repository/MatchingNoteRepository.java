package com.ssafy.hoydia.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor

public class MatchingNoteRepository {

    private final EntityManager em;

}
