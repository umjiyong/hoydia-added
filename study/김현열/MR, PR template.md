

![](C:/Users/HYKdev/Desktop/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-18%20155929.png)

# MR, PR template

## 권장사항

- 무슨 이유로 코드를 변경했는지
- 어떤 위험이나 장애가 발견되었는지
- 어떤 부분에 리뷰어가 집중하면 좋을지
- 관련 스크린샷
- 테스트 계획 또는 완료 사항

Merge Request 페이지에는 코드에 대한 변경사항과 그 변경으로 발생한 CI/CD 파이프라인 정보 그리고 리뷰에 참여한 사람들의 커멘트 스레드를 이용하여 리뷰가 진행됩니다.

![](C:/Users/HYKdev/Desktop/img.gif)



```
what is this PR?

changes

screenshot

test checklist
```



![](C:/Users/HYKdev/Desktop/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202022-07-18%20163624.png)



```
Issue: feature

Description

Todo

ETC
```





```null
# Feature Request
- Part
  - [ ] FE
  - [ ] BE
  - [ ] Data Preprocessiong
- 기능 상세 설명
  - 기능에서 어떤 부분이 구현되어야 하는 지 설명해주세요
  
  ## 어떤 이유로 MR를 하셨나요?
- [ ] feature 병합
- [ ] 버그 수정(아래에 issue #를 남겨주세요)
- [ ] 코드 개선
- [ ] 기타(아래에 자세한 내용 기입해주세요)

## 스크린샷 및 세부 내용 - 왜 해당 MR이 필요한지 자세하게 설명해주세요
- 세부사항을 항목으로 설명해주세요

## MR하기 전에 확인해주세요
- [ ] local code lint 검사를 진행하셨나요?
- [ ] loca ci test를 진행하셨나요 ?

## relavant issue number
- 관련된 이슈 넘버가 있으면 이곳에 기입해주세요
```