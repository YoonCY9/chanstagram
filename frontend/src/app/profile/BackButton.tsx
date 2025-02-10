'use client'

import { useRouter } from 'next/navigation'

export default function BackButton(){
      const router = useRouter()

  const handleGoBack = () => {
    router.back()
  }

  return (
    <button
      className="text-gray-600 mb-4"
      onClick={handleGoBack}
    >
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18" />
</svg>

    </button>
  )
}
