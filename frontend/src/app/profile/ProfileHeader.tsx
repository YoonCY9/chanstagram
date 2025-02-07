"use client"
import { useState } from 'react'

const ProfileHeader: React.FC = () => {
  const [isFollowing, setIsFollowing] = useState(false);

  const handleFollowClick = () => {
    setIsFollowing(!isFollowing);
  };

  return (
    <header className="flex items-center mb-11">
      <div className="w-36 h-36 rounded-full overflow-hidden mr-24">
        <img 
          src="https://blog.kakaocdn.net/dn/bh3xaW/btrd04olbd6/HkQMeUpJsB6D3GcVdXfrc1/img.jpg" 
          alt="Profile" 
          className="w-full h-full object-cover"
        />
      </div>
      <div>
        <div className="flex items-center mb-5">
          <h1 className="text-2xl font-light mr-5">nick_name</h1>
          <button 
            onClick={handleFollowClick}
            className={`px-2 py-1 rounded text-sm font-semibold ${
              isFollowing 
                ? 'bg-gray-200 text-gray-800' 
                : 'bg-blue-500 text-white hover:bg-blue-600'
            }`}
          >
            {isFollowing ? 'Following' : 'Follow'}
          </button>
        </div>
      </div>
    </header>
  )
}

export default ProfileHeader
