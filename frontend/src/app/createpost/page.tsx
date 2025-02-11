"use client";

import { useRouter } from "next/navigation";
import React, { useState } from "react";
import { uploadImage } from "./uploadImage";

export default function createPost() {
  const [postData, setPostData] = useState({
    content: "",
    imageUrl: [] as string[],
  });
  const router = useRouter();

  const [imgUploading, setImgUploading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setPostData({
      ...postData,
      [e.target.name]: e.target.value,
    });
  };

  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setImgUploading(true); // 업로드 상태 표시
    try {
      // 이미지 업로드 함수 호출
      const formData = new FormData();
      formData.append("image", file);

      const response = await uploadImage(formData); // 업로드 실행
      if (response?.data?.display_url) {
        // 업로드된 이미지 URL을 postData.imageUrl에 추가
        setPostData((prev) => ({
          ...prev,
          imageUrl: [...prev.imageUrl, response.data.display_url],
        }));
      } else {
        alert("이미지 업로드 실패");
      }
    } catch (error) {
      console.error("이미지 업로드 오류:", error);
    } finally {
      setImgUploading(false); // 업로드 상태 해제
    }
  };

  const handleUpload = async () => {
    try {
      const response = await fetch("http://localhost:8080/posts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include", // 쿠키 자동 포함
        body: JSON.stringify(postData),
      });

      if (response.ok) {
        alert("업로드 성공");
        router.push("/home");
      } else if (response.status === 401) {
        // 인증 실패 시 처리
        alert("로그인이 필요합니다.");
        router.push("/login");
      } else {
        alert("업로드 실패");
      }
    } catch (error) {
      console.error("업로드 중 오류 발생:", error);
      alert("업로드 중 문제가 발생했습니다.");
    }
  };

  return (
    <>
      <header className="flex items-center justify-between px-4 py-2 bg-white shadow-md">
        <button
          onClick={() => router.push("/home")}
          className="text-xl font-bold"
        >
          Chanstagram
        </button>
      </header>
      <div>
        <form className="mt-4">
          <textarea
            placeholder="내용을 입력해주세요."
            name="content"
            value={postData.content}
            onChange={handleChange}
            className="border border-gray-400 w-full p-4 h-64"
          />
        </form>
        <form className="p-6 border border-gray-600">
          <input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            disabled={imgUploading} // 업로드 중이면 입력 비활성화
            className={`${imgUploading ? "opacity-50 cursor-not-allowed" : ""}`}
          />
          {imgUploading && <p className="text-blue-500">이미지 업로드 중...</p>}
        </form>
        <button
          onClick={handleUpload}
          disabled={imgUploading} // 업로드 중이면 버튼 비활성화
          className={`mt-4 flex items-center gap-2 px-4 py-2 bg-blue-500 text-white shadow-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-400 transition ${
            imgUploading ? "opacity-50 cursor-not-allowed" : ""
          }`}
        >
          {imgUploading ? "업로드 중..." : "업로드"}
        </button>
      </div>
    </>
  );
}
