package com.beetrb.redis_study.oauth2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SYSTEM_EXCEPTION(500, "서버 에러가 발생했습니다."),
    INVALID_INPUT_VALUE(400, "유효하지 않은 입력값입니다."),
    NOT_FOUND_HANDLER(404, "404 NOT FOUND."),
    CANNOT_WORK_EXTERNAL_NETWORK(500, "외부 통신에서 에러가 났습니다."),
    /* 요청 관련 */
    HTTP_MESSAGE_NOT_READABLE(400, "요청 바디의 값이 올바르지 않습니다."),
    /* 인증 관련 */
    LOGIN_FAIL(401, "로그인에 실패했습니다."),
    UNAUTHORIZED(401, "권한이 없습니다."),
    UNAUTHENTICATED(401, "인증되지 않은 사용자입니다."),
    HANDLE_ACCESS_DENIED(403, "접근이 거부되었습니다."),
    /* 해시태그 관련 */
    NOT_FOUND_HASH(404,"해쉬태그가 없습니다."),

    /* 글 관련 */
    NOT_INPUT_REQUIRED(400,"필수입력값이 입력되지 않았습니다."),
    NOT_FOUND_POST(404,"게시물이 없습니다."),
    NOT_MATCH_PASSWORD(404,"게시물또는 비밀번호를 찾을 수가없습니다"),

    /* 공지사항 관련 */
    NOT_FOUNT_NOTICE(404, "공지사항이 없습니다."),

    /* 댓글 관련 */
    NOT_FOUND_COMMENT(404, "댓글이 없습니다."),

    /* 회원 관련  */
    NOT_FOUND_USER(404, "회원을 찾을 수 없습니다."),
    FILE_EXTENSION_NOT_SUPPORT(400, "지원하지 않는 이미지 형식입니다."),
    /* OAuth2 인증 관련 */
    PROVIDER_IS_UNCORRECTED(401, "지정된 로그인 제공자가 아닙니다."),
    UN_NORMAL_USER(404, "탈퇴한 회원이거나, 차단된 회원입니다."),

    /* 토큰 관련 */
    INVALID_JWT_SIGNATURE(401, "유효하지 않은 토큰 서명입니다."),
    MALFORMED_JWT_EXCEPTION(401, "토큰 값이 유효하지 않습니다."),
    EXPIRED_JWT_TOKEN(401 , "만료된 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(401, "지원되지 않는 토큰입니다."),
    INVALID_JWT_TOKEN(401, "유효하지 않은 토큰입니다."),
    ;
    private Integer status;
    private String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}
