# 1. JDK 기반 이미지 사용 (Spring Boot 3.x에 맞춰 JDK 17~21 권장)
FROM eclipse-temurin:17-jdk-alpine

# 2. JAR 파일을 앱 디렉토리로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 포트 오픈 (application.yml 설정과 맞출 것)
EXPOSE 8080

# 4. 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]