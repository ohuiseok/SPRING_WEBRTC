package com.justudy.backend.study.repository;

import com.justudy.backend.study.domain.QStudyMemberEntity;
import com.justudy.backend.study.domain.StudyMemberEntity;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudyMemberRepositoryImpl implements StudyMemberRepositorySupport {

    private final JPAQueryFactory queryFactory;
    private final QStudyMemberEntity qStudyMemberEntity = QStudyMemberEntity.studyMemberEntity;

    @Override
    public void deleteStudyMember(Long id, Long memberId) {
        queryFactory
                .delete(qStudyMemberEntity)
                .where(qStudyMemberEntity.study.sequence.eq(id), qStudyMemberEntity.member.sequence.eq(memberId))
                .execute();
    }


    @Override
    public List<StudyMemberEntity> readAllRegisterStudy(Long id) {
        return queryFactory
                .selectFrom(qStudyMemberEntity)
                .where(qStudyMemberEntity.member.sequence.eq(id))
                .fetch();
    }

    @Override
    public void deleteStudyMemberByStudy(Long studySequence) {
        queryFactory
                .delete(qStudyMemberEntity)
                .where(qStudyMemberEntity.study.sequence.eq(studySequence))
                .execute();
    }
}
