# ---------- 1단계: 빌드 ----------
FROM gradle:8.8-jdk21 AS builder
WORKDIR /workspace

# 의존성 캐시 최적화: 먼저 설정/의존성 관련 파일만 복사
COPY build.gradle settings.gradle gradle.properties* ./
COPY gradle gradle
RUN gradle --version

# 소스 복사 후 빌드 (테스트는 제외 권장)
COPY . .
RUN gradle clean build -x test

# ---------- 2단계: 실행 ----------
FROM eclipse-temurin:21-jdk

# 타임존=Asia/Seoul
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# (선택) 비루트 유저 생성
RUN useradd -ms /bin/bash appuser
WORKDIR /app

# 빌드 산출물 복사
COPY --from=builder /workspace/build/libs/*.jar /app/app.jar

# (선택) JVM 옵션/스프링 프로파일
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Xms64m -Duser.timezone=Asia/Seoul"
ENV SPRING_PROFILES_ACTIVE=prod

# 헬스체크 (애플리케이션에 /actuator/health 있을 때)
# HEALTHCHECK --interval=30s --timeout=3s --retries=5 CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# 비루트로 실행
USER appuser

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]