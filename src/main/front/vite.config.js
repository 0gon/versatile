import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import path from 'path';

// https://vite.dev/config/
export default defineConfig({
    plugins: [react(), tailwindcss()],
    resolve: {
        alias: {
            "@": path.resolve(__dirname, './src')
        }
    },
    server: {
        port: 5173,
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                secure: false,
                changeOrigin: true,
                // configure: (proxy, _options) => {
                //     proxy.on("proxyReq", function (proxyReq, req) {
                //         // 쿠키를 포함하여 요청을 전송
                //         proxyReq.setHeader("Cookie", req.headers.cookie || "");
                //     });
                // },
                // cookieDomainRewrite: "localhost", // 쿠키 도메인 재작성
                // cookiePathRewrite: "/", // 쿠키 경로 재작성
            },
        },
        host: true,
        allowedHosts: true,
    },
})
