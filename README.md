# book_manager2

* jpa Study ToyProject(도서관리페이지)

## Synopsis
      
* Project : SpringBoot + Gradle + Jpa
* FrontEnd : jquery + bootstrap + jsp + jstl
* BackEnd : spring-boot-data-jpa + queryDsl

## Development environment

* STS 3.9.9
* Java 1.8
* SpringBoot 2.2.1
* Gradle 5.6.X
* mariaDB 10.2.X

## Use library

* jdk-1.8.0.221
* spring-boot-2.2.1.RELEASE
* spring-core-5.2.1
Gradle-5.6.4
lombok-1.16.20
mariadb-java-client-2.3.0
springfox-swagger2-2.9.2
springfox-swagger-ui-2.6.1
spring-data-jpa-2.2.1.RELEASE
querydsl-core-4.2.1
HikariCP-3.4.1
logback-core-1.2.3
logback-classic-1.2.3
log4j-api-2.12.1
tomcat-embed-core-9.0.27
spring-boot-actuator-2.2.1
.....

## Swagger URL(API Document)
* http://localhost:8080/swagger-ui.html

## Function list

* BOOK 리스트보기(Paging)
* BOOK 검색/정렬
* BOOK 등록/수정/상세/삭제
* BOOK History 보기
* 기타 웹푸시기능 추가
  (jsp페이지 주석처리 -> 로컬에서시에는 상관없지만 
* Redis Cache기능 추가(19.12.27)<br/><br/>

## Execute

* 소스받아서 IDE에서 실행하는 방법

1. build.gradle 마우스 오른쪽 > Gradle > Refresh Gradle Project 실행
2. IDEA툴 > Window > Show View > Other > Gradle > Gradle Tasks 실행
   book-manager 프로젝트 선택 > build > build 실행
   /book-manager/src/main/java/com/enliple/book/BookManagerActor.java 파일 실행
 
* 빌드된 WAR파일을 CMD에서 바로 실행시키는 방법

1. Gradle Build 후 생성된 아래경로 파일 실행시킬 특정 경로에 복사후 해당 위치로 이동
   /book-manager/build/libs/book-manager-0.0.1-SNAPSHOT.war
2. 명령어 실행
   > java -jar book-manager-0.0.1-SNAPSHOT.war


## 실행 URL
http://localhost:8080/book

<img src="https://user-images.githubusercontent.com/37128830/70287978-34a71880-1814-11ea-92d2-b4936185e5e6.png"/>
