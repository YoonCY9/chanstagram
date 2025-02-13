// app/posts/[postId]/page.tsx
import React from "react";
import { cookies } from "next/headers";
import BackButton from "@/app/profile/BackButton";
import CommentItem from "@/app/comments/[postId]/CommentItem";
import CommentForm from "@/app/comments/[postId]/CommentForm";
import Link from "next/link";

// PostDetailedResponse DTO 타입 (필요한 필드만 정의)
interface PostDetailedResponse {
  postId: number;
  content: string;
  commentCount: number;
  imageUrl: string[];
  user: UserResponse;
  createdTime: string; // ISO 형식의 문자열로 가정
  updatedTime: string;
  comments: CommentDetailedResponse[];
  likeCount: number;
}

interface UserResponse {
  nickName: string;
  profileImage: string;
}

interface CommentDetailedResponse {
  id: number;
  content: string;
  loginId: string;
  likeCount: number;
  // (필요에 따라 추가 필드)
}

// 게시글 상세 정보를 가져오는 함수
async function fetchPostDetail(postId: string): Promise<PostDetailedResponse> {
  const res = await fetch(`http://localhost:8080/posts/detailed/${postId}`, {
    cache: "no-store",
  });
  if (!res.ok) {
    throw new Error(`Failed to fetch post details: ${res.statusText}`);
  }
  return res.json();
}

export default async function PostDetailPage({
  params,
}: {
  params: { postId: string };
}) {
  const { postId } = params;

  // HTTP-only 쿠키에서 token 읽기
  const cookieStore = await cookies();
  const token = cookieStore.get("token")?.value;
  if (!token) {
    throw new Error("로그인 하십시오");
  }

  // postId 유효성 검사 (숫자로만 이루어져 있는지)
  if (!/^\d+$/.test(postId)) {
    return <p className="text-red-500">Error: Invalid postId</p>;
  }

  // 게시글 상세 데이터 가져오기
  const post: PostDetailedResponse = await fetchPostDetail(postId);

  return (
    <div className="min-h-screen bg-gray-100">
      {/* 상단 헤더 - 고정 */}
      <header className="bg-white shadow p-4 sticky top-0 z-10">
        <div className="max-w-2xl mx-auto flex items-center justify-center">
          <Link href="/home">
            <h1 className="text-3xl font-bold text-pink-500">Chanstagram</h1>
          </Link>
        </div>
      </header>

      <div className="max-w-2xl mx-auto p-4">
        {/* 뒤로가기 버튼 */}
        <BackButton />

        {/* 게시글 상세 정보 */}
        <div className="bg-white border rounded-lg shadow-sm p-4 mb-6">
          <div className="flex items-center space-x-3">
            <img
              src={post.user.profileImage || "/images/default-avatar.png"}
              alt="User Avatar"
              className="w-10 h-10 rounded-full object-cover"
            />
            <div>
              <h2 className="text-lg font-bold">{post.user.nickName}</h2>
              <p className="text-xs text-gray-500">
                {new Date(post.createdTime).toLocaleString()}
              </p>
            </div>
          </div>
          <div className="mt-4">
            <p className="text-base">{post.content}</p>
          </div>
          {post.imageUrl && post.imageUrl.length > 0 && (
            <div className="mt-4 grid grid-cols-2 gap-2">
              {post.imageUrl.map((url, index) => (
                <img
                  key={index}
                  src={url}
                  alt={`Post image ${index + 1}`}
                  className="w-full h-auto object-cover rounded"
                />
              ))}
            </div>
          )}
          <div className="mt-4 flex items-center justify-between">
            <div className="flex items-center space-x-2">
              <span className="text-sm font-medium">
                {post.likeCount} Likes
              </span>
              <span className="text-sm font-medium">
                {post.commentCount} Comments
              </span>
            </div>
          </div>
        </div>

        {/* 댓글 영역 */}
        <div className="bg-white border rounded-lg shadow-sm p-4">
          <h3 className="text-2xl font-bold mb-4">Comments</h3>
          {post.comments.length === 0 ? (
            <p className="text-gray-500 text-center">No comments yet.</p>
          ) : (
            <ul className="space-y-4">
              {post.comments.map((comment) => (
                <CommentItem key={comment.id} comment={comment} token={token} />
              ))}
            </ul>
          )}
          {/* 댓글 작성 폼 */}
          <div className="mt-6 border-t pt-4">
            <CommentForm postId={postId} />
          </div>
        </div>
      </div>
    </div>
  );
}
