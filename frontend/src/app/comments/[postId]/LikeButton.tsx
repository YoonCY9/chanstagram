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
  const [liked, setLiked] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleLike = async () => {
    // 이미 좋아요를 눌렀다면 추가 요청을 막을 수 있습니다.
    if (liked) return;

    setLoading(true);
    try {
      const response = await fetch(
        `http://localhost:8080/comments/${commentId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            // 실제 인증 토큰으로 변경하세요.
            Authorization: "Bearer " + token,
          },
        },
      );

      if (!response.ok) {
        throw new Error("Failed to like comment");
      }

      // 백엔드에서 반환하는 CommentResponse DTO 사용 (여기서는 likeCount 만 사용)
      const data = await response.json();
      setLikeCount(data.likeCount);
      setLiked(true);
    } catch (error) {
      console.error("Error liking comment:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <button
      onClick={handleLike}
      disabled={loading || liked}
      className="flex items-center space-x-1 focus:outline-none"
    >
      {liked ? (
        // 채워진 하트 아이콘 (좋아요를 누른 상태)
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-6 w-6 text-red-500"
          fill="currentColor"
          viewBox="0 0 24 24"
        >
          <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
        </svg>
      ) : (
        // 비어있는 하트 아이콘 (아직 좋아요를 누르지 않은 상태)
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
