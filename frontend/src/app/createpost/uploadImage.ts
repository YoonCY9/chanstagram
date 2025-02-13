"use server";

export async function uploadImage(formData: FormData) {
  // FormData에서 "image" 필드를 가져옵니다.
  const file = formData.get("image") as File | null;
  if (!file) {
    return { error: "파일을 찾을 수 없습니다." };
  }

  // 파일을 ArrayBuffer로 읽고 base64 문자열로 변환합니다.
  const arrayBuffer = await file.arrayBuffer();
  const base64Content = Buffer.from(arrayBuffer).toString("base64");

  // imgbb API URL (API 키가 쿼리 파라미터로 포함됨)
  const imgbbUrl =
    "https://api.imgbb.com/1/upload?key=e263a076115c1e96adff6b26263ac866";

  // imgbb API에 전달할 form data 구성
  const imgbbFormData = new FormData();
  imgbbFormData.append("image", base64Content);

  // imgbb API에 POST 요청 전송
  const response = await fetch(imgbbUrl, {
    method: "POST",
    body: imgbbFormData,
  });
  const result = await response.json();

  // 결과로 반환되는 JSON에서 이미지 URL (예: result.data.display_url)를 DB에 저장할 수 있습니다.
  return result;
}
