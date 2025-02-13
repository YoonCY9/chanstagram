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
      <header className="flex items-center justify-between px-4 py-3 bg-white border-b border-gray-200 sticky top-0 z-10">
        <button
          onClick={() => router.push("/home")}
          className="text-xl font-bold text-gray-800 hover:text-gray-600 transition-colors"
        >
          Chanstagram
        </button>
      </header>
      <div className="p-4">
        {/* form의 action 속성에 서버 액션을 지정합니다. */}
        <form action={createPostAction}>
          <div className="bg-white rounded-xl shadow-[0_2px_12px_-3px_rgba(0,0,0,0.1)]">
            <textarea
              placeholder="오늘의 이야기를 공유해보세요..."
              name="content"
              value={postData.content}
              onChange={handleChange}
              className="w-full p-4 h-64 text-sm resize-none
                      border-none focus:ring-0
                      placeholder-gray-400
                      focus:outline-none"
            />
          </div>
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

          <input
            type="hidden"
            name="imageUrl"
            value={JSON.stringify(postData.imageUrl)}
          />
          <button
            type="submit"
            disabled={imgUploading}
            className={`w-full py-3 px-6 rounded-lg font-medium text-white 
    transition-all duration-200
    mt-8  // 상단 여백 2rem 추가
    ${
      imgUploading
        ? "bg-blue-300 cursor-not-allowed"
        : "bg-blue-500 hover:bg-blue-600 active:scale-[0.98]"
    }`}
            onClick={() => router.push("/home")}
          >
            {imgUploading ? "업로드 중..." : "게시하기"}
          </button>
        </form>
      </div>
    </>
  );
}
