import {create} from "zustand";

export const useMemberStore = create(set => ({
    isLogin: false,
    token: null,
    email: null,

    setIsLogin: (flag) => set({isLogin: flag}),

    login: (tk) => {
        tk.split('.'); // 헤더, 페이로드, 서명
        const payload = JSON.parse(atob(tk.split('.')[1]));
        const name = payload.email;

        set({isLogin: true, email: name, token: tk})
    },
    tokenRefresh: (tk) => {
        set({token: tk})
    },
    logout: () => set({isLogin: true, email: null, token: null}),
}));
