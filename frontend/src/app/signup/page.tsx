"use client";

import {useState} from "react";
import {useRouter} from "next/navigation";

export default function SignupPage() {
    const [formData, setFormData] = useState({
        userName: "",
        nickName: "",
        loginId: "",
        password: "",
        birth: "",
        content: "",
        profileImage: "",
        phoneNumber: "",
        gender: ""
    });

    const router = useRouter();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const formattedData = {
                ...formData,
                birth: formData.birth ? new Date(`${formData.birth}T00:00:00`).toISOString().split("T")[0] : null,
            };


            const response = await fetch("http://localhost:8080/users", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formattedData),
            });

            if (response.ok) {
                alert("회원가입 성공!");
                router.push("/login"); // 회원가입 성공 후 로그인 페이지로 이동
            } else {
                alert("회원가입 실패!");
            }
        } catch (error) {
            console.error("회원가입 중 오류 발생:", error);
            alert("오류가 발생했습니다.");
        }
    };

    return (
        <div
            className="flex items-center justify-center h-screen bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500">
            <div className="bg-white p-10 rounded-2xl shadow-xl w-[450px] max-w-md">


                <h1 className="text-5xl font-bold text-center mb-6 text-gray-800"
                    style={{fontFamily: "'Billabong', cursive"}}>
                    Chanstagram
                </h1>

                <form onSubmit={handleSubmit} className="space-y-4">
                    <input type="text" name="userName" placeholder="이름" value={formData.userName}
                           onChange={handleChange}
                           className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400"
                    />
                    <input type="text" name="nickName" placeholder="사용자 이름(닉네임)" value={formData.nickName}
                           onChange={handleChange}
                           className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400"
                    />
                    <input type="text" name="loginId" placeholder="로그인 아이디" value={formData.loginId}
                           onChange={handleChange}
                           className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400"
                    />
                    <input type="password" name="password" placeholder="비밀번호" value={formData.password}
                           onChange={handleChange}
                           className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400"
                    />
                    <input type="text" name="birth" placeholder="생년월일 (YYYY-MM-DD)" value={formData.birth}
                           onChange={handleChange}
                           className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400"
                    />
                    {/*<input type="text" name="content" placeholder="자기소개" value={formData.content}*/}
                    {/*       onChange={handleChange}*/}
                    {/*       className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400"*/}
                    {/*/>*/}
                    {/*<input type="text" name="profileImage" placeholder="프로필 이미지 URL" value={formData.profileImage}*/}
                    {/*       onChange={handleChange}*/}
                    {/*       className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400"*/}
                    {/*/>*/}
                    <input type="text" name="phoneNumber" placeholder="핸드폰번호" value={formData.phoneNumber}
                           onChange={handleChange}
                           className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-400"
                    />

                    {/* 성별 선택 */}
                    <div className="flex justify-between text-gray-600">
                        <label className="flex items-center">
                            <input type="radio" name="gender" value="Man" checked={formData.gender === "Man"}
                                   onChange={handleChange}
                                   className="mr-2"
                            />
                            남성
                        </label>
                        <label className="flex items-center">
                            <input type="radio" name="gender" value="Woman" checked={formData.gender === "Woman"}
                                   onChange={handleChange}
                                   className="mr-2"
                            />
                            여성
                        </label>
                    </div>

                    <button type="submit"
                            className="w-full bg-blue-500 text-white py-3 rounded-lg font-semibold hover:bg-blue-600 transition">
                        회원가입
                    </button>
                </form>

                {/* 로그인 페이지 이동 버튼 */}
                <div className="flex justify-center mt-4">
                    <p className="text-gray-600">이미 계정이 있나요?</p>
                    <button onClick={() => router.push("/login")} className="text-blue-500 ml-2 hover:underline">
                        로그인
                    </button>
                </div>
            </div>
        </div>
    );
}
