# Entity

## user(사용자)

## post(게시글)

## comment(댓글)

# API Spec

### user(사용자) 필드

- userName(사용자 이름)
- nickName(닉네임) unique
- loginId(로그인용 아이디) -> DB 테이블에서도 ID 로 사용 unique
- password(비밀번호)
- gender(성별)
- birth(생년월일)
- content(소개)
- profileImage(프로필 이미지)
- phoneNumber(전화번호) unique

### 생성자

- userName(사용자 이름)
- nickName(닉네임)
- loginId(로그인용 아이디)
- password(비밀번호)
- gender(성별)
- birth(생년월일)
- content(소개)
- profileImage(프로필 이미지)
- phoneNumber(전화번호)

### 회원가입 (Post) (/users) @RequestBody

- userName(사용자 이름)
- nickName(닉네임)
- loginId(로그인용 아이디)
- password(비밀번호)
- gender(성별)
- birth(생년월일)
- content(소개)
- profileImage(프로필 이미지)
- phoneNumber(전화번호)

### 로그인 (post) (/login) // 토큰 넣는거 잊지 말기!!

- loginId(로그인용 아이디)
- password(비밀번호)

### 회원탈퇴 (Delete) (/users) // 토큰 받고

- password(비밀번호)
  (회원을 탈퇴한다면 게시글과 댓글도 삭제)

### 회원수정 (Put) (/users)  @RequestBody // 토큰으로 user 찾기

- userName(사용자 이름)
- nickName(닉네임)
- gender(성별)
- birth(생년월일)
- content(소개)
- profileImage(프로필 이미지)
- phoneNumber(전화번호)

## post (게시글) 필드

- List<String> imageUrl (사진들) elementCollection
- String content (내용)
- User user (사용자)
- Long id
- int commentCount (댓글 수)

### 게시글 생성 (post) (/posts)  @RequestBody (사용자) -> 토큰값으로

- List<String> imageUrl (사진들) elementCollection
- String content (내용)

### nickName 로 게시글 조회 (Get) (/posts/{nickName}) @PathVariable

-String nickName(사용자 닉네임)


### 게시글 전체 조회 (생성시간으로 정렬해서 return) (Get) (/posts)

### 게시글 상세 조회 (Get) (/posts/{postId}) @PathVariable

@PAthVariable 에서 받는 값
--Long postId

Response Dto 필드
- List<String> imageUrl (사진들)
- String content (내용)
- List<Comment> Comments -> 따로 (Comment 에 해당하는)dto 만드세요
  -> commentRepository 를 사용하세요!

### 게시글 수정 (토큰값 받기) (Put) (/posts/{postId}) @PAthVariable ,@RequestBody

(postId 로 찾은 post 의 user 가 토큰으로 찾은 user 와 동일한지)
@RequestBody 에서 받는 값
- List<String> imageUrl (사진들)
- String content (내용)

@PAthVariable 에서 받는 값
-Long postId

(imageUrl 을 주지 않으면 원해 있던 이미지 그대로 올리기)


### 게시글 삭제 (토큰값 받기) (Delete) (/posts/{postId}) @PathVariable

(postId 로 찾은 post 의 user 가 토큰으로 찾은 user 와 동일한지)

-@PAthVariable 에서 받는 값
- Long postId

## Comment (댓글) 필드

- String content (내용)
- Long id
- User user (사용자)
- Post post (게시글)

### 댓글 생성 (토큰) (Post) (/comments) @RequestBody

(토큰으로 사용자 찾기)
- String content (내용)
- Long postId (게시글 Id) -> Post 찾기

### 댓글 수정 (토큰) (Put) (/comments/{commentId}) @PathVariable, @RequestBody

(commentId 로 찾은 comment 의 user 가 토큰으로 찾은 user 와 동일한지)

@PAthVariable 에서 받는 값
- Long commentId

@RequestBody 에서 받는 값
- String content (내용)
### 댓글 삭제 (토큰) (Delete) (/comments/commentId)  @PathVariable

(commentId 로 찾은 comment 의 user 가 토큰으로 찾은 user 와 동일한지)

@PAthVariable 에서 받는 값
- Long commentId