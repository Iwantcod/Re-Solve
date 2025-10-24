package com.resolve.Re_Solve.wrong.service;

import com.resolve.Re_Solve.global.advice.ApplicationError;
import com.resolve.Re_Solve.global.advice.ApplicationException;
import com.resolve.Re_Solve.platform.entity.PlatformType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PageTitleService {
    // 타임아웃/크기 제한 등 기본값
    private static final int TIMEOUT_MS = (int) Duration.ofSeconds(5).toMillis();
    private static final int MAX_BODY_SIZE_BYTES = 2_000_000; // 2MB까지만 읽기 (과도한 다운로드 방지)

    /**
     * 해당 문제의 제목을 크롤링
     * @param num 문제 번호
     * @param type 문제 플랫폼 enum
     * @return 문제 제목
     * @throws IOException
     */
    public String fetchTitle(Integer num, PlatformType type) throws IOException {
        String url = type.getUrl() + num;

        // 1) URL 기본 검증 (http/https만 허용)
        URI uri = URI.create(url);
        if (!"http".equalsIgnoreCase(uri.getScheme()) && !"https".equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("지원하지 않는 스킴: " + uri.getScheme());
        }

        // 2) 요청 전송 + 파싱
        Connection conn = Jsoup
                .connect(url)
                .timeout(TIMEOUT_MS)
                .maxBodySize(MAX_BODY_SIZE_BYTES)
                .userAgent("Mozilla/5.0 (compatible; TitleFetcher/1.0; +https://andypjt.site)")
                .followRedirects(true)
                .ignoreContentType(true) // text/html 이 아니어도 일단 받아서 파싱 시도
                .ignoreHttpErrors(true); // 4xx, 5xx여도 Document로 받기

        // GET 실행
        Document doc = conn.get();

        // 3) HTTP 상태 코드 확인
        int status = conn.response().statusCode();
        if(status == 404) {
            // 존재하지 않는 문제
            throw new ApplicationException(ApplicationError.NOT_FOUND_PROBLEM);
        } else if (status < 200 || status >= 300) {
            // 플랫폼 접근 불가
            throw new ApplicationException(ApplicationError.CANNOT_ACCESS_SITE);
        }

        // 4) <title> 추출
        String title = doc.title(); // head > title 자동 추출 (null-safe)
        if (title != null && !title.isBlank()) {
            return cutString(title.trim(), type);
        }

        // 5) 폴백(선택): og:title이나 twitter:title을 시도
        String ogTitle = doc.selectFirst("meta[property=og:title]") != null
                ? doc.selectFirst("meta[property=og:title]").attr("content") : null;
        if (ogTitle != null && !ogTitle.isBlank()) {
            System.out.println("5번");
            return cutString(ogTitle.trim(), type);
        }

        // 6) 그래도 없으면 비어있다고 판단
        return "";
    }
    private String cutString(String title, PlatformType type) {
        if(type.equals(PlatformType.PROGRAMMERS)) {
            Pattern p = Pattern.compile("코딩테스트 연습 - (.*?) \\| 프로그래머스 스쿨");
            Matcher m = p.matcher(title);
            if (m.find()) {
                return m.group(1);
            }
        } else if(type.equals(PlatformType.BEAKJOON)) {
            Pattern p = Pattern.compile("\\d+번: (.*)");
            Matcher m = p.matcher(title);
            if (m.find()) {
                return m.group(1);
            }
        }
        return "";
    }
}
