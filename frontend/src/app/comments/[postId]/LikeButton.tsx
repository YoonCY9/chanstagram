// app/comments/[postId]/LikeButton.tsx
"use client";

import React, { useState } from "react";

interface LikeButtonProps {
  commentId: number | string;
  initialLikeCount: number;
  token: string;
}

export default function LikeButton({
  commentId,
  initialLikeCount,
  token,
}: LikeButtonProps) {
  const [likeCount, setLikeCount] = useState(initialLikeCount);
  // liked 상태는 백엔드의 토글 로직과는 별개로, 프론트엔드에서 현재 상태를 표시하기 위해 사용합니다.
  const [liked, setLiked] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleToggle = async () => {
    setLoading(true);
    try {
      // 단일 엔드포인트 호출 (좋아요 / 언라이크 모두 처리)
      const response = await fetch(
        `http://localhost:8080/comments/${commentId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
          },
        },
      );

      if (!response.ok) {
        throw new Error("Failed to toggle like comment");
      }

      // 백엔드에서 반환된 CommentResponse DTO의 likeCount 사용
      const data = await response.json();

      // 응답에 따른 좋아요 수 업데이트
      setLikeCount(data.likeCount);

      // 현재 상태에 관계없이 토글 (만약 좋아요 상태였다면 좋아요 취소, 아니면 좋아요 등록)
      setLiked((prev) => !prev);
    } catch (error) {
      console.error("Error toggling like status:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <button
      onClick={handleToggle}
      disabled={loading}
      className="flex items-center space-x-1 focus:outline-none"
    >
      {liked ? (
        // 채워진 하트 아이콘 (좋아요 상태)
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-6 w-6 text-red-500"
          fill="currentColor"
          viewBox="0 0 24 24"
        >
          <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
        </svg>
      ) : (
        // 비어있는 하트 아이콘 (좋아요 상태가 아님)
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-6 w-6 text-gray-500"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
          strokeWidth={2}
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            d="M4.318 6.318a4.5 4.5 0 016.364 0L12 7.636l1.318-1.318a4.5 4.5 0 116.364 6.364L12 21.364l-7.682-7.682a4.5 4.5 0 010-6.364z"
          />
        </svg>
      )}
      <span className="text-sm">{likeCount}</span>
    </button>
  );
}
