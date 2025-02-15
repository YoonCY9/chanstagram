import ProfileHeader from "./ProfileHeader";
import ProfileTabs from "./ProfileTabs";
import ProfilePosts from "./ProfilePosts";
import BackButton from "../BackButton";
import ProfileInfo from "./ProfileInfo";
import { useParams } from "next/navigation";
import { cookies } from "next/headers";
import LogoutButton from "@/components/LogoutButton";
import DeleteAccountButton from "@/components/DeleteAccountButton";

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
  return data;
}
async function fetchFollowersByNickName(
  nickname: string,
): Promise<FollowResponse[]> {
  const response = await fetch(`${apiBaseUrl}/follows/followers/${nickname}`);

  if (!response.ok) {
    throw new Error("팔로워리스트를 가져오는 데 실패했습니다.");
  }
  const data = await response.json();

  return data;
}
async function fetchFollowingByNickName(
  nickname: string,
): Promise<FollowResponse[]> {
  const response = await fetch(`${apiBaseUrl}/follows/followees/${nickname}`);

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
      <div className="flex justify-end px-4 py-2"></div>
      <ProfileHeader userDetail={userProfile} token={token} />
      <ProfileInfo
        nickname={userProfile.nickName}
        postCount={postsData.length}
        followers={followersData}
        following={followingData}
      />
      <ProfileTabs />
      <ProfilePosts posts={postsData} /> {/* 게시물 리스트 전달 */}
    </div>
  );
}
