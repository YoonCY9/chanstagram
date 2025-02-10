import {cookies} from 'next/headers';

interface Profile {
    // 생략
}

interface LoginRequest {
    loginId: string;
    password: string;
}

export async function signIn({loginId, password}: LoginRequest): Promise<string> {

    const response = await fetch('http://localhost:8000/login', {
        method: 'POST',
        body: JSON.stringify({loginId, password}),
        headers: {'Content-Type': 'application/json'},
    });

    const token = await response.json();
    return token;
}

export async function fetchProfile(): Promise<Profile | undefined> {
    try {
        const cookieStore = await cookies();
        const cookie = cookieStore.get('token');
        const token = cookie?.value;
        if (!token) {
            return undefined;
        }

        const response = await fetch('http://localhost:3000/me', {
          headers: {
            Authorization: token,
          },
        });
        if (!response.ok) {
          throw new Error(`Failed to fetch profile: ${response.statusText}`);
        }

        return await response.json();

    } catch (error) {
        // 요청 타임아웃의 경우
        if (error instanceof DOMException && error.name === 'AbortError') {
            throw new Error('Request timed out');
        }
        // 네트워크 혹은 JSON 파싱 에러의 경우
        if (error instanceof TypeError) {
            throw new Error('Network or parsing error');
        }

        // 그 외의 예외 처리
        throw error;
    }
}
