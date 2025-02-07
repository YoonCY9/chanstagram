"use client";
import { useState, useRef, useEffect, useCallback } from "react";
import { useRouter } from "next/navigation";
import Underbar from "./Underbar"

interface Post {
  content: string;
  commentCount: number;
  likeCount: number;
  imageUrl: string;
  userNickname: string;
}

interface FeedProps {
  posts: Post[]; // Post 객체 배열을 받음
}

const dummyPosts: Post[] = [
  {
    content: "오늘 카페에서 마신 라떼가 너무 맛있었다! ☕✨",
    commentCount: 5,
    likeCount: 12,
    imageUrl: "https://source.unsplash.com/random/300x200?coffee",
    userNickname: "라떼중독자",
  },
  {
    content: "우리 집 고양이가 박스를 발견하더니 안에서 안 나옴 🐱📦",
    commentCount: 8,
    likeCount: 20,
    imageUrl: "https://source.unsplash.com/random/300x200?cat",
    userNickname: "냥이집사",
  },
  {
    content: "방금 만든 김치볶음밥 비주얼 미쳤다🔥🍳",
    commentCount: 3,
    likeCount: 18,
    imageUrl: "https://source.unsplash.com/random/300x200?food",
    userNickname: "요리하는곰돌이",
  },
  {
    content: "제주도로 여행 왔는데 바람이 진짜 장난 아니다 🌊🍊",
    commentCount: 6,
    likeCount: 25,
    imageUrl: "https://source.unsplash.com/random/300x200?jeju",
    userNickname: "여행러버",
  },
  {
    content: "React랑 Next.js 공부 시작했는데 너무 재밌다! ⚛️🚀",
    commentCount: 10,
    likeCount: 30,
    imageUrl: "https://source.unsplash.com/random/300x200?code",
    userNickname: "개발냥",
  },
  {
    content: "강아지가 산책 나가자고 째려보는 중... 🐶👀",
    commentCount: 4,
    likeCount: 22,
    imageUrl: "https://source.unsplash.com/random/300x200?dog",
    userNickname: "댕댕이사랑해",
  },
  {
    content: "오늘 찍은 노을 사진, 너무 예쁘다 🌅❤️",
    commentCount: 9,
    likeCount: 28,
    imageUrl: "https://source.unsplash.com/random/300x200?sunset",
    userNickname: "사진찍는고양이",
  },
  {
    content: "커피 없이 하루를 어떻게 살지... ☕😵",
    commentCount: 2,
    likeCount: 14,
    imageUrl: "https://source.unsplash.com/random/300x200?coffee",
    userNickname: "카페인중독자",
  },
  {
    content: "새로 산 키보드 타건감 미쳤다. 기계식 최고! ⌨️🔥",
    commentCount: 7,
    likeCount: 21,
    imageUrl: "https://source.unsplash.com/random/300x200?keyboard",
    userNickname: "기계식러버",
  },
  {
    content: "비 오는 날에는 역시 라면이지 🌧️🍜",
    commentCount: 5,
    likeCount: 17,
    imageUrl: "https://source.unsplash.com/random/300x200?ramen",
    userNickname: "라면덕후",
  },
  {
    content: "책 한 권 다 읽었다! 이번엔 무슨 책 볼까 📖🤔",
    commentCount: 3,
    likeCount: 10,
    imageUrl: "https://source.unsplash.com/random/300x200?book",
    userNickname: "책읽는사람",
  },
  {
    content: "오늘 처음으로 헬스장 가봤는데 너무 힘들다... 🏋️‍♂️",
    commentCount: 6,
    likeCount: 15,
    imageUrl: "https://source.unsplash.com/random/300x200?gym",
    userNickname: "운동초보",
  },
  {
    content: "집에서 만드는 수제 버거, 이게 진짜 맛이다! 🍔🔥",
    commentCount: 4,
    likeCount: 19,
    imageUrl: "https://source.unsplash.com/random/300x200?burger",
    userNickname: "햄버거사랑해",
  },
  {
    content: "밤하늘 별 찍으려고 나왔는데 구름 낀 거 실화? 😭🌌",
    commentCount: 8,
    likeCount: 24,
    imageUrl: "https://source.unsplash.com/random/300x200?night",
    userNickname: "별사진러",
  },
  {
    content: "강아지랑 산책 갔다가 귀여운 강아지 친구 만남! 🐶🐾",
    commentCount: 5,
    likeCount: 20,
    imageUrl: "https://source.unsplash.com/random/300x200?puppy",
    userNickname: "산책러",
  },
  {
    content: "카페에서 노트북으로 코딩하는 게 제일 행복함 💻☕",
    commentCount: 6,
    likeCount: 22,
    imageUrl: "https://source.unsplash.com/random/300x200?laptop",
    userNickname: "코딩하는고양이",
  },
  {
    content: "새로 산 필름 카메라로 찍은 사진 너무 감성적이다 📷✨",
    commentCount: 4,
    likeCount: 18,
    imageUrl: "https://source.unsplash.com/random/300x200?film",
    userNickname: "필카덕후",
  },
  {
    content: "드디어 첫 여행 브이로그 업로드 완료! ✈️📹",
    commentCount: 9,
    likeCount: 26,
    imageUrl: "https://source.unsplash.com/random/300x200?vlog",
    userNickname: "여행브이로거",
  },
  {
    content: "이틀 동안 달린 RPG 게임 클리어했다! 🎮🔥",
    commentCount: 7,
    likeCount: 23,
    imageUrl: "https://source.unsplash.com/random/300x200?game",
    userNickname: "게임광",
  },
  {
    content: "새로 배운 레시피로 초코 쿠키 만들었는데 대성공 🍪💕",
    commentCount: 5,
    likeCount: 16,
    imageUrl: "https://source.unsplash.com/random/300x200?cookie",
    userNickname: "베이킹러버",
  },
];

