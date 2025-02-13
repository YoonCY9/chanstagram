import Link from "next/link";
import { FollowResponse } from "@/app/profile/[nickname]/page"; // Link 컴포넌트 import

interface ProfileInfoProps {
  postCount: number;
  followers: FollowResponse[];
  following: FollowResponse[];
  nickname: string;
}

export default function ProfileInfo({
  postCount,
  followers,
  following,
  nickname,
}: ProfileInfoProps) {
  return (
    <div className="mb-5">
      <ul className="flex mb-5">
        <li className="mr-10">
          <strong>{postCount}</strong> 게시글
        </li>
        {/* 팔로워 클릭 시 팔로워 페이지로 이동 */}
        <li className="mr-10">
          <Link href={`/profile/${nickname}/followers`} className=" hover">
            <strong>{followers.length}</strong> 팔로워
          </Link>
        </li>
        {/* 팔로잉 클릭 시 팔로잉 페이지로 이동 */}
        <li className="mr-10">
          <Link href={`/profile/${nickname}/following`} className=" hover">
            <strong>{following.length}</strong> 팔로잉
          </Link>
        </li>
      </ul>
      <p>안녕하세요 찬스타그램 소개글입니다!</p>
    </div>
  );
}
