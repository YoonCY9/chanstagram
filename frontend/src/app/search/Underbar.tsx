"use client";
import { useState, useEffect } from "react";
import Link from "next/link";
import { Home, Search, PlusCircle, User } from "lucide-react";

// JWT 토큰에서 payload 부분을 디코딩하여 사용자 정보를 가져오는 함수
function decodeJwt(token: string) {
    const base64Url = token.split('.')[1]; // JWT의 두 번째 부분이 payload
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // base64 변환
    const jsonPayload = decodeURIComponent(
        atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join('')
    );
    return JSON.parse(jsonPayload); // 디코딩된 JSON 객체 반환
}

export default function Underbar() {
    const [nickname, setNickname] = useState<string | null>(null);

    useEffect(() => {
        const token = localStorage.getItem("token"); // 토큰을 localStorage에서 가져오기
        if (token) {
            const decodedToken = decodeJwt(token); // 토큰을 디코딩하여 사용자 정보 추출
            setNickname(decodedToken.nickName); // 토큰에서 닉네임을 추출하여 상태에 저장
        }
    }, []);

    return (
        <div className="fixed bottom-0 left-1/2 transform -translate-x-1/2 max-w-[480px] w-full bg-white border-t shadow-md flex justify-around p-3">
            <Link href="/home" className="flex flex-col items-center text-gray-600">
                <Home size={24} />
                <span className="text-xs">홈</span>
            </Link>
            <Link href="/search" className="flex flex-col items-center text-gray-600">
                <Search size={24} />
                <span className="text-xs">검색</span>
            </Link>
            <Link href="/createpost" className="flex flex-col items-center text-gray-600">
                <PlusCircle size={24} />
                <span className="text-xs">포스트</span>
            </Link>
            <Link
                href={`/profile/${nickname}`}
                className="flex flex-col items-center text-gray-600"
            >
                <User size={24} />
                <span className="text-xs">프로필</span>
            </Link>
        </div>
    );
}
