"use client";

import React, { useState } from "react";
import LikeButton from "./LikeButton";

interface Comment {
  id: number;
  content: string;
  loginId: string;
  likeCount: number;
  // 만약 백엔드에서 좋아요 여부도 함께 반환한다면:
  // likedByUser: boolean;
}

interface CommentItemProps {
  comment: Comment;
  token: string;
}

export default function CommentItem({ comment, token }: CommentItemProps) {
  // 수정 모드 여부와 현재 내용 상태 관리
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState(comment.content);
  const [loading, setLoading] = useState(false);

  // 수정 모드 진입
  const handleEditClick = () => {
    setIsEditing(true);
  };

  // 수정 취소: 원래 내용으로 되돌림
  const handleCancelClick = () => {
    setContent(comment.content);
    setIsEditing(false);
  };

  // 수정 저장: PUT 요청으로 수정 API 호출
  const handleSaveClick = async () => {
    setLoading(true);
    try {
      const response = await fetch(
        `http://localhost:8080/comments/${comment.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
          },
          body: JSON.stringify({ content }), // UpdateCommentRequest DTO 형식
        },
      );

      if (!response.ok) {
        throw new Error("Failed to update comment");
      }

      // 백엔드가 수정된 CommentResponse DTO를 반환합니다.
      const data = await response.json();
      setContent(data.content);
      setIsEditing(false);
    } catch (error) {
      console.error("Error updating comment:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <li className="flex items-start space-x-3">
      <img
        className="w-9 h-9 rounded-full object-cover"
        src="/images/default-avatar.png"
        alt="Avatar"
      />
      <div className="flex-1">
        <p className="text-sm text-gray-700">
          <span className="font-semibold mr-1">@{comment.loginId}</span>
          {isEditing ? (
            <textarea
              className="border rounded p-1"
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          ) : (
            content
          )}
        </p>
        <div className="flex space-x-5 mt-1 text-xs text-gray-400">
          {/* 좋아요 버튼 */}
          <LikeButton
            commentId={comment.id}
            initialLikeCount={comment.likeCount}
            token={token}
            // 만약 초기 좋아요 상태도 필요하다면 initialLiked prop도 전달합니다.
            // initialLiked={comment.likedByUser}
          />
          {/* 수정 및 기타 버튼 */}
          {isEditing ? (
            <>
              <button
                onClick={handleSaveClick}
                disabled={loading}
                className="hover:underline"
              >
                Save
              </button>
              <button
                onClick={handleCancelClick}
                disabled={loading}
                className="hover:underline"
              >
                Cancel
              </button>
            </>
          ) : (
            <button onClick={handleEditClick} className="hover:underline">
              Edit
            </button>
          )}
          <button className="hover:underline">Reply</button>
          <span>1m</span>
        </div>
      </div>
    </li>
  );
}
