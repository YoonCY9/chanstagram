"use client";

import { useEffect, useState } from "react";
import ProfileHeader from "./ProfileHeader";
import ProfileTabs from "./ProfileTabs";
import ProfilePosts from "./ProfilePosts";
import BackButton from "../BackButton";
import ProfileInfo from "./ProfileInfo";
import { useParams } from "next/navigation";

// UserResponse와 PostsByNickName 타입 정의
export interface UserResponse {
  nickName: string;
  profileImage: string;
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
  console.log("nickname " + nickname);
  const response = await fetch(`${apiBaseUrl}/users/${nickname}`);
  console.log("nickname " + nickname);

  if (!response.ok) {
    throw new Error("게시물을 가져오는 데 실패했습니다.");
  }
  const data = await response.json();
  console.log(data);
  return data;
}

export default function ProfilePage() {
  const [userDetail, setUserDetail] = useState<UserResponse | null>(null);
  const [posts, setPosts] = useState<PostsByNickName[]>([]); // 게시물 상태
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const params = useParams<{ nickname: string }>(); // URL에서 nickname 파라미터 가져오기

  useEffect(() => {
    const nickname = params.nickname;
    if (nickname) {
      const fetchData = async () => {
        try {
          // 사용자 정보만 가져오기
          const userProfile = await fetchUsersByNickName(nickname);

          setUserDetail(userProfile); // userDetail을 설정

          // 게시물 데이터 가져오기 (게시물이 없어도 상관없음)
          const postsData = await fetchPostsByNickName(nickname);
          setPosts(postsData); // 게시물 데이터 설정

          setIsLoading(false); // 로딩 완료
        } catch (err) {
          setError("데이터를 가져오는 데 실패했습니다.");
          setIsLoading(false);
        }
      };

      fetchData();
    }
  }, []); // `nickname`이 변경될 때마다 데이터 새로 가져오기

  if (isLoading) return <div>로딩 중...</div>;
  if (error) return <div>오류: {error}</div>;

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <BackButton />
      {/* ProfileHeader 컴포넌트는 항상 렌더링하며, userDetail을 게시물에서 가져옵니다. */}
      <ProfileHeader userDetail={userDetail} />
      <ProfileInfo postCount={posts.length} /> {/* 게시물 갯수 전달 */}
      <ProfileTabs />
      <ProfilePosts posts={posts} /> {/* 게시물 리스트 전달 */}
    </div>
  );
}
