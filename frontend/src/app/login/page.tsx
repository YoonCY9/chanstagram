"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import {logIn} from "@/app/login";

export default function LoginPage() {
    const [formData, setFormData] = useState({ loginId: "", password: "" });
    const router = useRouter();

    // 입력값 변경 핸들러
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    // 로그인 요청 핸들러
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); // 기본 제출 방지

        try {
            const response = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                const data = await response.json();
                alert("로그인 성공!");

                // 로그인 후 홈으로 이동
                router.push("/home");
            } else {
                alert("로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
            }
        } catch (error) {
            console.error("로그인 중 오류 발생:", error);
            alert("오류가 발생했습니다.");
        }
    };

    // 회원가입 페이지로 이동
    const goToSignupPage = () => {
        router.push("/signup");
    };

    return (
        <div className="flex items-center justify-center h-screen bg-gradient-to-r from-indigo-500 to-purple-600">
            <div className="bg-white p-8 rounded-xl shadow-xl w-96 max-w-sm">
                <div className="text-center mb-6">
                    <h1 className="text-4xl font-bold text-gray-700" style={{ fontFamily: "'Billabong', cursive" }}>
                        Chanstagram
                    </h1> {/* Instagram 스타일 폰트 */}
                    <h1 className="text-2xl font-bold text-gray-700">로그인</h1>
                </div>
                <form onSubmit={handleSubmit} action={logIn} className="space-y-4">
                    <input
                        type="text"
                        name="loginId"
                        id="loginId"
                        placeholder="아이디"
                        value={formData.loginId}
                        onChange={handleChange}
                        className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500"
                    />
                    <input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="비밀번호"
                        value={formData.password}
                        onChange={handleChange}
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
                    <button
                        onClick={goToSignupPage}
                        className="text-indigo-500 hover:underline"
                    >
                        회원가입
                    </button>
                </div>
            </div>
        </div>
    );
}
