// app/comments/[postId]/page.tsx

import React from "react";
import CommentForm from "./CommentForm"; // 클라이언트 컴포넌트 임포트

// 1) 백엔드에서 댓글 목록을 가져오는 함수
async function fetchComments(postId: string) {
    const res = await fetch(`http://localhost:8080/comments/${postId}`, {
        cache: "no-store",
    });

    if (!res.ok) {
        throw new Error(`Failed to fetch comments: ${res.statusText}`);
    }

    // 백엔드 구조: { comments: [ ... ] }
    return res.json();
}

// 2) 페이지 컴포넌트 (서버 컴포넌트)
export default async function CommentsPage({
                                               params,
                                           }: {
    params: { postId: string };
}) {
    const { postId } = params;

    // postId가 숫자인지 간단히 체크
    if (!/^\d+$/.test(postId)) {
        return <p className="text-red-500">Error: Invalid postId</p>;
    }

    try {
        // API 호출해서 댓글 데이터 가져오기
        const data = await fetchComments(postId);
        const comments = data.comments; // 백엔드 DTO: {comments: [...]}

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
                                        {/* 아바타 이미지 (예시) */}
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
                                                <button className="hover:underline">Like</button>
                                                <button className="hover:underline">Reply</button>
                                                <span>1m</span>
                                            </div>
                                        </div>
                                    </li>
                                ))}
                            </ul>
                        )}

                        {/* 댓글 작성 폼 (클라이언트 컴포넌트) */}
                        <div className="mt-6 border-t pt-4">
                            <CommentForm />
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
