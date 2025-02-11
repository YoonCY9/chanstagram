import React from "react";

// 1) 댓글 조회 함수
async function fetchComments(postId: string) {
    const res = await fetch(`http://localhost:8080/comments/${postId}`, {
        // SSR 시 항상 최신 데이터 가져오기
        cache: "no-store",
    });

    if (!res.ok) {
        throw new Error(`Failed to fetch comments: ${res.statusText}`);
    }

    // 백엔드 구조: { comments: [ ... ] }
    return res.json();
}

// 2) 서버 컴포넌트
export default async function CommentsPage({params}: { params: { postId: string } }) {
    const {postId} = params;

    // postId가 숫자인지 간단히 체크
    if (!/^\d+$/.test(postId)) {
        return <p className="text-red-500">Error: Invalid postId</p>;
    }

    try {
        // API 호출
        const data = await fetchComments(postId);
        // data.comments 형태: CommentResponse[] (백엔드에서 정의한 DTO)
        const comments = data.comments;

        return (
            <div className="max-w-md mx-auto p-4">
                {/* 전체 카드 박스: 인스타 피드 느낌 */}
                <div className="bg-white border rounded-lg shadow-sm">
                    {/* 헤더 영역 (제목 등) */}
                    <div className="p-4 border-b">
                        <h2 className="text-xl font-bold">Comments</h2>
                    </div>

                    {/* 콘텐츠 영역 */}
                    <div className="p-4">
                        {/* 댓글이 없을 때 */}
                        {comments.length === 0 ? (
                            <p className="text-gray-500 text-center">No comments yet.</p>
                        ) : (
                            <ul className="space-y-4">
                                {comments.map((comment: any) => (
                                    <li key={comment.id} className="flex items-start space-x-3">
                                        {/* 프로필 이미지 (아바타) */}
                                        <img
                                            className="w-9 h-9 rounded-full object-cover"
                                            src="/images/default-avatar.png" // 실제 프로필 이미지 경로
                                            alt="Avatar"
                                        />

                                        {/* 텍스트 블록 */}
                                        <div className="flex-1">
                                            {/* 아이디 + 내용 */}
                                            <p className="text-sm text-gray-700">
                                        <span className="font-semibold mr-1">
                                            @{comment.loginId}
                                        </span>
                                                {comment.content}
                                            </p>

                                            {/* 시간/버튼 등 */}
                                            <div className="flex space-x-5 mt-1 text-xs text-gray-400">
                                                <button className="hover:underline">
                                                    Like
                                                </button>
                                                <button className="hover:underline">
                                                    Reply
                                                </button>
                                                <span>1m</span>
                                                {/* 실제 시간 계산 로직은 추가 필요 */}
                                            </div>
                                        </div>
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>
                </div>
            </div>
        );
    } catch (error) {
        console.error(error);
        return <p className="text-red-500">Error loading comments</p>;
    }
}
