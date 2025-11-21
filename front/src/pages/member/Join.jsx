import { useState } from "react";
import { useNavigate } from "react-router-dom";
import BasicLayout from "@/layouts/BasicLayout";
import {post} from "@/util/fetch.jsx";
import ResultTypeCode from "@/util/resultTypeCode.jsx";

const Join = () => {
    const navi = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordCheck, setPasswordCheck] = useState("");

    const [emailMsg, setEmailMsg] = useState("");
    const [passwordCheckMsg, setPasswordCheckMsg] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();

        // 이메일 검증
        // if (!email.includes("@")) {
        //     setEmailMsg("이메일 형식이 아닙니다.");
        //     return;
        // } else {
        //     setEmailMsg("");
        // }

        // 비밀번호 확인 검증
        if (password !== passwordCheck) {
            setPasswordCheckMsg("비밀번호가 일치하지 않습니다.");
            return;
        } else {
            setPasswordCheckMsg("");
        }

        // 서버 요청(예시)
        console.log({ email, password });
        post("/api/v1/members/join", {email, password})
            .then(rsp => {
                switch (rsp.resultTypeCode) {
                    case ResultTypeCode.SUCCESS:
                        alert(rsp.message);
                        navi('/member/login');
                        break;

                    case ResultTypeCode.ALREADY_JOIN_EMAIL:
                        setEmailMsg(rsp.message);
                        break;

                    default:
                        console.error("not implement resultTypeCode: " + rsp.resultTypeCode);
                }
            })

    };

    return (
        <BasicLayout>
            <main className="w-full">
                <div className="w-full max-w-1020 m-auto flex justify-center items-center h-full">

                    <form
                        onSubmit={handleSubmit}
                        className="w-400 h-300 border-2 border-gray-300 p-20 flex flex-col justify-center"
                    >
                        <h2 className="text-2xl font-bold mb-10 text-center">회원가입</h2>

                        <div className="mb-10">
                            <input
                                type="text"
                                placeholder="아이디"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="w-full border-2 border-gray-300 p-2 pl-10 pr-40"
                                required
                            />
                            <div className="text-[10px] text-red-500">{emailMsg}</div>
                        </div>

                        <div className="mb-10">
                            <input
                                type="password"
                                placeholder="비밀번호"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                className="w-full border-2 border-gray-300 p-2 pl-10 pr-40"
                                required
                            />
                        </div>

                        <div className="mb-10">
                            <input
                                type="password"
                                placeholder="비밀번호 확인"
                                value={passwordCheck}
                                onChange={(e) => setPasswordCheck(e.target.value)}
                                className="w-full border-2 border-gray-300 p-2 pl-10 pr-40"
                                required
                            />
                            <div className="text-[10px] text-red-500">{passwordCheckMsg}</div>
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-gray-500 text-white p-2 mt-5 hover:bg-gray-600 cursor-pointer"
                        >
                            가입하기
                        </button>
                    </form>

                </div>
            </main>
        </BasicLayout>
    );
};

export default Join;
