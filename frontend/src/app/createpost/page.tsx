"use client";

import { useRouter } from "next/navigation";
import React, { useState } from "react";
import { uploadImage } from "@/app/createpost/uploadImage";
import { createPostAction } from "@/app/createpost/createPost";

export default function Page() {
  const [postData, setPostData] = useState({
    content: "",
    imageUrl: [] as string[],
  });
  const [imgUploading, setImgUploading] = useState(false);
  const router = useRouter();

  // 텍스트 입력값 변경 핸들러
  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setPostData({
      ...postData,
      [e.target.name]: e.target.value,
    });
  };

  // 파일 업로드 처리
  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setImgUploading(true);
    try {
      const formData = new FormData();
      formData.append("image", file);

      const response = await uploadImage(formData);
      if (response?.data?.display_url) {
        setPostData((prev) => ({
          ...prev,
          imageUrl: [...prev.imageUrl, response.data.display_url],
        }));
      } else {
        alert("이미지 업로드 실패");
      }
    } catch (error) {
      console.error("이미지 업로드 오류:", error);
      alert("이미지 업로드 중 문제가 발생했습니다.");
    } finally {
      setImgUploading(false);
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
      <div className="p-4">
        {/* form의 action 속성에 서버 액션을 지정합니다. */}
        <form action={createPostAction} method="POST">
          <textarea
            placeholder="내용을 입력해주세요."
            name="content"
            value={postData.content}
            onChange={handleChange}
            className="border border-gray-400 w-full p-4 h-64"
          />
          <div className="p-6 border border-gray-600 mt-4">
            <input
              type="file"
              accept="image/*"
              onChange={handleFileChange}
              disabled={imgUploading}
              className={`${imgUploading ? "opacity-50 cursor-not-allowed" : ""}`}
            />
            {imgUploading && (
              <p className="text-blue-500">이미지 업로드 중...</p>
            )}
          </div>
          {/* 서버 액션으로 전달할 이미지 URL 목록을 hidden input에 JSON 문자열로 담습니다 */}
          <input
            type="hidden"
            name="imageUrl"
            value={JSON.stringify(postData.imageUrl)}
          />
          <button
            type="submit"
            disabled={imgUploading}
            className={`mt-4 flex items-center gap-2 px-4 py-2 bg-blue-500 text-white shadow-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-400 transition ${
              imgUploading ? "opacity-50 cursor-not-allowed" : ""
            }`}
            onClick={() => router.push("/home")}
          >
            {imgUploading ? "업로드 중..." : "업로드"}
          </button>
        </form>
      </div>
    </>
  );
}
