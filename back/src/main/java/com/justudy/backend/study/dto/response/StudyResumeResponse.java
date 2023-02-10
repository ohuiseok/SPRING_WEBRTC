package com.justudy.backend.study.dto.response;

import com.justudy.backend.member.domain.MemberEntity;
import com.justudy.backend.study.domain.StudyResumeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class StudyResumeResponse {
    private Long sequence;
    private Long studySeq;
    private String content;
    private LocalDateTime createdTime;
    private Long imageSequence;
    private String nickName;

    public static StudyResumeResponse makeBuilder(StudyResumeEntity entity) {
        MemberEntity memberEntity = entity.getMember();
        return StudyResumeResponse.builder()
                .sequence(entity.getSequence())
                .studySeq(entity.getStudy().getSequence())
                .content(entity.getContent())
                .createdTime(entity.getCreatedTime())
                .imageSequence(memberEntity.getImageFile().getSequence())
                .nickName(memberEntity.getNickname())
                .build();
    }
}
