'use server';

import {cookies} from 'next/headers';
import {signIn} from './api';
import {redirect} from 'next/navigation';

export async function logIn(formData: FormData) {

    const token = await signIn(formData.get("loginId"), formData.get("password"));

    const cookieStore = await cookies();
    cookieStore.set('token', token, {
        httpOnly: true,
    });

    redirect('/home');
}
