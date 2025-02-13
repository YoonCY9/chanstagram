import { FollowResponse } from "@/app/profile/[nickname]/page"; // FollowResponse 타입 import

// 서버에서 데이터를 처리하는 Server Component
export default async function Following({
  params: { nickname },
}: {
  params: { nickname: string };
}) {
  const fetchFollowing = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/follows/followees/${nickname}`,
      );
      const data: FollowResponse[] = await response.json();
      return data; // 팔로잉 목록 리턴
    } catch (error) {
      console.error("팔로잉을 가져오는 데 실패했습니다.", error);
      return []; // 실패 시 빈 배열 리턴
    }
  };

  // 서버에서 데이터를 가져옵니다.
  const following = await fetchFollowing();

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-semibold mb-4">{nickname}님의 팔로잉</h1>
      <ul>
        {following.length > 0 ? (
          following.map((followee) => (
            <li key={followee.nickName} className="flex items-center mb-4">
              <img
                src={followee.profileImage}
                alt={followee.nickName}
                className="w-12 h-12 rounded-full mr-4"
              />
              <div>
                <h3 className="font-medium">{followee.nickName}</h3>
                <p className="text-sm text-gray-500">{followee.userName}</p>
              </div>
            </li>
          ))
        ) : (
          <p>팔로잉이 없습니다.</p>
        )}
      </ul>
    </div>
  );
}
