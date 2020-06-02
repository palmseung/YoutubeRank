<h1 align="center">YouTubeRank
<br>
</h1>

<h4 align="center">YouTube 영상 검색 웹 어플리케이션, YouTubeRank 입니다.</h4>


## 🎈 서비스 소개
-  사용자가 YouTube 검색 키워드를 입력하면, 해당 키워드에 대해 조회수가 가장 높은 YouTube 영상 5개를 알려드립니다.
-   한 번 검색한 키워드는 나만의 키워드 리스트에 저장되므로, 이후에는 단 한 번의 클릭으로 YouTube 영상 순위를 확인할 수 있습니다.
-   나만의 키워드 리스트에 저장된 키워드는 언제든 삭제할 수 있습니다.
-  모든 서비스는 회원 가입 후 이용 가능합니다.

<br/>

## 🎈 개발 배경

#### YouTubeRank 는 Mnet 의 음악경연 프로그램 <컴백전쟁: 퀸덤>을 시청하던 도중 열🔥 받아서 구상한 프로젝트입니다.

- <컴백전쟁: 퀸덤>은 음악경연 프로그램으로서, 매주 가수들이 음악 무대를 선보인 후 순위를 선정합니다. 가수들이 선보인 무대에 대한 점수는 현장 관객 투표와 출연자 자체 투표로 산정됩니다. 
- 프로그램에서 발표한 경연 순위가 YouTube 영상의 조회수와 굉장히(!) 많이 엇나가면서, 특정 키워드에 대해 YouTube 영상들의 순위를 한 번에 보여주는 서비스가 필요하다는 생각을 했습니다. 

<br/>

## 🎈 기술 스택

#### Back-end는 다음의 기술로 구성됩니다.

- Java 8
- Spring boot 2.2.4.
- Spring Security / JWT 
- Spring Data JPA / PostgreSQL
- Spring RestDocs

#### Front-end는 다음의 기술로 구성됩니다.

- Mustache
- jQuery
- BootStrap

#### 다음의 도구를 사용하여 제작합니다.

- IntelliJ IDEA

<br/>

##  🎈 프로젝트 관리
#### Github의 이슈 관리 및 PR(Pull Request) 기능을 이용해 프로젝트를 관리하고 있습니다.

