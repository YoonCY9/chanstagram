"use client";
import { useState, useRef, useEffect, useCallback } from "react";
import Link from "next/link";
import { Home, Search, PlusCircle, User } from "lucide-react";

export default function Underbar() {
    return (
        <div className="fixed bottom-0 left-1/2 transform -translate-x-1/2 max-w-[480px] w-full bg-white border-t shadow-md flex justify-around p-3">
            <Link href="/home" className="flex flex-col items-center text-gray-600">
                <Home size={24} />
                <span className="text-xs">홈</span>
            </Link>
            <Link href="/search" className="flex flex-col items-center text-gray-600">
                <Search size={24} />
                <span className="text-xs">검색</span>
            </Link>
            <Link href="/createpost" className="flex flex-col items-center text-gray-600">
                <PlusCircle size={24} />
                <span className="text-xs">포스트</span>
            </Link>
            <Link href="/profile" className="flex flex-col items-center text-gray-600">
                <User size={24} />
                <span className="text-xs">프로필</span>
            </Link>
        </div>
    );
}