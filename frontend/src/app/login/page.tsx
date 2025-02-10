import {logIn} from "@/app/login";
import Link from "next/link";

export default function LoginPage() {
    return (
        <div className="flex items-center justify-center h-screen bg-gradient-to-r from-indigo-500 to-purple-600">
            <div className="bg-white p-8 rounded-xl shadow-xl w-96 max-w-sm">
                <div className="text-center mb-6">
                    <h1 className="text-4xl font-bold text-gray-700" style={{fontFamily: "'Billabong', cursive"}}>
                        Chanstagram
                    </h1> {/* Instagram 스타일 폰트 */}
                    <h1 className="text-2xl font-bold text-gray-700">로그인</h1>
                </div>
                <form action={logIn} className="space-y-4">
                    <input
                        type="text"
                        name="loginId"
                        id="loginId"
                        placeholder="아이디"
                        // value={formData.loginId}
                        // onChange={handleChange}
                        className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500"
                    />
                    <input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="비밀번호"
                        // value={formData.password}
                        // onChange={handleChange}
                        className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500"
                    />
                    <button
                        type="submit"
                        className="w-full bg-gradient-to-r from-indigo-500 to-purple-600 text-white py-3 rounded-lg font-semibold hover:bg-indigo-700 transition"
                    >
                        로그인
                    </button>
                </form>

                <div className="flex justify-center mt-6">
                    <Link href={"/signup"}
                          className="text-indigo-500 hover:underline"
                    >
                        회원가입
                    </Link>
                </div>
            </div>
        </div>
    );
}
