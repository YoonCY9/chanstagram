# Entity

## user(사용자)

## post(게시글)

## comment(댓글)

# API Spec

### user(사용자) 필드 !!!

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


Post like(post_user) User
## 추가 기능
- ### 좋아요  Like
- #### Entity 필드
- Long id,
- Long userId
- Long postId
  -> 좋아요값이 true 이면 like 테이블에 생성, false 이면 삭제(먼저 값 확인 하고 true 이면 삭제,false 이면 그대로)

### like의 추가기능
- ~좋아요 수 만들기 
- 좋아요가 가장 많은 포스트 순으로 정렬
- ~user가 좋아요한 포스트만 filter하기
- 


- ### 팔로우 Follow
- #### Entity 필드
- Long id
- User follower(팔로우 하는사람 : 로그인 한 사람)
- User followee(팔로우를 당하는 사람)

### 상대를 팔로우 하기 (Post) ("/follows") @PathVariable
(토큰값을 받아서 본인 loginId 값 추출)
- String followeeNickName (팔로우할 사람의 닉네임)
- 이미 팔로우한 사람을 팔로우 요청을 다시 보내면 팔로우 취소 (follow id 삭제)
### 해시태그
- #### Entity 필드
- Long id
- String hashTag

### 해시태그를 생성 (Post) ("/hashtags") @RequestBody
- String hashTag (해시태그를 할 단어)

### 사람태그
- #### Entity 필드
- User user (태그 할 사람)
- Long id

### 사람태그를 생성 (Post) ("/usertag") @PathVariable
- String nickName (태그를 할 사람 닉네임)