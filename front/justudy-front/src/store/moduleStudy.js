import axios from 'axios';
import port from '@/store/port';

export default {
    namespaced: true,
    state: {
        promotionStudies: [],
        morePromotionStudies: [],
        topCategories: [],
        bottomCategories: [],
        applyStudyInfo: {}, //공개된 스터디 상세 정보
        applyStudies: [], //내가 지원한 스터디 목록
        myStudies: [], // 내가 가입한 스터디 목록
        studyInfo: {}, //스터디 상세 정보
        nameCheck: false
    },
    getters: {},
    mutations: {
        GET_PROMOTIONSTUDY(state, payload) {
            state.promotionStudies = payload;
        },
        GET_MOREPROMOTIONSTUDY(state, payload) {
            state.morePromotionStudies = payload;
        },
        GET_TOPCATEGORIES(state, payload) {
            state.topCategories = payload;
        },
        GET_BOTTOMCATEGORIES(state, payload) {
            state.bottomCategories = payload;
        },
        emptyCategories(state) {
            state.bottomCategories = null;
        },
        GET_APPLYSTUDYINFO(state, payload) {
            state.applyStudyInfo = payload;
        },
        GET_APPLYSTUDIES(state, payload) {
            state.applyStudies = payload;
        },
        GET_MYSTUDIES(state, payload) {
            state.myStudies = payload;
        },
        GET_STUDYINFO(state, payload) {
            state.studyInfo = payload;
        },
        CHECK_NAME(state, payload) {
            state.nameCheck = payload;
        }
    },
    actions: {
        //공개된 스터디 받아오기
        async getPromotionStudies({commit}, send) {
            console.log('검색 진행', send);
            let API_URL;
            if (send.content == '') {
                API_URL = `${port}study/?page=${send.page}`;
            } else {
                API_URL = `${port}study/?page=${send.page}&type=${send.type}&search=${send.content}`;
            }
            console.log(API_URL);

            await axios({
                url: API_URL,
                method: 'GET'
            })
                .then(res => {
                    commit('GET_PROMOTIONSTUDY', res.data);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //공개된 스터디 목록에서 더보기를 눌렀을 때 처리하는 내용
        async getMorePromotionStudies({commit}, send) {
            const API_URL = `${port}study/?page=${send.page}&type=${send.type}&search=${send.content}`;
            await axios({
                url: API_URL,
                method: 'GET'
            })
                .then(res => {
                    commit('GET_MOREPROMOTIONSTUDY', res.data);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //상위 카테고리 받아오기
        async getTopCategories({commit}) {
            const API_URL = `${port}category/main-category`;
            await axios({
                url: API_URL,
                method: 'GET'
            })
                .then(res => {
                    commit('GET_TOPCATEGORIES', res.data);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //하위 카테고리 받아오기
        async getBottomCategories({commit}, top) {
            let API_URL = `${port}category`;
            if (top != '전체') {
                API_URL += `?main=${top}`;
            }
            await axios({
                url: API_URL,
                method: 'GET'
            })
                .then(res => {
                    commit('GET_BOTTOMCATEGORIES', res.data);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //스터디 홍보페이지 상세 정보 받아오기
        async getApplyStudyInfo({commit}, id) {
            const API_URL = `${port}study/${id}`;
            await axios({
                url: API_URL,
                method: 'GET',
                withCredentials: true
            })
                .then(res => {
                    console.log('홍보 스터디 상세: ', res.data);
                    commit('GET_APPLYSTUDYINFO', res.data);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //스터디 지원 신청
        async applyStudy({commit}, sendData) {
            const API_URL = `${port}study/apply`;
            await axios({
                url: API_URL,
                method: 'POST',
                data: sendData,
                withCredentials: true
            })
                .then(() => {
                    commit;
                    window.location.replace('/study/myStudy');
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //내가 지원한 스터디 목록 받아오기
        async getApplyStudies({commit}) {
            const API_URL = `${port}study/mystudy/apply`;
            await axios({
                url: API_URL,
                method: 'GET',
                withCredentials: true
            })
                .then(res => {
                    commit('GET_APPLYSTUDIES', res.data);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //내가 가입한 스터디 목록 받아오기
        async getMyStudies({commit}) {
            const API_URL = `${port}study/mystudy/register`;
            await axios({
                url: API_URL,
                method: 'GET',
                withCredentials: true
            })
                .then(res => {
                    commit('GET_MYSTUDIES', res.data);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //스터디 이름 중복체크
        async nameCheck({commit}, name) {
            const API_URL = `${port}study/check/${name}`;
            await axios({
                url: API_URL,
                method: 'GET'
            })
                .then(() => {
                    commit('CHECK_NAME', true);
                    alert('사용 가능한 이름입니다.');
                })
                .catch(err => {
                    if (err.response.status == 409) {
                        alert('이미 사용 중인 이름입니다.');
                    } else {
                        console.log(err);
                    }
                });
        },
        //스터디 생성하기
        async createStudy({commit}, study) {
            const API_URL = `${port}study`;
            await axios({
                url: API_URL,
                method: 'POST',
                data: study,
                withCredentials: true
            })
                .then(res => {
                    commit;
                    window.location.replace(`/study/${res.data.sequence}/info`);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //스터디 상세페이지 정보 받아오기
        async getStudyInfo({commit}, studySeq) {
            const API_URL = `${port}study/${studySeq}`;
            await axios({
                url: API_URL,
                method: 'GET',
                withCredentials: true
            })
                .then(res => {
                    console.log(res.data);
                    commit('GET_STUDYINFO', res.data);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //스터디 탈퇴하기
        async withdrawStudy({commit}, studySeq) {
            const API_URL = `${port}study/${studySeq}/withdraw`;
            await axios({
                url: API_URL,
                method: 'DELETE',
                withCredentials: true
            })
                .then(() => {
                    commit;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        //스터디 지원 취소
        async deleteApply({commit}, seq) {
            const API_URL = `${port}study/apply/${seq}`;
            await axios({
                url: API_URL,
                method: 'DELETE',
                withCredentials: true
            })
                .then(() => {
                    commit;
                    window.location.reload();
                })
                .catch(err => {
                    console.log(err);
                });
        }
    }
};
