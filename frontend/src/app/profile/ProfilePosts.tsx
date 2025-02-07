const ProfilePosts: React.FC = () => {
    // 예시로 9개의 포스트
    const posts = Array(9).fill(null)
  
    return (
      <div className="grid grid-cols-3 gap-7">
        {posts.map((_, index) => (
          <div key={index} className="aspect-square relative">
            <img 
              src={`/post-${index + 1}.jpg`} 
              alt={`Post ${index + 1}`} 
              className="absolute inset-0 w-full h-full object-cover"
            />
          </div>
        ))}
      </div>
    )
  }
  
  export default ProfilePosts
  