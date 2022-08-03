package com.ssafy.hoydia.repository;

import com.ssafy.hoydia.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final EntityManager em;

    public Long regist(File file) {

        em.persist(file);

        return file.getId();

    }

    public File findById(Long id) {

        return em.find(File.class,id);

    }




}
