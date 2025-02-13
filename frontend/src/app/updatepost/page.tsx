"use client";

import { useRouter, useSearchParams } from "next/navigation";
import React, { useEffect, useState, ChangeEvent } from "react";
import { updatePostAction } from "@/app/updatepost/updatePost";

export default function Page() {
  const searchParams = useSearchParams();
  const postId = searchParams.get("postId");
  const [content, setContent] = useState(""); // 이미지 상태 제거
  const router = useRouter();

  // 기존 컨텐츠만 조회
  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await fetch(`http://localhost:8080/posts/${postId}`);
        const data = await response.json();
        setContent(data.content); // content만 설정
      } catch (error) {
        console.error("게시글 조회 실패:", error);
      }
    };
    if (postId) fetchPost();
  }, [postId]);

  // 텍스트 입력 핸들러
  const handleChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  // 폼 제출 핸들러 (이미지 관련 코드 제거)
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!postId) return;

    try {
      const formData = new FormData();
      formData.append("content", content);

      await updatePostAction(Number(postId), formData);
      router.push("/home");
      alert("게시글이 수정되었습니다.");
    } catch (error) {
      console.log(error);
      alert("자신의 게시글만 수정 가능 합니다.");
    }
  };

  return (
    <div className="p-4">
      <form onSubmit={handleSubmit}>
        <textarea
          placeholder="내용을 입력해주세요."
          value={content}
          onChange={handleChange}
          className="border border-gray-400 w-full p-4 h-64 mb-4"
        />

        <button
          type="submit"
          className="mt-4 w-full px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
        >
          수정 완료
        </button>
      </form>
    </div>
  );
}
