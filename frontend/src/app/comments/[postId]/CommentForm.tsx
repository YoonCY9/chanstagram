// app/comments/[postId]/CommentForm.tsx

"use client"; // 클라이언트 컴포넌트
import React from "react";
import { createCommentAction } from "./actions"; // (A)에서 작성한 서버 액션

export default function CommentForm({ postId }: { postId: string }) {
    return (
        <form action={createCommentAction} method="POST" className="flex flex-col gap-2">
            {/* postId를 서버 액션으로 넘기기 위해 hidden 필드에 넣음 */}
            <input type="hidden" name="postId" value={postId} />

            <textarea
                name="content"
                className="border border-gray-300 p-2 rounded"
                rows={3}
                placeholder="댓글을 입력하세요..."
            />
            <button
                type="submit"
                className="self-end px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
            >
                등록
            </button>
        </form>
    );
}
