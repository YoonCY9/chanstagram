// app/comments/[postId]/actions.ts

"use server"; // 🚀 서버 액션 선언

import { cookies } from "next/headers";
import { revalidatePath } from "next/cache";

// 댓글 생성 서버 액션 함수
export async function createCommentAction(formData: FormData) {
  // 1) FormData에서 postId, content 추출
  const postId = formData.get("postId") as string;
  const content = formData.get("content") as string;
  if (!content?.trim()) {
    throw new Error("댓글 내용이 비어있습니다.");
  }

  // 2) HttpOnly 쿠키에서 토큰 읽기
  const token = (await cookies()).get("token")?.value;
  if (!token) {
    throw new Error("로그인이 필요합니다.");
  }

  // 3) 백엔드로 POST /comments/{postId}
  const response = await fetch(`http://localhost:8080/comments`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({
      content,
      postId: Number(postId),
    }),
  });

  if (!response.ok) {
    throw new Error("댓글 생성 실패: " + response.statusText);
  }

  // 4) 생성 후 revalidatePath로 페이지 새로고침(SSR 데이터 갱신)
  //    예: /comments/3 -> `/comments/3`
  revalidatePath(`/comments/${postId}`);
}
