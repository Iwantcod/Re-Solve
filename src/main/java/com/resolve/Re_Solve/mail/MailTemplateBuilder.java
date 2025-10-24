package com.resolve.Re_Solve.mail;

import com.resolve.Re_Solve.wrong.dto.MailFormatDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MailTemplateBuilder {
    private MailTemplateBuilder() {}

    /** 메일 제목: [Re-Solve] ㅇㅇㅇ님이 오늘 다시 풀어볼 문제 */
    public static String buildSubject(String username) {
        return "[Re-Solve] " + username + "님이 오늘 다시 풀어볼 문제";
    }

    /** 메일 본문(HTML) 생성 */
    public static String buildHtml(String username, List<MailFormatDto> items) {
        // past(1,3,7,21) 기준으로 그룹핑
        Map<Integer, List<MailFormatDto>> byPast = items == null ? Map.of() :
                items.stream().collect(Collectors.groupingBy(
                        m -> Optional.ofNullable(m.getPast()).orElse(-1),
                        Collectors.toList()
                ));

        // 섹션 정의: past -> (섹션 타이틀, 부제)
        record Section(int past, String title, String subtitle) {}
        List<Section> sections = List.of(
                new Section(1,  "1일 전 틀렸던 문제",  "10분까지 떠올려보고 다시 풀기"),
                new Section(3,  "3일 전 틀렸던 문제",  "20분까지 떠올려보고 다시 풀기"),
                new Section(7,  "7일 전 틀렸던 문제",  "처음 봤을 때처럼 완전 새로 풀기"),
                new Section(21, "21일 전 틀렸던 문제", "처음 봤을 때처럼 완전 새로 풀기")
        );

        StringBuilder sb = new StringBuilder(2048);
        sb.append("""
        <div style="font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827; line-height:1.6;">
          <h2 style="margin:0 0 8px 0;">""").append(escape(username)).append("""
          님이 오늘 다시 풀어볼 문제</h2>
          <p style="margin:0 0 18px 0; color:#6b7280;">Re-Solve가 추천하는 복습 큐입니다.</p>
        """);

        for (Section s : sections) {
            List<MailFormatDto> list = byPast.getOrDefault(s.past(), Collections.emptyList());
            if (list.isEmpty()) continue; // 해당 past에 항목 없으면 섹션 생략

            sb.append("""
              <div style="margin:22px 0 10px 0;">
                <h3 style="margin:0 0 4px 0;">""").append(s.title()).append("""
                </h3>
                <div style="margin:0 0 10px 0; color:#374151;">""").append(s.subtitle()).append("""
            </div>
            """);

            // 표 렌더링
            sb.append("""
                <table role="table" cellpadding="0" cellspacing="0" border="0"
                       style="border-collapse:collapse; width:100%; max-width:720px; border:1px solid #e5e7eb; border-radius:8px; overflow:hidden;">
                  <thead>
                    <tr style="background:#f9fafb;">
                      <th align="left" style="padding:10px 12px; font-size:14px; border-bottom:1px solid #e5e7eb;">플랫폼</th>
                      <th align="left" style="padding:10px 12px; font-size:14px; border-bottom:1px solid #e5e7eb;">문제명</th>
                      <th align="left" style="padding:10px 12px; font-size:14px; border-bottom:1px solid #e5e7eb;">링크</th>
                    </tr>
                  </thead>
                  <tbody>
            """);

            for (MailFormatDto m : list) {
                String platform = nvl(m.getPlatformName());
                String title    = nvl(m.getTitle());
                String url      = nvl(m.getUrl());
                // 문제 번호를 제목 옆에 보조로 표기(선택)
                String titleWithNum = title + (m.getNum() != null ? " (#" + m.getNum() + ")" : "");

                sb.append("<tr>")
                        .append(td(escape(platform)))
                        .append(td(escape(titleWithNum)))
                        .append(tdLink(url))
                        .append("</tr>");
            }

            sb.append("""
                  </tbody>
                </table>
              </div>
            """);
        }

        // 섹션이 하나도 없을 때 안내
        boolean hasAny = sections.stream().anyMatch(s -> byPast.getOrDefault(s.past(), List.of()).size() > 0);
        if (!hasAny) {
            sb.append("""
              <div style="margin:18px 0; color:#6b7280;">오늘은 복습할 문제가 없어요. 내일 다시 만나요!</div>
            """);
        }

        // 푸터
        sb.append("""
          <hr style="border:none; border-top:1px solid #e5e7eb; margin:24px 0;">
          <div style="font-size:12px; color:#6b7280;">
            이 메일은 Re-Solve 알림 설정에 따라 발송되었습니다.
          </div>
        </div>
        """);

        return sb.toString();
    }

    // ---------- helpers ----------

    private static String td(String innerHtml) {
        return "<td style=\"padding:10px 12px; font-size:14px; border-bottom:1px solid #f3f4f6;\">" + innerHtml + "</td>";
    }

    private static String tdLink(String url) {
        if (url == null || url.isBlank()) return td("-");
        String safe = escape(url);
        return "<td style=\"padding:10px 12px; font-size:14px; border-bottom:1px solid #f3f4f6;\">" +
                "<a href=\"" + safe + "\" target=\"_blank\" style=\"color:#111827; text-decoration:underline;\">바로가기</a></td>";
    }

    private static String nvl(String s) { return (s == null) ? "" : s; }

    /** 매우 간단한 HTML escape (필요시 Apache Commons Text의 StringEscapeUtils 사용 가능) */
    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("\"","&quot;")
                .replace("'","&#39;");
    }
}
