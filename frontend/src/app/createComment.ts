// "use server"; // 🚀 서버 액션 사용 선언
//
// interface CommentResponse {
//     content: string;
//     postId: number;
// }
//
// export async function createComment(formData: FormData) {
//     const content = formData.get("content") as string;
//     const postId = Number(formData.get("postId"));
//
//     if (!content.trim()) return;
//
//     const token = "your-auth-token"; // 인증 토큰을 여기에 넣으세요.
//     // 예를 들어, 로그인 시 받아온 토큰을 사용하거나, 서버 환경변수에서 불러올 수 있음
//
//     const response = await fetch('http://localhost:8080/comments', {
//         method: 'POST',
//         body: JSON.stringify({ content, postId }),
//         headers: {
//             'Content-Type': 'application/json',
//             'Authorization': `Bearer ${token}`  // 헤더에 인증 토큰 추가
//         },
//     });
//
//     if (!response.ok) {
//         throw new Error(`Failed to create comment: ${response.statusText}`);
//     }
//
//     return response.json(); // 성공하면 JSON 데이터 반환
// }
