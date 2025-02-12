"use client";

import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";

interface UserRequest {
  userName: string;
  nickName: string;
  gender: string; // 백엔드의 Gender enum에 맞춰 ("MALE", "FEMALE", "OTHER" 등)
  birth: string; // ISO 날짜 문자열 (예: "1990-01-01")
  content: string;
  profileImage: string;
  phoneNumber: string;
}

export default function UserUpdateForm() {
  const router = useRouter();
  const [formData, setFormData] = useState<UserRequest>({
    userName: "",
    nickName: "",
    gender: "MALE",
    birth: "",
    content: "",
    profileImage: "",
    phoneNumber: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // 선택 사항: 기존 사용자 정보를 불러와서 폼의 초기값으로 세팅
  useEffect(() => {
    const fetchUserProfile = async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        setError("로그인 하십시오.");
        return;
      }
      try {
        // 예시: 사용자의 정보를 가져오는 API가 있다면 호출합니다.
        // 아래 URL은 예시이며 실제 API에 맞게 수정하세요.
        const res = await fetch("http://localhost:8080/users/me", {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
          },
        });
        if (!res.ok) {
          throw new Error("회원 정보를 불러오지 못했습니다.");
        }
        const data = await res.json();
        // 백엔드에서 받은 데이터 형식에 맞춰 formData를 세팅합니다.
        setFormData({
          userName: data.userName || "",
          nickName: data.nickName || "",
          gender: data.gender || "MALE",
          birth: data.birth || "",
          content: data.content || "",
          profileImage: data.profileImage || "",
          phoneNumber: data.phoneNumber || "",
        });
      } catch (err) {
        setError("회원 정보를 불러오는 중 오류가 발생했습니다.");
      }
    };
    fetchUserProfile();
  }, []);

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    const token = localStorage.getItem("token");
    if (!token) {
      setError("로그인 하십시오.");
      setLoading(false);
      return;
    }
    try {
      const res = await fetch("http://localhost:8080/users", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
        body: JSON.stringify(formData),
      });
      if (!res.ok) {
        throw new Error("회원 정보 수정에 실패했습니다.");
      }
      // 성공 시 페이지 이동이나 알림 표시
      alert("회원 정보가 수정되었습니다.");
      // 예: 프로필 페이지로 이동
      router.push("/profile");
    } catch (err: any) {
      setError(err.message || "오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto p-4">
      <h1 className="text-xl font-bold mb-4">회원정보 수정</h1>
      {error && <p className="text-red-500 mb-2">{error}</p>}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block mb-1">이름</label>
          <input
            type="text"
            name="userName"
            value={formData.userName}
            onChange={handleChange}
            className="w-full border rounded p-2"
            required
          />
        </div>
        <div>
          <label className="block mb-1">닉네임</label>
          <input
            type="text"
            name="nickName"
            value={formData.nickName}
            onChange={handleChange}
            className="w-full border rounded p-2"
            required
          />
        </div>
        <div>
          <label className="block mb-1">성별</label>
          <select
            name="gender"
            value={formData.gender}
            onChange={handleChange}
            className="w-full border rounded p-2"
          >
            <option value="MALE">남성</option>
            <option value="FEMALE">여성</option>
            <option value="OTHER">기타</option>
          </select>
        </div>
        <div>
          <label className="block mb-1">생년월일</label>
          <input
            type="date"
            name="birth"
            value={formData.birth}
            onChange={handleChange}
            className="w-full border rounded p-2"
            required
          />
        </div>
        <div>
          <label className="block mb-1">소개</label>
          <textarea
            name="content"
            value={formData.content}
            onChange={handleChange}
            className="w-full border rounded p-2"
          ></textarea>
        </div>
        <div>
          <label className="block mb-1">프로필 이미지 URL</label>
          <input
            type="text"
            name="profileImage"
            value={formData.profileImage}
            onChange={handleChange}
            className="w-full border rounded p-2"
          />
        </div>
        <div>
          <label className="block mb-1">전화번호</label>
          <input
            type="tel"
            name="phoneNumber"
            value={formData.phoneNumber}
            onChange={handleChange}
            className="w-full border rounded p-2"
          />
        </div>
        <button
          type="submit"
          disabled={loading}
          className="w-full py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          {loading ? "수정 중..." : "회원정보 수정"}
        </button>
      </form>
    </div>
  );
}
