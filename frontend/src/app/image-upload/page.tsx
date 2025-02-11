"use client";

// 현재는 필요하지 않지만, 이미지 업로드 하는 기능 샘플코드 입니다.
import { uploadImage } from "../createpost/uploadImage";

export default function UploadComponent() {
  return (
    <form action={uploadImage}>
      <input type="file" name="image" accept="image/*" />
      <button type="submit">업로드</button>
    </form>
  );
}
