"use client";

import { useRouter } from "next/navigation";
import React, {useState} from "react";

export default function creatPost() {
    const [postData,setPostData] = useState({
        content: "",
        imageUrl: [] as string[],
        });
    const router = useRouter();

    const getCookie = (name: string): string | null => {
        const cookies = document.cookie.split("; "); // 쿠키를 세미콜론으로 분리
        for (const cookie of cookies) {
            const [key, value] = cookie.split("="); // key=value 형태 분리
            if (key === name) {
                return value;
            }
        }
        return null;
    };

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setPostData({
            ...postData,
            [e.target.name]: e.target.value,
        });
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            const file = e.target.files[0];
            const blobUrl = URL.createObjectURL(file); // blob URL 생성
            setPostData({
                ...postData,
                imageUrl: [...postData.imageUrl, blobUrl], // blob URL을 리스트에 추가
            });
        }
    };

    const handleUpload = async () => {
        // 쿠키에서 토큰 가져오기
        const token = getCookie("token");
        if (!token) {
            alert("로그인이 필요합니다.");
            router.push("/login"); // 토큰이 없으면 로그인 페이지로 리다이렉트
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/posts", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
                },
                body: JSON.stringify(postData), // JSON 형태로 전송
            });

            if (response.ok) {
                alert("업로드 성공");
                router.push("/home");
            } else {
                alert("업로드 실패");
            }
        } catch (error) {
            console.error("업로드 중 오류 발생:", error);
            alert("업로드 중 문제가 발생했습니다.");
        }
    };



    return (
        <>
            <header className="flex items-center justify-between px-4 py-2 bg-white shadow-md">
                <button onClick={() => router.push("/home")} className="text-xl font-bold">
                    Chanstagram
                </button>
            </header>
            <div>
                <form className="mt-4">
          <textarea
              placeholder="내용을 입력해주세요."
              name="content"
              value={postData.content}
              onChange={handleChange}
              className="border border-gray-400 w-full p-4 h-64"
          />
                </form>
                <form className="p-6 border border-gray-600">
                    <input type="file" accept="image/*" onChange={handleFileChange} />
                </form>
                <button
                    onClick={handleUpload}
                    className="mt-4 flex items-center gap-2 px-4 py-2 bg-blue-500 text-white shadow-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-400 transition"
                >
                    업로드
                </button>
            </div>
        </>
    );
}