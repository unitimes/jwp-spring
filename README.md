### 2. 서버가 시작하는 시점에 부모 ApplicationContext와 자식 ApplicationContext가 초기화되는 과정에 대해 구체적으로 설명해라.
* web.xml에 등록한 listener에 의해 applicationContext.xml이 초기화 된다. 이 과정에서 applicationContext.xml에 등록한 bean들이 초기화 된다.
* 이후 등록한 servlet의 load-on-startup 설정에 의해서 최초 servlet 요청 시 혹은 listener 의 실행이 종료 된 후 servlet이 초기화 된다. 이때 next servlet과 연관된 next-servlet.xml이 초기화 된다. 마찬가지로 등록한 bean이 초기화 된다.



### 3. 서버 시작 후 http://localhost:8080으로 접근해서 질문 목록이 보이기까지 흐름에 대해 최대한 구체적으로 설명하라. 
* web.xml의 servlet mapping 설정에 의해 모든 요청은 next servlet이 처리하게 된다.
* QuestionController에 ""이 mapping 되어 있기 때문에 list 메소드가 실행되고 viewResolver 설정에 의해 qna/list.jsp 가 전달 된다.


### 9. UserService와 QnaService 중 multi thread에서 문제가 발생할 가능성이 있는 소스는 무엇이며, 그 이유는 무엇인가?
* UserService는 single thread로 작동한다. spring의 기본 scope이 single thread이기 때문이다. 따라서 전역 변수의 값을 변화시키는 메서드가 존재할 경우 thread safe하지 못하다. 따라서 지역 변수화 하든지, scopr을 변화시켜야 한다.