"use client";

import { useEffect, useState } from "react";
import ProfileHeader from "./ProfileHeader";
import ProfileInfo from "./ProfileInfo";
import ProfileTabs from "./ProfileTabs";
import ProfilePosts from "./ProfilePosts";
import BackButton from "../BackButton";

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

const apiBaseUrl = `http://localhost:8080`;

// 게시물 데이터를 가져오는 함수
async function fetchPostsByNickName(nickname: string): Promise<PostsByNickName[]> {
  const response = await fetch(`${apiBaseUrl}/posts/${nickname}`);
  if (!response.ok) {
    throw new Error('게시물을 가져오는 데 실패했습니다.');
  }
  return response.json();
}

export default function ProfilePage({ params }: { params: { nickname: string } }) {
  const [userDetail, setUserDetail] = useState<UserResponse | null>(null);
  const [posts, setPosts] = useState<PostsByNickName[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const nickname = params.nickname;

    const fetchData = async () => {
      try {
        // 게시물 데이터를 가져옴
        const postsData = await fetchPostsByNickName(nickname);

        // 게시물 데이터에서 첫 번째 게시물의 user 정보를 가져와서 사용자 프로필로 설정
        if (postsData.length > 0) {
          const userProfile = postsData[0].user; // 첫 번째 게시물의 user 정보를 가져옴
          setUserDetail(userProfile);
        }

        setPosts(postsData);
        setIsLoading(false);
      } catch (err) {
        setError("데이터를 가져오는 데 실패했습니다.");
        setIsLoading(false);
      }
    };

    fetchData();
  }, [params.nickname]);

  if (isLoading) return <div>로딩 중...</div>;
  if (error) return <div>오류: {error}</div>;
  if (posts.length === 0) return <div>게시물이 없습니다.</div>;

  return (
      <div className="max-w-4xl mx-auto px-4 py-8">
        <BackButton />
        {userDetail && <ProfileHeader userDetail={userDetail} />}
        <ProfileInfo postCount={posts.length} />
        <ProfileTabs />
        <ProfilePosts posts={posts} />
      </div>
  );
}
