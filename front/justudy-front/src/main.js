import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import '@/assets/styles/icons.css';
import axios from 'axios';
import port from './store/port';
Vue.config.productionTip = false;

router.beforeEach(async (to, from, next) => {
    //authenticationState는 유저가 로그인이 되어

    const {authorization} = to.meta;

    if (authorization?.[0] == 'login') {
        // 여기서 로그인 체크 할것임
        await axios
            .get(port + 'member/mypage', {
                withCredentials: true
            })
            .then(res => {
                console.log(res.data);
            })
            .catch(() => {
                alert('로그인이 필요합니다.');
                return next({path: '/login'});
            });
        // console.log(authorization);
    }
    next();
});

new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app');
