'use server';

import {cookies} from 'next/headers';
import {signIn} from './api';
import {redirect} from 'next/navigation';

export async function logIn(formData: FormData) {
    const loginId = formData.get("loginId");
    if (!loginId) {
        throw new Error('Invalid loginId provided');
    }
    const password = formData.get("password");
    if (!password) {
        throw new Error('Invalid password');
    }

    const token = await signIn({loginId: loginId as string, password: password as string});

    const cookieStore = await cookies();
    cookieStore.set('token', token, {
        httpOnly: true,
    });

    redirect('/home');
}
