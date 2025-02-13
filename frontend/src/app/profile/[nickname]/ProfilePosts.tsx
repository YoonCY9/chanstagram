interface ProfilePostsProps {
  posts: Array<{
    postId: number;
    imageUrl: string[];
  }>;
}

export default function ProfilePosts({ posts }: ProfilePostsProps) {
  return (
    <div className="grid grid-cols-3 gap-3">
      {posts.map((post, index) => (
        <div
          key={index}
          className="aspect-square relative border border-gray-200"
        >
          <img
            src={post.imageUrl[0]} // 첫 번째 이미지만 표시
            alt={`Post ${index + 1}`}
            className="absolute inset-0 w-full h-full object-cover"
          />
        </div>
      ))}
    </div>
  );
}