function Navigator({
  setSearchQuery,
}: {
  setSearchQuery: (query: string) => void;
}) {
  const router = useRouter(); // useRouter 훅 사용

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    const query = (e.target as HTMLFormElement).q.value;
    setSearchQuery(query);

    // URL의 쿼리 스트링 업데이트
    router.push(`?q=${query}`);
  };

  return (
    <nav className="p-4 bg-gray-200">
      <form className="flex gap-2" onSubmit={handleSearch}>
        <input
          type="text"
          name="q"
          placeholder="검색..."
          className="border p-2 rounded-md w-full"
        />
        <button type="submit">검색</button>
      </form>
    </nav>
  );
}

function Feed({ posts, searchQuery }: { posts: Post[]; searchQuery: string }) {
  const [visiblePosts, setVisiblePosts] = useState<Post[]>(posts.slice(0, 6));
  const [page, setPage] = useState(1);
  const loaderRef = useRef<HTMLDivElement | null>(null);

  const loadMorePosts = useCallback(() => {
    setPage((prevPage) => {
      const nextPage = prevPage + 1;
      setVisiblePosts(posts.slice(0, nextPage * 6));
      return nextPage;
    });
  }, [posts]);

  const filteredPosts = posts.filter(
    (post) =>
      post.content.toLowerCase().includes(searchQuery.toLowerCase()) ||
      post.userNickname.toLowerCase().includes(searchQuery.toLowerCase()),
  );

  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          loadMorePosts();
        }
      },
      { threshold: 1.0 },
    );

    if (loaderRef.current) observer.observe(loaderRef.current);
    return () => observer.disconnect();
  }, [loadMorePosts]);

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4 p-4">
      {filteredPosts.map((post, index) => (
        <div
          key={index}
          className="bg-white rounded-xl shadow-md overflow-hidden"
        >
          <img
            src={post.imageUrl}
            alt={post.content}
            className="w-full h-40 object-cover"
          />
          <div className="p-4">
            <h3 className="text-lg font-bold">{post.userNickname}</h3>
            <p className="text-sm text-gray-600 truncate">{post.content}</p>
            <div className="flex justify-between mt-2 text-sm text-gray-500">
              <span>❤️ {post.likeCount}</span>
              <span>💬 {post.commentCount}</span>
            </div>
          </div>
        </div>
      ))}
      <div ref={loaderRef} className="h-10"></div>
    </div>
  );
}

export default function Page({
  searchParams,
}: {
  searchParams: { [key: string]: string | string[] | undefined };
}) {
  const [searchQuery, setSearchQuery] = useState<string>(
    (searchParams.q as string) || "",
  );

  // searchParams가 Promise로 처리되도록 useEffect에서 처리
  useEffect(() => {
    const fetchSearchParams = async () => {
      if (searchParams.q) {
        const query = Array.isArray(searchParams.q)
          ? searchParams.q[0]
          : searchParams.q;
        setSearchQuery(query);
      }
    };

    fetchSearchParams();
  }, [searchParams]);

  return (
    <div className="h-screen flex flex-col">
      <Navigator setSearchQuery={setSearchQuery} />
      <Feed posts={dummyPosts} searchQuery={searchQuery} />
      <Underbar/>
    </div>
  );
}
