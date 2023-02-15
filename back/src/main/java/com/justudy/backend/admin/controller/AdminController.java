package com.justudy.backend.admin.controller;

import com.justudy.backend.admin.dto.request.MemberSearch;
import com.justudy.backend.admin.dto.response.AdminMemberDetail;
import com.justudy.backend.admin.dto.response.MemberListResponse;
import com.justudy.backend.admin.dto.response.MemberListResult;
import com.justudy.backend.admin.dto.response.TotalResult;
import com.justudy.backend.admin.service.AdminService;
import com.justudy.backend.community.dto.request.CommunitySearch;
import com.justudy.backend.community.dto.response.CommunityDetailResponse;
import com.justudy.backend.community.dto.response.CommunityListResponse;
import com.justudy.backend.community.dto.response.CommunityListResult;
import com.justudy.backend.community.service.CommunityService;
import com.justudy.backend.login.infra.SessionConst;
import com.justudy.backend.member.service.MemberService;
import com.justudy.backend.report.dto.response.admin.ReportListResult;
import com.justudy.backend.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    private final MemberService memberService;

    private final CommunityService communityService;

    private final ReportService reportService;

    @GetMapping("/total-member")
    public TotalResult getTotalMember() {
        return new TotalResult(adminService.getCountOfMembers());
    }

    @GetMapping("/member")
    public MemberListResult<List<MemberListResponse>> getMembers(@ModelAttribute MemberSearch memberSearch) {
        return adminService.getMembers(memberSearch.validateNull());
    }

    @GetMapping("/member/{memberSequence}")
    public AdminMemberDetail getMemberDetail(@PathVariable Long memberSequence) {
        return adminService.getMemberDetail(memberSequence);
    }

    @DeleteMapping("/member/{memberSequence}")
    public ResponseEntity<Void> banMember(@PathVariable Long memberSequence, HttpSession session) {
        Long loginSequence = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        memberService.banMember(loginSequence, memberSequence);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/week/signup")
    public Long getWeekSignup() {
        return adminService.getWeekSignup();
    }

    @GetMapping("/community")
    public CommunityListResult<CommunityListResponse> getCommunities(@ModelAttribute CommunitySearch communitySearch) {
        return adminService.getCommunities(communitySearch);
    }

    @GetMapping("/community/{communitySequence}")
    public CommunityDetailResponse getCommunityDetail(@PathVariable Long communitySequence) {
        return adminService.getCommunityDetailByAdmin(communitySequence);
    }

    @DeleteMapping("/community/{communitySequence}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long communitySequence,
                                                HttpSession session) {
        Long loginSequence = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        adminService.deleteCommunity(loginSequence, communitySequence);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/week/community")
    public Long geCountOfCommunityByWeek() {
        return adminService.geCountOfCommunityByWeek();
    }

    @GetMapping("/report")
    public ReportListResult getReport(@PageableDefault(size = 20) Pageable pageable) {
        return reportService.getReportList(pageable);
    }
}
