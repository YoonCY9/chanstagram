// app/components/InstagramLogoutButton.tsx
"use client";

import { useRouter } from "next/navigation";

export default function LogoutButton() {
  const router = useRouter();

  const handleLogout = async () => {
    try {
      const response = await fetch("/api/logout", {
        method: "POST",
      });
      if (!response.ok) {
        console.error("Logout failed:", response.statusText);
      }
    } catch (error) {
      console.error("Error during logout:", error);
    } finally {
      // 로컬 스토리지의 토큰 삭제 (있다면)
      localStorage.removeItem("token");
      // 로그아웃 후 로그인 페이지로 리다이렉트
      router.push("/login");
    }
  };

  return (
    <button
      onClick={handleLogout}
      className="flex items-center gap-2 px-4 py-2 bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 text-white font-semibold rounded-full shadow hover:opacity-90 transition duration-300"
    >
      {/* 로그아웃 아이콘 (Heroicons 참고) */}
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
