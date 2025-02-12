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
      className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
    >
      로그아웃
    </button>
  );
}
