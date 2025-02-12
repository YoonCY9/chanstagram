// app/comments/[postId]/page.tsx
import React from "react";
import { cookies } from "next/headers";
import CommentItem from "./CommentItem";
import CommentForm from "./CommentForm";
import BackButton from "@/app/profile/BackButton";
import Link from "next/link";

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

  // HTTP-only 쿠키에서 token 읽기
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
      <div className="min-h-screen bg-gray-100">
        {/* 상단 헤더 */}
        <header className="bg-white shadow p-4 sticky top-0 z-10">
          <div className="max-w-md mx-auto flex items-center justify-center">
            <Link href="/home">
              <h1 className="text-3xl font-bold text-pink-500">Chanstagram</h1>
            </Link>
          </div>
        </header>
        <BackButton />
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

              {/* 댓글 작성 폼 */}
              <div className="mt-6 border-t pt-4">
                <CommentForm postId={postId} />
              </div>
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
