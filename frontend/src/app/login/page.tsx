export default function LoginPage() {

    async function handleSubmit(formData: FormData) {
        'use server';


        const rawFormData = {
            loginId: formData.get("loginId"),
            password: formData.get("password"),
        };``
        console.log({...rawFormData});
    }

    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div className="bg-white p-8 rounded-xl shadow-lg w-96">
                <h1 className="text-2xl font-bold text-center mb-6">로그인</h1>
                <form action={handleSubmit}>
                    <div className="space-y-4">
                        <input
                            type="text"
                            name="loginId"
                            id="loginId"
                            placeholder="아이디"

                        />
                        <input
                            type="password"
                            name="password"
                            id="password"
                            placeholder="비밀번호"
                        />
                        <button
                            type="submit"
                            className="w-full bg-red-500 text-white py-3 rounded-lg font-semibold hover:bg-red-600 transition"
                        >
                            로그인
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}