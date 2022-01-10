# 방명록

### 화면
* 목록 화면
  * 전체 목록 조회(페이징)
  * 제목/내용/작성자 항목으로 조회(페이징)
* 등록 화면
  * 새로운 글 등록(등록 후 목록 화면으로 이동)
* 조회 화면
  * 목록 화면에서 특정 글 선택시 조회 화면으로 이동
  * 조회 화면에서는 수정/삭제가 가능한 화면으로 이동 가능
* 수정/삭제 화면
  * 삭제(삭제 후 목록 화면으로 이동)
  * 수정(수정 후 조회 화면으로 이동)



### API
```
목록 조회 GET /guestbook/list

등록 화면: GET /guestbook/register
등록 처리: POST /guestbook/register

조회 화면: GET /guestbook/read

수정/삭제 화면: GET /guestbook/modify
수정 처리: POST /guestbook/modify
삭제 처리: POST /guestbook/remove
```