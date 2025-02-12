// app/comments/[postId]/page.tsx
import React from "react";
import CommentForm from "./CommentForm";
import LikeButton from "./LikeButton";
import { cookies } from "next/headers";

async function fetchComments(postId: string) {
  const res = await fetch(`http://localhost:8080/comments/${postId}`, {
    cache: "no-store",
  });

  if (!res.ok) {
    throw new Error(`Failed to fetch comments: ${res.statusText}`);
  }

  return res.json(); // { comments: [...] }
}

export default async function CommentsPage({
  params,
}: {
  params: { postId: string };
}) {
  const { postId } = params;

  const cookieStore = await cookies();
  const token = cookieStore.get("token")?.value;

  if (!token) {
    throw new Error("로그인 하십시오");
  }

  if (!/^\d+$/.test(postId)) {
    return <p className="text-red-500">Error: Invalid postId</p>;
  }

  try {
    const data = await fetchComments(postId);
    const comments = data.comments;

    return (
      <div className="max-w-md mx-auto p-4">
        <div className="bg-white border rounded-lg shadow-sm">
          <div className="p-4 border-b">
            <h2 className="text-xl font-bold">Comments</h2>
          </div>

          <div className="p-4">
            {comments.length === 0 ? (
              <p className="text-gray-500 text-center">No comments yet.</p>
            ) : (
              <ul className="space-y-4">
                {comments.map((comment: any) => (
                  <li key={comment.id} className="flex items-start space-x-3">
                    <img
                      className="w-9 h-9 rounded-full object-cover"
                      src="/images/default-avatar.png"
                      alt="Avatar"
                    />
                    <div className="flex-1">
                      <p className="text-sm text-gray-700">
                        <span className="font-semibold mr-1">
                          @{comment.loginId}
                        </span>
                        {comment.content}
                      </p>
                      <div className="flex space-x-5 mt-1 text-xs text-gray-400">
                        {/* 좋아요 버튼: comment.likeCount 값을 initialLikeCount로 전달 */}
                        <LikeButton
                          commentId={comment.id}
                          initialLikeCount={comment.likeCount}
                          token={token}
                        />
                        <button className="hover:underline">Reply</button>
                        <span>1m</span>
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            )}

            {/* 댓글 작성 폼 (서버 액션 방식) */}
            <div className="mt-6 border-t pt-4">
              <CommentForm postId={postId} />
            </div>
          </div>
        </div>
      </div>
    );
  } catch (error) {
    console.error(error);
    return <p className="text-red-500">Error loading comments</p>;
  }
}
