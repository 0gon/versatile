application 속성 파일에서 값을 @Value로 읽는데, 이것을 yamlSnake로 직접 읽어서 처리하기.
장점 - 앱에서 사용되는 필수 속성값을 런타임중이 아닌 런타임 초기(부트스트랩)에 잡을 수 있다
-> @ConfigurationProperties + @Validated 로 스프링에서 지원함