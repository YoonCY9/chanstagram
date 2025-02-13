import { NextResponse } from "next/server";
import { cookies } from "next/headers";

export async function DELETE(request: Request) {
  // HttpOnly 쿠키에서 토큰 읽기
  const tokenCookie = (await cookies()).get("token");
  if (!tokenCookie) {
    return NextResponse.json(
      { error: "토큰이 존재하지 않습니다." },
      { status: 401 },
    );
  }
  const token = tokenCookie.value;

  // 백엔드 API 호출 시 Authorization 헤더에 "Bearer " 접두어 추가
  const backendUrl = process.env.BACKEND_URL || "http://localhost:8080";
  const backendResponse = await fetch(`${backendUrl}/users`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`, // 수정된 부분
    },
  });

  if (!backendResponse.ok) {
    return NextResponse.json(
      { error: "회원 탈퇴에 실패했습니다." },
      { status: backendResponse.status },
    );
  }

  // 회원 탈퇴 성공 시 쿠키 만료 처리
  const response = NextResponse.json({ message: "회원 탈퇴 성공" });
  response.cookies.set("token", "", { path: "/", expires: new Date(0) });
  return response;
}
