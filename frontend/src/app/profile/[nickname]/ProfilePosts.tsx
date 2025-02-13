import Link from "next/link";

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
          <Link href={`/posts/${post.postId}`}>
            <button>
              <img
                src={post.imageUrl[0]} // 첫 번째 이미지만 표시
                alt={`Post ${index + 1}`}
                className="absolute inset-0 w-full h-full object-cover"
              />
            </button>
          </Link>
        </div>
      ))}
    </div>
  );
}
