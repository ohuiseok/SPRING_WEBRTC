package com.justudy.backend.study.dto.response;

import com.justudy.backend.member.domain.MemberEntity;
import com.justudy.backend.study.domain.StudyEntity;
import com.justudy.backend.study.domain.StudyMemberEntity;
import com.justudy.backend.study.domain.StudyResumeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class StudyDetailResponse {
    private Long sequence;
    private List<StudyMemberResponse> member;
    private List<Long> resumeSeq;
    private List<StudyFrequencyResponse> frequency;
    private String topCategory;
    private String bottomCategory;
    private String name;
    private Long leaderSeq;
    private String introduction;
    private Integer population;
    private String level;
    private String onlineOffline;
    private Boolean isOpen;
    private String github;
    private String notion;
    private Long imageSequence;
    private LocalDateTime createdTime;
    private String startTime;

    public static StudyDetailResponse makeBuilder(StudyEntity entity) {
        return StudyDetailResponse.builder()
                .sequence(entity.getSequence())
                .member(entity.getStudyMembers()
                        .stream()
                        .map(StudyMemberResponse::makeBuilder)
                        .sorted(Comparator.comparing(StudyMemberResponse::getBadge).reversed())
                        .collect(Collectors.toList()))
                .resumeSeq(entity.getResumes()
                        .stream()
                        .map(StudyResumeEntity::getSequence)
                        .collect(Collectors.toList()))
                .frequency(entity.getFrequency()
                        .stream()
                        .map(StudyFrequencyResponse::makeBuilder)
                        .collect(Collectors.toList()))
                .topCategory(entity.getCategory().getParentCategory().getName())
                .bottomCategory(entity.getCategory().getName())
                .name(entity.getName())
                .leaderSeq(entity.getLeaderSeq())
                .introduction(entity.getIntroduction())
                .population(entity.getPopulation())
                .level(entity.getLevel())
                .onlineOffline(entity.getOnlineOffline())
                .isOpen(entity.getIsOpen())
                .github(entity.getGithub())
                .notion(entity.getNotion())
                .imageSequence(entity.getImageFile().getSequence())
                .createdTime(entity.getCreatedTime())
                .startTime(entity.getStartTime())
                .build();
    }
}
