import axios from "axios";
import {useMemberStore} from "@/store/useMemberStore.jsx";


/**
 * 자동 토큰 갱신이 설정된 axios 인스턴스
 * @type {axios.AxiosInstance}
 */
const auto = axios.create({
    baseURL: "/",
    withCredentials: true, // HttpOnly refresh 쿠키 위해 필요
});

let isRefreshing = false;
let failedQueue = [];

const processQueue = (error, token = null) => {
    failedQueue.forEach(prom => {
        if (error) {
            prom.reject(error);
        } else {
            prom.resolve(token);
        }
    });

    failedQueue = [];
};

auto.interceptors.response.use(
    (res) => res,
    async (error) => {
        const originalRequest = error.config;

        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            // refresh 요청 중 중복 방지
            if (isRefreshing) {
                return new Promise(function (resolve, reject) {
                    failedQueue.push({resolve, reject});
                })
                    .then(token => {
                        originalRequest.headers["Authorization"] = "Bearer " + token;
                        return auto(originalRequest);
                    })
                    .catch(err => Promise.reject(err));
            }

            isRefreshing = true;

            try {
                const refreshRes = await auto.post("/auth/refresh");
                const newAccessToken = refreshRes.data.accessToken;

                // 새 access token 저장 (zustand or localStorage 등)
                // 예: store.setAccessToken(newAccessToken)
                const {tokenRefresh} = useMemberStore();
                tokenRefresh(newAccessToken);

                processQueue(null, newAccessToken);

                originalRequest.headers["Authorization"] = "Bearer " + newAccessToken;
                return auto(originalRequest);
            } catch (refreshError) {
                processQueue(refreshError, null);

                // refresh 도 401이면 로그인 페이지로 이동
                // logout();
                return Promise.reject(refreshError);
            } finally {
                isRefreshing = false;
            }
        }

        return Promise.reject(error);
    }
);

export default auto;
