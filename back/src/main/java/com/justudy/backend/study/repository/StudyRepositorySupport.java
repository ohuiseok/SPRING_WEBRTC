package com.justudy.backend.study.repository;


import com.justudy.backend.study.domain.StudyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;


public interface StudyRepositorySupport {

    Slice<StudyEntity> findAllBySearchOption(Pageable pageable, List<String> sub, String studyLeader, String studyName);

}
