// app/components/LogoutButton.tsx
"use client";

import { useRouter } from "next/navigation";

export default function LogoutButton() {
  const router = useRouter();

  const handleLogout = async () => {
    try {
      // 만약 쿠키 기반 인증을 사용 중이라면, 백엔드 로그아웃 API를 호출하여 쿠키를 삭제할 수 있습니다.
      // 예시:
      await fetch("/api/logout", { method: "POST" });
    } catch (error) {
      console.error("Error during logout:", error);
    } finally {
      // 로컬 스토리지에 저장된 토큰이 있다면 삭제
      localStorage.removeItem("token");

      // 로그아웃 후 리다이렉트 (예: 로그인 페이지)
      router.push("/login");
    }
  };

  return (
    <button
      onClick={handleLogout}
      className="flex items-center gap-2 text-red-500 font-semibold text-sm hover:underline focus:outline-none"
    >
      {/* 간단한 로그아웃 아이콘 (Heroicons 참고) */}
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
          d="M17 16l4-4m0 0l-4-4m4 4H7"
        />
      </svg>
      로그아웃
    </button>
  );
}
