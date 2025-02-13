"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { deletePost } from "@/app/home/deletePost";
import { PlusCircle } from "lucide-react";

interface PostProps {
  postId: number;
}

const DeleteButton: React.FC<PostProps> = ({ postId }) => {
  const handleDelete = async () => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      try {
        await deletePost(postId); // 서버 액션 호출
        alert("게시글이 삭제되었습니다.");
        window.location.reload(); // 페이지 새로고침
      } catch (error) {
        console.error(error);
        alert("자신의 게시글만 삭제 가능 합니다.");
      }
    }
  };

  return (
    <button
      onClick={handleDelete}
      className="text-sm text-red-500 hover:underline"
    >
      삭제
    </button>
  );
};

interface IUser {
  imageUrls: string[]; // 게시물 이미지 배열
  nickName: string; // 사용자 이름
  likeCount: number; // 좋아요 수
  isLiked: boolean;
  profileImage: string;
  content: string;
  postId: number;
}

const Home = () => {
  const [users, setUsers] = useState<IUser[]>([]); // 초기 상태를 빈 배열로 설정
  const [loading, setLoading] = useState(true); // 로딩 상태
  const router = useRouter();

  // useEffect(() => {
  //     const token = localStorage.getItem("token");
  //     if (!token) {
  //         router.push("/login"); // 로그인 페이지로 리디렉션
  //     }
  // }, [router]);

  // 백엔드에서 데이터를 가져오는 함수

  const fetchPosts = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/posts?page=1&size=10",
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );
      if (!response.ok) {
        throw new Error("Failed to fetch posts");
      }
      const data = await response.json();

      // 백엔드 데이터를 React의 상태로 변환
      const formattedData = data.map((post: any) => ({
        imageUrls: post.imageUrl, // List<String> 형태
        nickName: post.user.nickName, // UserResponse에서 사용자 이름
        likeCount: post.likeCount || 0, // 좋아요 수
        isLiked: false, // 기본값
        profileImage: post.user.profileImage || "", // 사용자 프로필 이미지
        content: post.content, // 게시물 내용
        postId: post.postId,
      }));
      setUsers(formattedData); // 상태 업데이트
    } catch (error) {
      console.error("Error fetching posts:", error);
    } finally {
      setLoading(false); // 로딩 완료
    }
  };

  // 컴포넌트가 마운트되면 fetchPosts 호출
  useEffect(() => {
    fetchPosts();
  }, []);

  // 좋아요 버튼 클릭 이벤트 핸들러
  const handleLike = (index: number) => {
    setUsers((prevUsers) =>
      prevUsers.map((user, i) =>
        i === index
          ? {
              ...user,
              likeCount: user.isLiked ? user.likeCount - 1 : user.likeCount + 1, // 좋아요 수 증가/감소
              isLiked: !user.isLiked, // 좋아요 상태 토글
            }
          : user,
      ),
    );
  };

  return (
    <div className="flex flex-col h-screen w-full max-w-md mx-auto bg-white overflow-hidden">
      {/* 헤더 개선 */}
      <header className="flex items-center justify-between px-4 py-3 bg-white border-b border-gray-200 sticky top-0 z-10">
        <button
          onClick={() => window.location.reload()}
          className="text-xl font-bold text-gray-800 hover:text-gray-600 transition-colors"
        >
          Chanstagram
        </button>
      </header>

      {/* 메인 피드 개선 */}
      <main className="flex-1 overflow-hidden bg-gray-50">
        <section className="space-y-6 pb-4">
          {users.map((user, index) => (
            <div
              key={index}
              className="bg-white rounded-lg shadow-[0_2px_12px_-3px_rgba(0,0,0,0.1)] border border-gray-100"
            >
              {/* 프로필 섹션 */}
              <button
                onClick={() =>
                  router.push(`/profile/${encodeURIComponent(user.nickName)}`)
                }
                className="flex items-center p-4 hover:bg-gray-50 transition-colors w-full"
              >
                <img
                  src={user.profileImage}
                  alt={`${user.nickName} profile`}
                  className="w-8 h-8 rounded-full object-cover ring-2 ring-pink-300"
                />
                <span className="ml-3 font-semibold text-gray-800">
                  {user.nickName}
                </span>
              </button>

              {/* 이미지 갤러리 */}
              <div className="flex overflow-x-scroll space-x-2 px-4 hide-scrollbar">
                {user.imageUrls.map((url, imgIndex) => (
                  <div key={imgIndex} className="relative flex-shrink-0">
                    <img
                      src={url}
                      alt={`Post image ${imgIndex + 1}`}
                      className="w-full h-auto object-cover rounded"
                    />
                  </div>
                ))}
              </div>

              {/* 컨텐츠 영역 */}
              <div className="px-4 py-3">
                <p className="text-gray-800 text-sm leading-5 tracking-wide">
                  {user.content}
                </p>
              </div>

              {/* 액션 버튼 그룹 */}
              <div className="px-4 py-2 flex items-center justify-between border-t border-gray-100">
                <button
                  onClick={() => handleLike(index)}
                  className="p-1 hover:scale-110 transition-transform"
                >
                  <svg
                    className={`w-7 h-7 ${user.isLiked ? "text-red-500 fill-current" : "text-gray-600"}`}
                    // ... 기존 SVG 경로 유지
                  />
                </button>
                <div className="flex items-center space-x-4">
                  <Link href={`/comments/${user.postId}`}>
                    <p className="text-gray-600 text-sm hover:text-blue-500 hover:underline transition-colors">
                      💬 {user.likeCount > 0 ? user.likeCount : ""} 댓글 보기
                    </p>
                  </Link>
                  <Link href={`/updatepost?postId=${user.postId}`}>
                    <p className="text-gray-600 text-sm hover:text-blue-500 hover:underline transition-colors">
                      ✏️ 수정
                    </p>
                  </Link>
                  <DeleteButton postId={user.postId} />
                </div>
              </div>
            </div>
          ))}
        </section>
      </main>
    </div>
  );
};

export default Home;
