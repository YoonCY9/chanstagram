import { FollowResponse } from "@/app/profile/[nickname]/page";
import Link from "next/link";

export default async function Followers({
  params,
}: {
  params: Promise<{ nickname: string }>;
}) {
  const nickname = (await params).nickname;
  const fetchFollowers = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/follows/followers/${nickname}`,
      );
      const data: FollowResponse[] = await response.json();
      return data; // 팔로워 목록 리턴
    } catch (error) {
      console.error("팔로워를 가져오는 데 실패했습니다.", error);
      return []; // 실패 시 빈 배열 리턴
    }
  };

  // 서버에서 데이터를 가져옵니다.
  const followers = await fetchFollowers();

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-semibold mb-4">{nickname}님의 팔로워</h1>
      <ul>
        {followers.length > 0 ? (
          followers.map((follower) => (
            <Link href={`/profile/${follower.nickName}`}>
              <li key={follower.nickName} className="flex items-center mb-4">
                <img
                  src={follower.profileImage}
                  alt={follower.nickName}
                  className="w-12 h-12 rounded-full mr-4"
                />

                <div>
                  <h3 className="font-medium">{follower.nickName}</h3>
                  <p className="text-sm text-gray-500">{follower.userName}</p>
                </div>
              </li>
            </Link>
          ))
        ) : (
          <p>팔로워가 없습니다.</p>
        )}
      </ul>
    </div>
  );
}
