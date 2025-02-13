import { NextResponse } from "next/server";

export async function POST(request: Request) {
  const response = NextResponse.json({ message: "Logged out successfully" });

  // 쿠키를 만료시키기 위해 값을 빈 문자열로 설정하고 만료일을 과거로 지정합니다.
  response.cookies.set("token", "", {
    path: "/",
    expires: new Date(0),
  });

  return response;
}
