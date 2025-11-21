import {RouterProvider} from "react-router-dom";
import router from "./router/router.jsx";
import './App.css'
import {useMemberStore} from "@/store/useMemberStore.jsx";
import {useEffect} from "react";
import axios from "axios";

function App() {

    const {isLogin} = useMemberStore();

    /**
     * 엑세스 토큰 재발급
     */
    useEffect(() => {
        axios.post("http://localhost:8080/api/v1/auth/refresh", null, {
            withCredentials: true
        })
            .then(response => {
                const {accessToken} = response.data;

            }).catch(error => {
                console.log(error)
        })
    }, []);

    useEffect(() => {
        if(isLogin) {

        }
    }, [isLogin]);

    return (
        <RouterProvider router={router}/>
    )
}

export default App

if (import.meta.env.DEV) {
    window.useTokenStore = function () {
        return useMemberStore.getState()
    }


}