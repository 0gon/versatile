import { Suspense, lazy } from "react";
import { createBrowserRouter } from "react-router-dom";


const Loading = <div>Loading...</div>

const Index = lazy(() => import("@/pages/Index"))
const MemberLogin = lazy(() => import("@/pages/member/Login"))
const MemberJoin = lazy(() => import("@/pages/member/Join"))


const router = createBrowserRouter([
    {
        path: "",
        element: <Suspense fallback={Loading}><Index /></Suspense>
    },
    {
        path: "/member/login",
        element: <Suspense fallback={Loading}><MemberLogin /></Suspense>
    },
    {
        path: "/member/join",
        element: <Suspense fallback={Loading}><MemberJoin /></Suspense>
    }
    // {
    //   path: "todo",
    //   element: <Suspense fallback={Loading}><TodoIndex /></Suspense>,
    //   children: todoRouter(),
    // },
])

export default router;