import { FollowResponse } from "@/app/profile/[nickname]/page";

interface ProfileInfoProps {
  postCount: number;
  followers: FollowResponse[];
  following: FollowResponse[];
}

export default function ProfileInfo({
  postCount,
  followers,
  following,
}: ProfileInfoProps) {
  return (
    <div className="mb-5">
      <ul className="flex mb-5">
        <li className="mr-10">
          <strong>{postCount}</strong> 게시글
        </li>
        <li className="mr-10">
          <strong>{followers.length}</strong> 팔로워
        </li>
        <li className="mr-10">
          <strong>{following.length}</strong> 팔로잉
        </li>
      </ul>
      <p>안녕하세요 찬스타그램 소개글입니다!</p>
    </div>
  );
}