- 개발 인원 : 1명 
- 개발 기간 (ver.1) : 약 41일 (2020년 4월 25일 ~ 2020년 6월 2일)
- 프로젝트 진행 현황 : [클릭!](https://github.com/palmseung/YoutubeRank/projects)

<br/>


##  🎈 YouTubeRank with TDD
#### Back-end는 TDD를 지향합니다. YouTubeRank에서 수행된 테스트는 아래와 같습니다. 
- 인수테스트 (Acceptance Test) 
- 컨트롤러 테스트 (Controller Test)
- 서비스 테스트 (Service Test)
-  도메인 테스트 (Domain / Repository Test)
#### 테스트는 [TestContainer](https://www.testcontainers.org)를 이용하여 진행합니다.
- 운영과 동일한 형태의 개발 환경에서 테스트 하기 위해 TestContainers(PostgreSQL)를 이용하여 테스트를 진행합니다.
- TestContainers를 실행시키는 추상클래스(BaseContainerTest)를 생성한 후, 각각의 테스트 클래스가 BaseContainerTest를 상속받도록 구현하였습니다.
#### Acceptance Test는 아래와 같이 진행합니다.
- WebTestClient를 사용하였습니다. 
- 실제 Servlet을 띄워 실제 운영 환경과 유사한 상황에서 테스트를 진행하였습니다.
- 인수테스트 단계에서는 'API를 호출하는 클라이언트 측의 요구사항을 충족하는가'에 중점을 두고 테스트를 진행하였습니다.
#### Repository Test는 아래와 같은 환경에서 진행하였습니다.
- TestContainers와 @DataJpaTest가 호환되지 않아 @SpringBootTest 어노테이션을 사용하여 진행하였습니다.
- BaseContainerTest를 상속받지 않고, Repository 테스트 클래스 내부에 TestConatiners를 실행할 수 있도록 별도로 구현하였습니다.
#### 마지막 커밋 기준으로 Test Coverage는 다음과 같습니다.
- Test Coverage : 93% classes, 86% lines covered

<br/>

##  🎈 YouTubeRank with Spring Security & JWT
#### 인증과 인가는 Spring Security와 JWT(Json Web Token)을 이용해 아래와 같이 진행됩니다.
1. 사용자가 로그인을 요청하면, 서버는 사용자의 이메일 정보를 기반으로 한 AccessToken을 발급하여 로그인 응답 본문에 실어 보냅니다.
2. 클라이언트 단에서 발급된 AccessToken을 브라우저의 LocalStorage에 저장합니다.  
3. 이후 사용자가 서버에 요청할 때마다 LocalStorage에 저장된 AccessToken을 가져와 요청 헤더에 포함하여 전달합니다.
4. AccessToken은 맵핑된 컨트롤러로 전달되기 전 JwtAuthenticationFilter를 통해 유효성을 검증받습니다. 
5. AccessToken이 유효하다면, 해당 토큰에서 사용자 정보를 추출하여 SecurityContextHolder에 해당 사용자 정보를 주입합니다.
6. 이후 UserNamePasswordAuthenticationFilter을 거치면서 사용자 정보를 확인해 인증 여부를 결정합니다.
7. 맵핑된 컨트롤러로 요청이 전달되어 서버가 요청을 처리합니다.
#### 관리자 계정은 지정된 이메일과 비밀번호로 회원가입을 요청할 경우에만 아래의 절차를 거쳐 가입할 수 있습니다.
1. 관리자 회원가입 요청(이름/이메일/비밀번호)
2. admin.properties에 담긴 이메일 주소와 비밀번호 정보를 가져옵니다. 
3. 회원가입 요청 정보와 admin.properites에 지정된 정보가 일치하면 관리자 회원가입 요청을 처리합니다.

<br/>

##  🎈 YouTubeRank with JPA
#### 회원 관리는 Member 엔티티에서 담당합니다. 
- [엔티티 코드](https://github.com/palmseung/YoutubeRank/blob/master/src/main/java/com/palmseung/modules/members/domain/Member.java)
- Member 엔티티는 Spring Security에서 인증 및 인가처리를 위해 UserDetails 인터페이스를 구현(implement)합니다.
- Member 엔티티는 회원가입 일자와 최근 정보 수정 일자를 기록하기 위해 BaseTimeEntity를 상속(extends)받습니다. 
#### 검색 키워드는 Keyword 엔티티에서 담당합니다.
- [엔티티 코드](https://github.com/palmseung/YoutubeRank/blob/master/src/main/java/com/palmseung/modules/keywords/domain/Keyword.java)
- 향후 키워드 데이터를 활용하기 위해 엔티티로 설정하였습니다.

<br/>


#### 나의 검색 키워드는 MyKeyword 엔티티에서 별도로 관리합니다.
- [엔티티 코드](https://github.com/palmseung/YoutubeRank/blob/master/src/main/java/com/palmseung/modules/keywords/domain/MyKeyword.java)
- Member 엔티티와 Keyword 엔티티가 ManyToMany 관계가 되는 것을 피하기 위해 MyKeyword 엔티티를 생성하였습니다.
- Member 엔티티와 MyKeyword 엔티티는 OneToMany 관계입니다. (Member가 One, MyKeyword가 Many)
- MyKeyword 엔티티와 Keyword 엔티티는 OneToMany 관계입니다. (MyKeyword가 Many, Keyword가 One)

#### 만약 인증된 사용자가 '퀸덤' 이라는 키워드로 검색을 한다면, 아래와 같은 프로세스를 거치게 됩니다.
1. 인증된 사용자가 '퀸덤'이라는 키워드 입력합니다.
2. 데이터베이스의 keyword 테이블에 저장된 키워드가 아니라면, Keyword 객체를 생성합니다 (데이터베이스의 keyword 테이블에 저장된 키워드라면, 저장된 Keyword 정보를 불러옵니다).
3. Member 정보와 Keyword 정보를 바탕으로, MyKeyword 객체를 생성합니다.

#### Controller와 View 레이어에서 준영속 상태가 되는 엔티티의 데이터를 확보하기 위해 별도의 QueryService 를 구현하고 있습니다. 
- 기존의 Service 클래스 외에 QueryService 클래스를 별도로 생성하였습니다.
- 이를 통해, OSIV(Open Session In View) 설정을 false로 설정하더라도 Lazy Loading으로 설정된 데이터를 Controllerd와 View에서도 사용 가능하도록 구성하였습니다.
- [MemberQueryService 코드](https://github.com/palmseung/YoutubeRank/blob/master/src/main/java/com/palmseung/modules/members/service/MemberService.java)
- [KeywordQueryService 코드](https://github.com/palmseung/YoutubeRank/blob/master/src/main/java/com/palmseung/modules/keywords/service/KeywordQueryService.java)


<br/>


##  🎈 YouTubeRank as a Rest API 
#### YouTubeRank의 API는 Stateless를 지향합니다.
- YouTubeRank는 JWT을 이용함으로써,  서버가 Client의 status를 따로 저장하지 않도록 구현하였습니다. 
- YouTubeRank의 서버는 클라이언트측에서 들어오는 요청만으로만 작업을 처리합니다. 
- Stateless한 서버는 클라이언트와의 연결고리가 없기 때문에 확장성 (Scalability) 이 높아집니다.

#### YouTubeRank의 API는 Self-descriptive와 HATEOAS를 지향합니다.
- API의 JSON 응답 안에 키와 밸류가 어떤 의미를 가지는지 해석할 수 있는 API 문서(Profile)를 첨부함으로써 Self-Descriptive 조건을 충족합니다. 
- 각 어플리케이션의 상태도 하이퍼링크로 함께 전달함으로써 HATEOAS를 지향하도록 API를 구현하였습니다. 
- YouTubeRank의 회원가입 요청에 대한 API 응답 예시  

> <img src="https://github.com/palmseung/YoutubeRank/blob/master/src/main/resources/static/img/response-example.png" width="450"/>

#### YouTubeRank는 Spring RestDocs를 이용하여 API를 문서화 하고 있습니다.
- API Documentation : [YouTubeRank의 API 문서 살펴보러 가기](https://github.com/palmseung/YoutubeRank/blob/master/src/docs/asciidoc/api-guide.pdf)

<br/>


##  🎈 YouTubeRank with YouTube Data API
#### 특정 키워드에 대한 YouTube 영상 데이터를 불러오기 위해 YouTube Data API 를 이용합니다. 이 과정은 아래와 같은 프로세스로 진행됩니다.
1. youtube.properties 파일에서 Google API Key 값을 불러옵니다.
2. YouTube Data API 를 통해 특정 키워드에 대해 조회수가 가장 높은 비디오 5개에 대한 데이터를 응답받습니다. 

#### YouTubeRank에서 YouTube API를 통해 불러온 데이터는 아래와 같은 규칙을 가진 일급컬렉션(YouTubeVideos) 형태로 사용됩니다.
- 각각의 영상에 대한 정보(videoId, title, thumbnail URL, description)는 YouTubeVideo (DTO, [코드](https://github.com/palmseung/YoutubeRank/blob/master/src/main/java/com/palmseung/modules/youtube/domain/YouTubeVideo.java))를 통해 전달됩니다.
- 5개의 YouTubeVideo가 모여 YouTubeVideos(일급 컬렉션, [코드](https://github.com/palmseung/YoutubeRank/blob/master/src/main/java/com/palmseung/modules/youtube/domain/YouTubeVideos.java))을 이루게 됩니다.
- YouTubeVidoes 에서는 YouTubeVideo의 사이즈가 5인지를 확인한 후, 각각의 조회수가 내림차순으로 되어있는지를 검증합니다.

<br/>

<br/>

