"use client";
import {useEffect, useState} from "react";
import {useRouter} from "next/navigation";


interface UserResponse {
    nickName: string;
    profileImage: string;
}

interface CommentDetailedResponse {
    id: number;
    content: string;
    nickName: UserResponse;
}

interface PostDetailedResponse {
    postId: number;
    content: string;
    commentCount: number;
    imageUrl: string[];
    user: UserResponse;
    createdTime: string;
    updatedTime: string;
    comments: CommentDetailedResponse[];
    likeCount: number;
}

export default function PostDetailPage({params}: { params: { postId: string } }) {
    const [post, setPost] = useState<PostDetailedResponse | null>(null);
    const router = useRouter();

    const [liked, setLiked] = useState(false);

    const toggleLike = () => {
        setLiked(!liked); // 상태 토글
    }
    useEffect(() => {
        // ⚡️ 임시 데이터 (Mock Data)
        const mockPost: PostDetailedResponse = {
            postId: 1,
            content: "오늘 날씨 너무 좋다! ☀️",
            commentCount: 2,
            imageUrl: [
                "https://www.adobe.com/kr/creativecloud/photography/hub/guides/media_18e138984597cb1cf59b28b142b30f65d14a6fd36.jpeg?width=750&format=jpeg&optimize=medium", // ✅ 테스트용 이미지
            ],
            user: {
                nickName: "chanstagram_user",
                profileImage: "https://cdn.news.hidoc.co.kr/news/photo/202104/24409_58461_0826.jpg", // ✅ 프로필 이미지 테스트용
            },
            createdTime: new Date().toISOString(),
            updatedTime: new Date().toISOString(),
            comments: [
                {
                    id: 1,
                    content: "정말 좋네요! 😍",
                    nickName: {
                        nickName: "user1",
                        profileImage: "https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcxXz0g%2FbtrpdnEFZxC%2FkLMtiZk07PMa0rmvLGF1g0%2Fimg.jpg",
                    },
                },
            ],
            likeCount: 5,
        };


        // 🚀 API 대신 임시 데이터 사용
        setPost(mockPost);
    }, []);

    if (!post) return <div className="text-center p-10">게시글을 불러오는 중...</div>;

    return (
        <div className="max-w-2xl mx-auto mt-10 bg-white shadow-md rounded-lg overflow-hidden">
            {/* 상단 바 */}
            <div className="flex items-center justify-between p-4 border-b bg-white shadow-sm">
                {/* 홈으로 가는 버튼 */}
                <button onClick={() => router.push("/home")} className="text-3xl font-bold"
                        style={{fontFamily: "'Billabong', cursive"}}>
                    Chanstagram
                </button>
            </div>

            {/* 게시글 작성자 정보 */}
            <div className="flex items-center p-4 border-b">
                <img src={post.user.profileImage} alt="프로필 이미지" className="w-12 h-12 rounded-full mr-3"/>
                <div>
                    <button onClick={() => router.push("/profile")}
                            className="font-semibold">{post.user.nickName}</button>
                    <p className="text-gray-500 text-sm">{new Date(post.createdTime).toLocaleString()}</p>
                </div>
            </div>

            {/* 게시글 이미지 */}
            {post.imageUrl.length > 0 && (
                <div className="w-full bg-black">
                    {post.imageUrl.map((url, index) => (
                        <img key={index} src={url} alt="게시글 이미지" className="w-full object-cover max-h-[500px]"/>
                    ))}
                </div>
            )}

            {/* 게시글 내용 */}
            <div className="p-4">
                <p className="text-gray-800">{post.content}</p>
            </div>

            {/* 좋아요 및 댓글 수 */}
            <div className="p-4 flex justify-between border-t">
                <button onClick={() => setLiked(!liked)} className="text-3xl">
                    {liked ? (
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24"
                            fill="red"
                            className="w-8 h-8 transition duration-300 ease-in-out"
                        >
                            <path
                                d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
                        </svg>
                    ) : (
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24"
                            fill="none"
                            stroke="black"
                            strokeWidth="2"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            className="w-8 h-8 transition duration-300 ease-in-out"
                        >
                            <path
                                d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
                        </svg>
                    )}
                </button>
                <div className="font-semibold">💬 {post.commentCount} 댓글</div>
            </div>

            {/* 댓글 리스트 */}
            <div className="p-4 border-t">
                <h3 className="font-semibold mb-3">댓글</h3>
                {post.comments.length > 0 ? (
                    post.comments.map((comment) => (
                        <div key={comment.id} className="flex items-start space-x-3 mb-3">
                            <img src={comment.nickName.profileImage} alt="프로필" className="w-8 h-8 rounded-full"/>
                            <div className="bg-gray-100 p-2 rounded-xl">
                                <button className="font-bold">{comment.nickName.nickName}</button>
                                <p className="text-gray-700">{comment.content}</p>
                            </div>
                        </div>
                    ))
                ) : (
                    <p className="text-gray-500">댓글이 없습니다.</p>
                )}
            </div>

            {/* 뒤로 가기 버튼 */}
            <div className="p-4 border-t flex justify-center">
                <button
                    className="bg-gray-200 text-gray-800 px-4 py-2 rounded-lg hover:bg-gray-300"
                    onClick={() => router.back()}
                >
                    뒤로 가기
                </button>
            </div>
        </div>
    );
}

