'use server';

import { cookies } from 'next/headers';
import { signIn } from './api';
import { redirect } from 'next/navigation';

export async function login() {
    const token = await signIn();

    const cookieStore = await cookies();
    cookieStore.set('token', token, {
        httpOnly: true,
    });

    redirect('/');
}
