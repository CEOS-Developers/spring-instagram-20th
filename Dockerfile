# 1. 베이스 이미지 설정 (Java 17 사용)
FROM openjdk:17-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 빌드 결과물 복사
COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

# 4. 실행 명령 설정
ENTRYPOINT ["java", "-jar", "app.jar"]