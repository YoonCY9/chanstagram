"use client";
import { useState, useEffect } from "react";
import { cookies } from "next/headers";
import { UserResponse } from "@/app/profile/[nickname]/page";
import { useRouter, useParams } from "next/navigation";
import { fetchProfile } from "@/app/api";
import LogoutButton from "@/components/LogoutButton";
import DeleteAccountButton from "@/components/DeleteAccountButton";

interface ProfileHeaderProps {
  userDetail: UserResponse;
  token: string;
}

export default function ProfileHeader({
  userDetail,
  token,
}: ProfileHeaderProps) {
  const [isFollowing, setIsFollowing] = useState(userDetail.following);
  const [loggedInUserNickname, setLoggedInUserNickname] = useState("");
  const router = useRouter();
  const { nickname } = useParams(); // URL에서 현재 프로필의 nickname을 가져옴

  useEffect(() => {
    async function fetchProfileAsync() {
      const response = await fetch("http://localhost:8080/me", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error(`Failed to fetch profile: ${response.statusText}`);
      }
      const profile = await response.json();
      setLoggedInUserNickname(profile.nickName);
    }

    fetchProfileAsync();
  }, [token]);

  const handleFollowClick = async () => {
    if (token) {
      try {
        await followUser(userDetail.nickName, token);
        setIsFollowing(!isFollowing); // 팔로우 상태 변경
        router.refresh(); // 페이지 갱신
      } catch (error) {
        console.error("팔로우 실패:", error);
      }
    }
  };

  const followUser = async (nickname: string, token: string) => {
    const response = await fetch(`http://localhost:8080/follows/${nickname}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("팔로우 실패");
    }
  };

  return (
    <header className="flex items-center mb-11">
      {/* 프로필 이미지 */}
      <div className="w-40 h-40 rounded-full overflow-hidden mr-24">
        <img
          src={userDetail.profileImage}
          alt="Profile"
          className="w-full h-full object-cover scale-125"
        />
      </div>

      {/* 사용자 이름과 버튼 */}
      <div>
        <div className="flex items-center mb-5">
          <h1 className="text-2xl font-light mr-5">{userDetail.nickName}</h1>

          {/* 자기 자신의 프로필일 때 "회원수정" 버튼, 다른 사람의 프로필일 때 "팔로우" 버튼 */}
          {nickname === loggedInUserNickname ? (
            <div className="flex flex-col items-start space-y-4">
              {/* 회원수정 버튼 */}
              <button
                onClick={() => router.push(`/users`)} // 회원수정 페이지로 이동
                className="px-4 py-2 bg-blue-500 text-white font-semibold rounded-full hover:bg-blue-600 transition duration-300 w-full"
              >
                회원수정
              </button>

              {/* 로그아웃 및 계정 삭제 버튼 */}
              <div className="flex flex-col space-y-4 w-full">
                <LogoutButton />
                <DeleteAccountButton />
              </div>
            </div>
          ) : (
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
          )}
        </div>
      </div>
    </header>
  );
}
