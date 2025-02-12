"use client";

import React from "react";
import { useRouter } from "next/navigation";

interface CommentDeleteButtonProps {
  commentId: number;
  token: string;
}

export default function CommentDeleteButton({
  commentId,
  token,
}: CommentDeleteButtonProps) {
  const router = useRouter();

  const handleDelete = async () => {
    if (!confirm("정말로 댓글을 삭제하시겠습니까?")) return;

    try {
      const response = await fetch(
        `http://localhost:8080/comments/${commentId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: "Bearer " + token,
          },
        },
      );

      if (!response.ok) {
        throw new Error("댓글 삭제에 실패했습니다.");
      }

      // 삭제 후 페이지를 새로고침하여 최신 댓글 목록을 불러옵니다.
      router.refresh();
    } catch (error) {
      console.error("댓글 삭제 중 오류 발생:", error);
    }
  };

  return (
    <button onClick={handleDelete} className="text-red-500 hover:underline">
      Delete
    </button>
  );
}
