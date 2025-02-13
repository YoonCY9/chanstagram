import { useEffect, useState } from "react";
import ProfileHeader from "./ProfileHeader";
import ProfileTabs from "./ProfileTabs";
import ProfilePosts from "./ProfilePosts";
import BackButton from "../BackButton";
import ProfileInfo from "./ProfileInfo";
import { useParams } from "next/navigation";
import { cookies } from "next/headers";
import LogoutButton from "@/components/LogOutButton";

// UserResponse와 PostsByNickName 타입 정의
export interface UserResponse {
  nickName: string;
  profileImage: string;
  following: boolean;
}

export interface PostsByNickName {
  postId: number;
  content: string;
  commentCount: number;
  imageUrl: string[];
  user: UserResponse; // 게시물의 user 정보
  createdTime: string;
  updatedTime: string;
}
export interface FollowResponse {
  userName: string;
  nickName: string;
  profileImage: string;
}

const apiBaseUrl = `http://localhost:8080`;

// 게시물 데이터를 가져오는 함수
async function fetchPostsByNickName(
  nickname: string,
): Promise<PostsByNickName[]> {
  const response = await fetch(`${apiBaseUrl}/posts/${nickname}`);

  if (!response.ok) {
    throw new Error("게시물을 가져오는 데 실패했습니다.");
  }
  const data = await response.json();
  return data;
}
async function fetchUsersByNickName(nickname: string): Promise<UserResponse> {
  const cookieStore = await cookies();
  const token = cookieStore.get("token")?.value;
  const response = await fetch(`${apiBaseUrl}/users/nickName/${nickname}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error("유저 정보를 가져오는 데 실패했습니다.");
  }
  const data = await response.json();
  console.log(data);
  return data;
}
async function fetchFollowersByNickName(
  nickname: string,
): Promise<FollowResponse[]> {
  const response = await fetch(`${apiBaseUrl}/follows/followers/{nickName}`);

  if (!response.ok) {
    throw new Error("팔로워리스트를 가져오는 데 실패했습니다.");
  }
  const data = await response.json();

  return data;
}
async function fetchFollowingByNickName(
  nickname: string,
): Promise<FollowResponse[]> {
  const response = await fetch(`${apiBaseUrl}/follows/followees/{nickName}`);

  if (!response.ok) {
    throw new Error("팔로잉리스트를 가져오는 데 실패했습니다.");
  }
  const data = await response.json();

  return data;
}

export default async function ProfilePage(props: {
  params: Promise<{ nickname: string }>;
}) {
  const nickname = (await props.params).nickname;
  const userProfile = await fetchUsersByNickName(nickname);
  const postsData = await fetchPostsByNickName(nickname);
  const followersData = await fetchFollowersByNickName(nickname);
  const followingData = await fetchFollowingByNickName(nickname);
  const cookieStore = await cookies();
  const token = cookieStore.get("token")?.value;

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <BackButton />
      <LogoutButton />
      {/* ProfileHeader 컴포넌트는 항상 렌더링하며, userDetail을 게시물에서 가져옵니다. */}
      <ProfileHeader userDetail={userProfile} token={token} />
      <ProfileInfo
        postCount={postsData.length}
        followers={followersData}
        following={followingData}
      />
      <ProfileTabs />
      <ProfilePosts posts={postsData} /> {/* 게시물 리스트 전달 */}
    </div>
  );
}
