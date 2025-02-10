"use client";

import { useState } from "react";

interface UserDetailResponse {
  userName: string;
  nickName: string;
  profileImage: string;
}

interface ProfileHeaderProps {
  userDetail: UserDetailResponse;
}

export default function ProfileHeader({ userDetail }: ProfileHeaderProps) {
  const [isFollowing, setIsFollowing] = useState(false);

  const handleFollowClick = () => {
    setIsFollowing(!isFollowing);
  };

  return (
      <header className="flex items-center mb-11">
        <div className="w-36 h-36 rounded-full overflow-hidden mr-24">
          <img src={userDetail.profileImage} alt="Profile" className="w-full h-full object-cover" />
        </div>
        <div>
          <div className="flex items-center mb-5">
            <h1 className="text-2xl font-light mr-5">{userDetail.nickName}</h1>
            <button
                onClick={handleFollowClick}
                className={`px-2 py-1 rounded text-sm font-semibold ${
                    isFollowing ? "bg-gray-200 text-gray-800" : "bg-blue-500 text-white hover:bg-blue-600"
                }`}
            >
              {isFollowing ? "Following" : "Follow"}
            </button>
          </div>
        </div>
      </header>
  );
}
