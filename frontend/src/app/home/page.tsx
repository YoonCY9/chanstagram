"use client";

import React, {useEffect, useState} from "react";
import {useRouter} from "next/navigation";
import Link from "next/link";

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
            const response = await fetch("http://localhost:8080/posts?page=1&size=10", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
            });
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
                    : user
            )
        );
    };

    if (loading) {
        return <div className="flex justify-center items-center h-screen">Loading...</div>;
    }

    return (
        <div className="flex flex-col h-screen w-full max-w-md mx-auto bg-gray-100">
            {/* 헤더 */}
            <header className="flex items-center justify-between px-4 py-2 bg-white shadow-md">
                <button onClick={() => window.location.reload()} className="text-xl font-bold">
                    Chanstagram
                </button>
                {/* 돋보기 버튼 */}
                <button
                    onClick={() => router.push("/search")}
                    className="text-gray-500 hover:text-gray-800 transition-colors"
                >
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth={2}
                        stroke="currentColor"
                        className="w-6 h-6"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="M21 21l-4.35-4.35M11 18a7 7 0 100-14 7 7 0 000 14z"
                        />
                    </svg>
                </button>
            </header>

            {/* 메인 피드 */}
            <main className="flex-1 overflow-y-scroll p-4">
                {/* 게시물 섹션 */}
                <section className="space-y-4">
                    {users.map((user, index) => (
                        <div key={index} className="bg-white rounded-xl shadow-md overflow-hidden">
                            {/* 프로필 */}
                            <button onClick={() => router.push("/profile")} className="flex items-center p-6">
                                <img
                                    src={user.profileImage}
                                    alt={`${user.nickName} profile`}
                                    className="w-10 h-10 rounded-full object-cover"
                                />
                                <span className="ml-4 font-semibold">{user.nickName}</span>
                            </button>

                            {/* 게시물 이미지 */}
                            <div className="flex overflow-x-scroll space-x-2">
                                {user.imageUrls.map((url, imgIndex) => (
                                    <img
                                        key={imgIndex}
                                        src={url}
                                        alt={`Post image ${imgIndex + 1}`}
                                        className="w-64 h-64 object-cover rounded-lg"
                                    />
                                ))}
                            </div>

                            {/* Content 박스 */}
                            <div className="px-4 py-5">
                                <p className="text-sm bg-white">{user.content}</p>
                            </div>

                            {/* 액션 버튼 */}
                            <div className="flex items-center justify-between px-4 py-2">
                                <button onClick={() => handleLike(index)}>
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        fill={user.isLiked ? "red" : "none"}
                                        viewBox="0 0 24 24"
                                        strokeWidth={2}
                                        stroke="currentColor"
                                        className="w-6 h-6"
                                    >
                                        <path
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09A6.49 6.49 0 0116.5 3c3.04 0 5.5 2.46 5.5 5.5 0 3.78-3.4 6.86-8.55 11.54L12 21.35z"
                                        />
                                    </svg>
                                </button>
                            </div>

                            {/* 좋아요/댓글 수 */}
                            <div className="px-4 py-2">
                                <p className="text-sm font-semibold">좋아요 {user.likeCount}개</p>

                                <Link href={`/comments/${user.postId}`}>
                                    <p className="text-sm text-gray-600">댓글 보기</p>
                                    </Link>
                            </div>
                        </div>
                    ))}
                </section>
            </main>
        </div>
    );
};

export default Home;
