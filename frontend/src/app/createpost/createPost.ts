"use server";
import { cookies } from "next/headers";

export async function createPostAction(formData: FormData) {
  // formData에서 필요한 데이터를 추출합니다.
  const content = formData.get("content");
  const imageUrlRaw = formData.get("imageUrl");
  let imageUrlParsed: string[] = [];

  if (imageUrlRaw) {
    try {
      imageUrlParsed = JSON.parse(imageUrlRaw as string);
    } catch (err) {
      console.error("이미지 URL 파싱 오류:", err);
    }
  }

  // 서버에서 HTTP‑Only 쿠키로 저장된 토큰 읽기 (Next.js의 cookies API 사용)
  const token = (await cookies()).get("token")?.value;
  if (!token) {
    throw new Error("로그인이 필요합니다.");
  }

  // 백엔드 API에 POST 요청 처리
  const response = await fetch("http://localhost:8080/posts", {
    method: "POST",
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
    throw new Error(`업로드 실패: ${errorData}`);
  }

  // 성공 시 필요한 데이터 혹은 메시지를 반환할 수 있습니다.
  return await response.json();
}
