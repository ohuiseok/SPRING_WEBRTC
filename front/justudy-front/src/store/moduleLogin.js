import axios from 'axios';
import port from './port';

export default {
    namespaced: true,
    state: {
        isLogin: {
            loginCheck: false
        }
    },
    getters: {},
    mutations: {
        SET_ISLOGIN(state, payload) {
            state.isLogin = payload;
        }
    },
    actions: {
        async login({commit}, {user}) {
            await axios
                .post(
                    port + 'login',
                    {
                        userId: user.userId,
                        password: user.password
                    },
                    {
                        withCredentials: true
                    }
                )
                .then(res => {
                    commit('SET_ISLOGIN', {
                        loginCheck: true
                    });
                    localStorage.setItem('nickname', res.data.nickname);
                    window.location.href = '/';
                })
                .catch(err => {
                    console.log(err);
                    alert('아이디/비밀번호가 일치하지 않습니다.');
                });
        },
        async logout({commit}) {
            const API_URL = `${port}logout`;
            await axios({
                url: API_URL,
                method: 'POST',
                withCredentials: true
            })
                .then(() => {
                    commit('SET_ISLOGIN', {
                        loginCheck: false
                    });
                    localStorage.removeItem('nickname');
                    window.location.href = '/';
                })
                .catch(err => {
                    console.log(err);
                });
        }
    }
};
