"use client";

import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import LikeButton from "./LikeButton";
import CommentDeleteButton from "./CommentDeleteButton"; // 댓글 삭제 버튼 컴포넌트 추가

interface Comment {
  id: number;
  content: string;
  loginId: string;
  likeCount: number;
}

interface CommentItemProps {
  comment: Comment;
  token: string;
}

// /me API의 응답 DTO 타입 (필요한 필드만 정의)
interface UserDetailResponse {
  userName: string;
  nickName: string;
  loginId: string;
  password: string;
  gender: string;
  bitrh: string; // 오타일 수 있으니 주의 (birth로 수정할 수도 있음)
  content: string;
  profileImage: string;
  phoneNumber: string;
}

export default function CommentItem({ comment, token }: CommentItemProps) {
  const router = useRouter();

  // 댓글 수정 관련 상태
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState(comment.content);
  const [loading, setLoading] = useState(false);

  // 현재 로그인한 사용자의 정보 상태 (내 프로필 조회 API)
  const [currentUser, setCurrentUser] = useState<UserDetailResponse | null>(
    null,
  );

  // /me API를 호출하여 현재 로그인한 사용자의 정보를 가져옴
  useEffect(() => {
    async function fetchCurrentUser() {
      try {
        const response = await fetch("http://localhost:8080/me", {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
          },
        });
        if (!response.ok) {
          throw new Error("Failed to fetch current user info");
        }
        const data: UserDetailResponse = await response.json();
        setCurrentUser(data);
      } catch (error) {
        console.error("Error fetching current user info:", error);
      }
    }
    fetchCurrentUser();
  }, [token]);

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

      // 백엔드에서 수정된 CommentResponse DTO를 반환합니다.
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
        src={
          // 댓글 작성자가 현재 로그인 사용자라면 currentUser.profileImage를 사용하고,
          // 아니라면 기본 아바타 이미지를 사용합니다.
          currentUser && comment.loginId === currentUser.loginId
            ? currentUser.profileImage
            : "/images/default-avatar.png"
        }
        alt="Avatar"
      />
      <div className="flex-1">
        <p className="text-sm text-gray-700">
          <span className="font-semibold mr-1">
            {currentUser && comment.loginId === currentUser.loginId
              ? currentUser.nickName
              : comment.loginId}
          </span>
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
          />
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
              {/* 수정 모드일 때 삭제 버튼 추가 */}
              <CommentDeleteButton commentId={comment.id} token={token} />
            </>
          ) : (
            <>
              <button onClick={handleEditClick} className="hover:underline">
                Edit
              </button>
              {/* 편집 모드가 아닐 때도 삭제 버튼 표시 */}
              <CommentDeleteButton commentId={comment.id} token={token} />
            </>
          )}
        </div>
      </div>
    </li>
  );
}
