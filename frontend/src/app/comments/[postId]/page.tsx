import React from "react";
import CommentForm from "./CommentForm";
import CommentItem from "./CommentItem"; // 새로 만든 댓글 항목 컴포넌트
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

  // 쿠키에서 token 값을 가져옴
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
                  <CommentItem
                    key={comment.id}
                    comment={comment}
                    token={token}
                  />
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
