-- board 카테고리 샘플 생성
-- 전체보기, 자유게시판, 관심사 공유, 정모후기, 가입인사, 공지사항

SELECT * FROM board_category;
-- 카테고리 이름 타입 변경
ALTER TABLE board_category MODIFY b_cate_name VARCHAR2(40);

INSERT INTO board_category(b_cate_num, b_cate_name, m_num)
VALUES(BOARD_CATEGORY_SEQ.nextval, '전체보기', 1);
INSERT INTO board_category(b_cate_num, b_cate_name, m_num)
VALUES(BOARD_CATEGORY_SEQ.nextval, '자유게시판', 1);
INSERT INTO board_category(b_cate_num, b_cate_name, m_num)
VALUES(BOARD_CATEGORY_SEQ.nextval, '관심사 공유', 1);
INSERT INTO board_category(b_cate_num, b_cate_name, m_num)
VALUES(BOARD_CATEGORY_SEQ.nextval, '정모후기', 1);
INSERT INTO board_category(b_cate_num, b_cate_name, m_num)
VALUES(BOARD_CATEGORY_SEQ.nextval, '가입인사', 1);
INSERT INTO board_category(b_cate_num, b_cate_name, m_num)
VALUES(BOARD_CATEGORY_SEQ.nextval, '공지사항', 1);



-- board 샘플 생성
SELECT * FROM board;
INSERT INTO board(b_num, b_cate_name, b_title, b_content, m_num, u_id)
VALUES(BOARD_NUM.nextval, '자유게시판', 'SQLDeveloper로 쓴 게시판 내용', 'SQL', 14, 'thffk');


COMMIT;













