// app/comments/[postId]/CommentForm.tsx
"use client"; // 클라이언트 컴포넌트

import React, { useState } from "react";
import { useParams, useRouter } from "next/navigation";

export default function CommentForm() {
    const { postId } = useParams() as { postId?: string };
    const [content, setContent] = useState("");
    const router = useRouter();

    // 쿠키에서 JWT 토큰 꺼내기 (예시)
    const getCookie = (name: string): string | null => {
        const cookies = document.cookie.split("; ");
        for (const cookie of cookies) {
            const [key, value] = cookie.split("=");
            if (key === name) return value;
        }
        return null;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!postId) {
            alert("유효하지 않은 postId 입니다.");
            return;
        }

        const token = getCookie("token");
        if (!token) {
            alert("로그인이 필요합니다.");
            return;
        }

        try {
            // **백엔드**: POST /comments/{postId}
            const response = await fetch(`http://localhost:8080/comments/${postId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                // RequestBody에 postId를 넣어준다.
                body: JSON.stringify({
                    content,
                    postId: Number(postId),
                }),
            });

            if (!response.ok) {
                throw new Error(`댓글 생성 실패: ${response.statusText}`);
            }

            // 성공 처리
            alert("댓글이 등록되었습니다.");
            setContent("");
            router.refresh(); // 현재 페이지 SSR 데이터 다시 불러오기
        } catch (err) {
            console.error(err);
            alert((err as Error).message);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-2">
      <textarea
          className="border border-gray-300 p-2 rounded"
          rows={3}
          placeholder="댓글을 입력하세요..."
          value={content}
          onChange={(e) => setContent(e.target.value)}
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
