import {useState} from "react";
import {useNavigate} from "react-router-dom";
import BasicLayout from "@/layouts/BasicLayout";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faEye, faEyeSlash} from '@fortawesome/free-solid-svg-icons';
import {post} from "@/util/fetch.jsx";
import ResultTypeCode from "@/util/resultTypeCode.jsx";
import {useMemberStore} from "@/store/useMemberStore.jsx";




const Login = () => {
    const navigate = useNavigate();
    const {setIsLogin} = useMemberStore();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [responseMessage, setResponseMessage] = useState("");

    // 요청과 응답 처리
    const handleSubmit = (e) => {
        e.preventDefault();

        // 서버 요청 자리
        if (email === "" || password === "") {
            setResponseMessage("아이디와 비밀번호를 모두 입력해주세요.");
            return;
        }


        /** @type {Promise<Response<{token: string, username: string}>>} */
        let tmp = post("/api/v1/members/login", {email, password});
        tmp.then(rsp => {
            if (rsp.resultTypeCode === ResultTypeCode.SUCCESS) {
                setIsLogin(true);
                navigate('/');
            } else {
                setResponseMessage(rsp.message || "로그인 실패.");
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
                        <h2 className="text-2xl font-bold mb-10 text-center">로그인</h2>

                        {/* 이메일 */}
                        <div className="mb-10">
                            <input
                                type="text"
                                placeholder="아이디"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="w-full border-2 border-gray-300 p-2 pl-10 pr-40"
                                required
                            />
                        </div>

                        {/* 비밀번호 + 아이콘 */}
                        <div className="mb-10 relative">
                            <input
                                type={showPassword ? "text" : "password"}
                                placeholder="비밀번호"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                className="w-full border-2 border-gray-300 p-2 pl-10 pr-40"
                                required
                            />

                            <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye}
                                             className="absolute right-5 top-1/2 -translate-y-1/2 text-gray-500 cursor-pointer"
                                             onClick={() => setShowPassword(!showPassword)}/>
                        </div>

                        {/* 응답 메세지 */}
                        <div className="text-center text-[13px] text-blue-500 mb-3">
                            {responseMessage}
                        </div>

                        {/* 버튼 */}
                        <button
                            type="submit"
                            className="w-full bg-blue-500 text-white p-2 hover:bg-blue-600"
                        >
                            로그인
                        </button>

                        <button
                            type="button"
                            className="w-full bg-gray-500 text-white p-2 mt-5 hover:bg-gray-600"
                            onClick={() => navigate('/member/join')}
                        >
                            회원가입
                        </button>

                        <button
                            type="button"
                            className="w-full bg-gray-500 text-white p-2 mt-5 hover:bg-gray-600"
                            onClick={() => alert("not implement. 구글 이메일 전송 연동 예정.")}
                        >
                            아이디 비밀번호 찾기
                        </button>

                    </form>
                </div>
            </main>
        </BasicLayout>
    );
};

export default Login;
