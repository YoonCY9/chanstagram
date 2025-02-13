"use client";

import { useState, useEffect } from "react";
import { cookies } from "next/headers";
import { UserResponse } from "@/app/profile/[nickname]/page";
import { useRouter } from "next/navigation";

interface ProfileHeaderProps {
  userDetail: UserResponse;
  isFollowing: boolean;
  setIsFollowing: (following: boolean) => void;
  token: string;
}

export default function ProfileHeader({
  userDetail,
  token,
}: ProfileHeaderProps) {
  const [isFollowing, setIsFollowing] = useState(userDetail.following);
  const router = useRouter();
  const handleFollowClick = async () => {
    if (token) {
      try {
        await followUser(userDetail.nickName, token);
        setIsFollowing(!isFollowing); // 팔로우 상태 변경
        router.refresh();
      } catch (error) {
        console.error("팔로우 실패:", error);
      }
    }
  };
  async function followUser(nickname: string, token: string) {
    const response = await fetch(`http://localhost:8080/follows/${nickname}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token, // 헤더에 토큰 추가
      },
    });

    if (!response.ok) {
      throw new Error("팔로우 실패");
    }

    // return response.json(); // 성공적으로 팔로우가 완료되면 응답 반환
  }
  return (
    <header className="flex items-center mb-11">
      {/* 프로필 이미지 */}
      <div className="w-36 h-36 rounded-full overflow-hidden mr-24">
        <img
          src={userDetail.profileImage}
          alt="Profile"
          className="w-full h-full object-cover"
        />
      </div>

      {/* 사용자 이름과 팔로우 버튼 */}
      <div>
        <div className="flex items-center mb-5">
          <h1 className="text-2xl font-light mr-5">{userDetail.nickName}</h1>
          <button
            onClick={handleFollowClick}
            className={`px-2 py-1 rounded text-sm font-semibold ${
              isFollowing
                ? "bg-gray-200 text-gray-800"
                : "bg-blue-500 text-white hover:bg-blue-600"
            }`}
          >
            {isFollowing ? "Following" : "Follow"}
          </button>
        </div>
      </div>
    </header>
  );
}
