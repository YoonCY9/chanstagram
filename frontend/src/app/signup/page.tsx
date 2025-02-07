"use client"
import {useState} from "react";

export default function SignupPage() {
    // 입력값을 저장할 상태 변수들
    const [formData, setFormData] = useState({
        username: "",
        nickName: "",
        loginId: "",
        password: "",
        birth: "",
        content: "",
        profileImage: "",
        phoneNumber: "",
        gender: ""
    });

    // 입력값 변경 핸들러
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    // 회원가입 요청 핸들러
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); // 기본 폼 제출 방지

        try {
            // birth 값이 비어있거나 유효하지 않으면 null 처리
            let formattedBirth = null;
            if (formData.birth) {
                const date = new Date(formData.birth);
                if (!isNaN(date.getTime())) {
                    formattedBirth = date.toISOString().split("T")[0];
                }
            }

            const formattedData = {
                ...formData,
                birth: formattedBirth, // 변환된 생년월일
                gender: formData.gender === "남성" ? "Man" : "Woman", // 한글 → 영문 변환
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
            } else {
                alert("회원가입 실패!");
            }
        } catch (error) {
            console.error("회원가입 중 오류 발생:", error);
            alert("오류가 발생했습니다.");
        }
    };


    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div className="bg-white p-8 rounded-xl shadow-lg w-96">
                <h1 className="text-2xl font-bold text-center mb-6">회원가입</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <input type="text" name="username" placeholder="이름" value={formData.username}
                           onChange={handleChange} className="w-full p-3 border rounded-lg"/>
                    <input type="text" name="nickName" placeholder="사용자 이름(닉네임)" value={formData.nickName}
                           onChange={handleChange} className="w-full p-3 border rounded-lg"/>
                    <input type="text" name="loginId" placeholder="로그인 아이디" value={formData.loginId}
                           onChange={handleChange} className="w-full p-3 border rounded-lg"/>
                    <input type="password" name="password" placeholder="비밀번호" value={formData.password}
                           onChange={handleChange} className="w-full p-3 border rounded-lg"/>
                    <input type="text" name="birth" placeholder="생년월일 (YYYY-MM-DD)" value={formData.birth}
                           onChange={handleChange} className="w-full p-3 border rounded-lg"/>
                    <input type="text" name="content" placeholder="자기소개" value={formData.content}
                           onChange={handleChange} className="w-full p-3 border rounded-lg"/>
                    <input type="text" name="profileImage" placeholder="프로필 이미지 URL" value={formData.profileImage}
                           onChange={handleChange} className="w-full p-3 border rounded-lg"/>
                    <input type="text" name="phoneNumber" placeholder="핸드폰번호" value={formData.phoneNumber}
                           onChange={handleChange} className="w-full p-3 border rounded-lg"/>

                    {/* 성별 선택 (라디오 버튼) */}
                    <div className="flex justify-between">
                        <label>
                            <input type="radio" name="gender" value="Man" checked={formData.gender === "Man"}
                                   onChange={handleChange}/>
                            남성
                        </label>
                        <label>
                            <input type="radio" name="gender" value="Woman" checked={formData.gender === "Woman"}
                                   onChange={handleChange}/>
                            여성
                        </label>
                    </div>

                    <button type="submit"
                            className="w-full bg-blue-500 text-white py-3 rounded-lg font-semibold hover:bg-blue-600 transition">
                        회원가입
                    </button>
                </form>
            </div>
        </div>
    );
}
