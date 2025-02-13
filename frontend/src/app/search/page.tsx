       "use client";
        import { Search } from "lucide-react";
import { useState, useRef, useEffect, useCallback } from "react";
import { useRouter } from "next/navigation";
import Underbar from "./Underbar";


interface PostResponse {
  content: string;
  commentCount: number;
  likeCount: number;
  imageUrl: string;
  userNickname: string;
}

export default async function Page({searchParams}: { searchParams: Promise<{ q: string | undefined }> }) {
    const q = (await searchParams).q;

    const posts = await fetchPosts(q);


    async function fetchPosts(q: string | undefined) : Promise<PostResponse[]> {
        const baseUrl = `http://localhost:8080/posts?page=1&size=10`;

        const response = q ? await fetch(`${baseUrl}&searchby=${q}`) : await fetch(`${baseUrl}`);
        let posts = await response.json();

        if(!response.ok){
            throw new Error('Network response is not ok');
        }

        return posts;
    }

    return (
        <div className="min-h-screen flex flex-col items-center">

            <form className="mb-4 w-full max-w-md flex items-center border-b pb-2">
                <input
                    type="text"
                    name="q"
                    placeholder="검색..."
                    className="flex-1 p-2 outline-none"
                />
                <button type="submit" className="p-2">
                    <Search size={20} />
                </button>
            </form>


            <div className="w-full max-w-3xl flex flex-col items-center">
                {posts.map((p, index) => (
                    <div key={index} className="w-full h-screen flex flex-col">

                        <div className="w-full h-full">
                            <img
                                src={p.imageUrl}
                                alt="Post Image"
                                className="w-full h-full object-cover"
                            />
                        </div>

                        <div className="p-4 flex flex-col items-start w-full">
                            <h3 className="text-lg font-bold">{p.userNickname}</h3>
                            <p className="text-sm text-gray-600">{p.content}</p>

                            <div className="flex justify-between w-full mt-2 text-sm text-gray-500">
                                <span>❤️ {p.likeCount}</span>
                                <span>💬 {p.commentCount}</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
