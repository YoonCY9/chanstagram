"use server";

import { cookies } from "next/headers";

export async function deletePost(postId: number) {
  const token = (await cookies()).get("token")?.value;
  if (!token) {
    throw new Error("로그인이 필요합니다.");
  }

  // DELETE 요청을 백엔드로 보냄
  const response = await fetch(`http://localhost:8080/posts/${postId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to delete post");
  }

  return { success: true };
}
