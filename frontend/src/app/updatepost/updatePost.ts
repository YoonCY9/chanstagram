"use server";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export async function updatePostAction(postId: number, formData: FormData) {
  const content = formData.get("content");
  const imageUrlRaw = formData.get("imageUrl");
  let imageUrlParsed: string[] = [];

  try {
    imageUrlParsed = JSON.parse(imageUrlRaw as string);
  } catch (err) {
    console.error("이미지 URL 파싱 오류:", err);
  }

  const token = (await cookies()).get("token")?.value;
  if (!token) throw new Error("로그인이 필요합니다.");

  // PUT 요청으로 변경 및 postId URL 포함
  const response = await fetch(`http://localhost:8080/posts/${postId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({
      content,
      imageUrl: imageUrlParsed,
    }),
  });

  if (!response.ok) {
    const errorData = await response.text();
    throw new Error(`수정 실패: ${errorData}`);
  }
}
