package com.example.miniTeamProject;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends CrudRepository<Diary, Long> {
    @Override
    List<Diary> findAll();
}
