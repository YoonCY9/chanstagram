"use client";

import React, { useState, useEffect } from "react";
import Link from "next/link";

interface Post {
  postId: number;
  content: string;
  commentCount: number;
  imageUrl: string[];
  user: {
    nickName: string;
    profileImage?: string;
  };
  createdTime: string;
  updatedTime: string;
  likeCount: number;
}

interface User {
  id: number;
  nickName: string;
  profileImage?: string;
}

export default function SearchPage() {
  // 검색 대상: posts(게시글) 또는 users(사용자)
  const [searchType, setSearchType] = useState<"posts" | "users">("posts");
  // 검색 결과 (게시글 또는 사용자 배열)
  const [results, setResults] = useState<any[]>([]);
  const [page, setPage] = useState(1);
  const [size] = useState(10);
  const [keyword, setKeyword] = useState("");
  // 게시글 검색에만 사용 (정렬 기준: 최신순, 좋아요순)
  const [criteria, setCriteria] = useState("");

  // 검색 결과 호출 함수
  const fetchResults = async () => {
    try {
      const params = new URLSearchParams({
        page: page.toString(),
        size: size.toString(),
      });
      if (keyword) {
        params.append("searchby", keyword);
      }
      let url = "";
      if (searchType === "posts") {
        if (criteria) {
          params.append("orderby", criteria);
        }
        url = `http://localhost:8080/posts?${params.toString()}`;
      } else {
        url = `http://localhost:8080/users?${params.toString()}`;
      }

      const response = await fetch(url);
      if (!response.ok) {
        throw new Error("검색 결과를 불러오는데 실패했습니다.");
      }
      const data = await response.json();
      // 사용자 검색의 경우 백엔드 응답 DTO의 필드명이 userResponses 입니다.
      if (searchType === "users") {
        setResults(data.userResponses || []);
      } else {
        setResults(data);
      }
    } catch (error) {
      console.error("Error fetching results:", error);
    }
  };

  // 검색 조건이나 페이지, 검색 대상이 변경되면 데이터 호출
  useEffect(() => {
    fetchResults();
  }, [page, keyword, criteria, searchType]);

  // 폼 제출 시 페이지를 1로 리셋
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setPage(1);
    fetchResults();
  };

  return (
    <div className="max-w-4xl mx-auto px-4 py-8 bg-gray-50 min-h-screen">
      {/* Header with logo */}
      <header className="mb-8 flex items-center justify-between">
        <Link
          href="/home"
          className="text-4xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-black to-black"
        >
          Chanstagram
        </Link>
      </header>

      {/* 검색 탭 */}
      <div className="mb-6">
        <h1 className="text-3xl font-bold mb-4 text-center">검색하기</h1>
        <div className="flex justify-center space-x-6 border-b pb-2">
          <button
            onClick={() => {
              setSearchType("posts");
              setPage(1);
            }}
            className={`pb-2 transition-colors ${
              searchType === "posts"
                ? "border-b-2 border-black font-bold text-black"
                : "text-gray-500 hover:text-black"
            }`}
          >
            게시글
          </button>
          <button
            onClick={() => {
              setSearchType("users");
              setPage(1);
            }}
            className={`pb-2 transition-colors ${
              searchType === "users"
                ? "border-b-2 border-black font-bold text-black"
                : "text-gray-500 hover:text-black"
            }`}
          >
            사용자
          </button>
        </div>
      </div>

      {/* 검색 폼 */}
      <form
        onSubmit={handleSubmit}
        className="flex flex-col sm:flex-row items-center gap-4 mb-8"
      >
        <input
          type="text"
          placeholder={
            searchType === "posts" ? "게시글 검색..." : "사용자 닉네임 검색..."
          }
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          className="w-full sm:w-auto border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2"
        />
        {searchType === "posts" && (
          <select
            value={criteria}
            onChange={(e) => setCriteria(e.target.value)}
            className="border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2"
          >
            <option value="">최신순</option>
            <option value="like">좋아요순</option>
          </select>
        )}
        <button
          type="submit"
          className="bg-gradient-to-r rounded-lg px-6 py-2 hover:opacity-90 transition"
        >
          검색
        </button>
      </form>

      {/* 검색 결과 */}
      {searchType === "posts" ? (
        <div className="space-y-6">
          {results.length > 0 ? (
            results.map((post: Post) => (
              <Link key={post.postId} href={`/posts/${String(post.postId)}`}>
                <div className="border border-gray-200 rounded-lg p-6 shadow-sm bg-white hover:shadow-md transition cursor-pointer">
                  {post.imageUrl && post.imageUrl.length > 0 && (
                    <img
                      src={post.imageUrl[0]}
                      alt="post image"
                      className="w-full h-64 object-cover mb-4 rounded"
                    />
                  )}
                  <p className="mb-4 text-gray-800">{post.content}</p>
                  <div className="text-sm text-gray-500">
                    좋아요: {post.likeCount}
                  </div>
                </div>
              </Link>
            ))
          ) : (
            <p className="text-center text-gray-500">
              게시글 검색 결과가 없습니다.
            </p>
          )}
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {results.length > 0 ? (
            results.map((user: User) => (
              <Link key={user.id} href={`/profile/${user.nickName}`}>
                <div className="border border-gray-200 rounded-lg p-4 shadow-sm bg-white flex flex-col items-center hover:shadow-md transition cursor-pointer">
                  <img
                    src={user.profileImage || "/default-profile.png"}
                    alt={user.nickName}
                    className="w-24 h-24 rounded-full object-cover"
                  />
                  <span className="font-bold text-gray-800 mt-2 text-center break-words">
                    {user.nickName}
                  </span>
                </div>
              </Link>
            ))
          ) : (
            <p className="text-center text-gray-500 col-span-full">
              사용자 검색 결과가 없습니다.
            </p>
          )}
        </div>
      )}

      {/* 페이지네이션 */}
      <div className="flex justify-between items-center mt-10">
        <button
          onClick={() => setPage((prev) => Math.max(prev - 1, 1))}
          className="bg-gray-300 text-gray-700 px-5 py-2 rounded-lg disabled:opacity-50"
          disabled={page === 1}
        >
          이전
        </button>
        <span className="font-semibold text-gray-700">페이지 {page}</span>
        <button
          onClick={() => setPage(page + 1)}
          className="bg-gray-300 text-gray-700 px-5 py-2 rounded-lg"
        >
          다음
        </button>
      </div>
    </div>
  );
}
