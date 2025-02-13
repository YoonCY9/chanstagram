"use client";

import { useRouter } from "next/navigation";

export default function DeleteAccountButton() {
  const router = useRouter();

  const handleDeleteAccount = async () => {
    // 탈퇴 전 사용자 확인
    if (!confirm("정말 회원 탈퇴하시겠습니까? 이 작업은 복구할 수 없습니다.")) {
      return;
    }

    try {
      // API 라우트 호출 (HttpOnly 쿠키 전송을 위해 credentials 옵션 포함)
      const response = await fetch("/api/deleteUser", {
        method: "DELETE",
        credentials: "include",
      });
      if (!response.ok) {
        console.error("회원 탈퇴 실패:", response.statusText);
        alert("회원 탈퇴에 실패했습니다.");
        return;
      }
      // 탈퇴 성공 시, 회원가입 페이지 등으로 리다이렉트
      router.push("/signup");
    } catch (error) {
      console.error("회원 탈퇴 중 오류 발생:", error);
      alert("회원 탈퇴 중 오류가 발생했습니다.");
    }
  };

  return (
    <button
      onClick={handleDeleteAccount}
      className="flex items-center gap-2 px-4 py-2 bg-red-600 text-white font-semibold rounded-full shadow hover:bg-red-700 transition duration-300"
    >
      {/* 휴지통 아이콘 (Heroicons) */}
      <svg
        xmlns="http://www.w3.org/2000/svg"
        className="w-5 h-5"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth={2}
          d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5-4h4m-4 0a1 1 0 00-1 1v1h6V4a1 1 0 00-1-1m-4 0h4"
        />
      </svg>
      회원 탈퇴
    </button>
  );
}
