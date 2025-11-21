import {useMemberStore} from "@/store/useMemberStore.jsx";
import {Link} from "react-router-dom";

const Header = () => {

    const {isLogin, email} = useMemberStore();

    return (

        <div>
            <div className="w-full bg-[#f0f0f0]">
                <div className="w-full max-w-1020 m-auto h-33 flex justify-between text-[11px]">
                    <div></div>


                    <div>
                        <ul className="flex list-none">

                            {isLogin ? (<>
                                <li className="pt-10 pr-10">
                                    <p><span>{email}</span>님</p>
                                </li>

                                <li className="pt-10 pr-10">
                                    <Link to="/member/logout">로그아웃</Link>
                                </li>
                            </>) : (
                                <li className="pt-10 pr-10">
                                    <Link to="/member/login">로그인</Link>
                                </li>
                            )}


                            <li className="pt-10 pr-10">
                                <Link to="/member/join">회원가입</Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div className="max-w-1020 m-auto">
                <div className="w-full flex">
                    <button
                        className="w-110 h-110 bg-[#346aff] flex flex-col justify-center items-center gap-6 cursor-pointer">
                        <div className="w-3/10 h-4 bg-white"></div>
                        <div className="w-3/10 h-4 bg-white"></div>
                        <div className="w-3/10 h-4 bg-white"></div>
                    </button>

                    <div className="grow">
                        <div className="mt-30 ml-20 flex justify-between items-center">
                            <div className="mr-10">
                                <Link to="/" className="text-4xl font-bold align-super">
                                    gonpang
                                </Link>
                            </div>


                            <div className="grow h-40">
                                <div className="size-full border-2 border-[#4285f4] flex">
                                    <input className="grow px-10 focus:outline-none" type="text" placeholder="상품 검색"/>
                                    <button className="bg-[#4285f4] text-white cursor-pointer">검색</button>
                                </div>
                            </div>


                            <div>
                                <ul className="flex">
                                    <li className="mx-10">
                                        <Link to="/member/me">내정보</Link>
                                    </li>


                                    <li>
                                        <Link to="">장바구니</Link>
                                    </li>
                                </ul>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>


    )
}

export default Header;