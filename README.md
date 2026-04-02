# 🚀 2단계 - 칸반 보드 관리(수정)

## ui 테스트 목록

```gherkin
Scenario: 태스크 수정 다이어로그에서 Todo 상태면서 담당자가 없음 상태에서 In Progress 상태로 수정하려는 상황
    Given Todo 상태가 선택되어 있다
    And  담당자가 지정되어 있지 않다
    When In Progress 상태를 클릭한다
    Then 첫 번째 담당자가 자동으로 선택되고 In Progress 상태로 바뀐다
```

```gherkin
Scenario: 태스크 수정 다이어로그에서 Todo 상태면서 담당자가 있는 상태에서 Done 상태로 수정하려는 상황
    Given Todo 상태가 선택되어 있다
    And 담당자도 지정되어 있다
    When Done 상태를 클릭한 후 수정 버튼을 클릭한다
    Then 태스크가 Done 상태로 바뀐다
```

```gherkin
Scenario: Todo 상태면서 담당자가 지정되지 않은 태스크카드를 In Progress 상태로 이동하려는 상황
    Given Todo 상태가 선택되어 있고 담당자가 없음 상태인 태스크
    When 해당 태스크를 In Progress 상태로 드래그앤드롭한다
    Then 태스크가 이동하지 않고 Todo 상태로 남아있다
```

```gherkin
Scenario: Todo 상태면서 담당자가 지정된 태스크카드를 In Progress 상태로 이동하려는 상황
    Given Todo 상태가 선택되어 있고 담당자가 존재하는 태스크
    When 해당 태스크를 In Progress 상태로 드래그앤드롭한다
    Then 태스크가 In Progress 상태로 변경된다
```

```gherkin
Scenario: Todo(or In Progress) 태스크카드를 삭제하려는 상황
    Given Todo(or In Progress) 상태의 태스크카드를 클릭한 상태
    When 삭제 버튼을 클릭한다
    Then 태스크 카드가 화면에 표시되지 않는다
```

```gherkin
Scenario: Done(or Review) 태스크카드를 삭제하려는 상황
    Given Done(or Review) 상태의 태스크카드를 클릭한 상태
    When 삭제 버튼을 클릭한다
    Then 태스크가 삭제되지 않고 화면에 표시된다
```

## 구현할 기능 목록

- [ ] 피그마에 맞게 디자인을 적용한다

### 태스크카드 수정/삭제 기능
- [x] 태스크카드를 클릭하면 수정 다이어로그가 표시된다.
- [ ] To Do 상태의 태스크만 담당자 없음 상태가 허용된다.
    - To Do 상태일 때 수정 다이어로그에 담당자에 없음 버튼이 표시된다.
    - In Progress, Review, Done 상태의 태스크는 담당자가 항상 존재해야한다.
- [ ] To Do 상태면서 담당자가 없을 경우 다른 상태로 수정 시 첫번째 담당자로 기본 선택된다.
- [x] To Do, In Progress 상태의 태스크만 삭제가 허용된다.
- [ ] Review, Done 상태에서 태스크의 삭제는 불가능하다.
    - 불가능을 알리는 스낵바를 표시한다 
- [x] 삭제 버튼을 클릭하면 해당하는 태스크가 삭제된다.
- [x] 삭제가 수행되면 스낵바가 표시된다.
- [x] 수정이 수행되면 스낵바가 표시된다.

### 태스크 상태 전이 규칙
- [ ] Review 상태가 추가된다
- [ ] To Do 상태에서는 In Progress로만 전이가 가능하다
- [ ] 담당자가 지정되지 않았다면 In Progress로 전이가 불가능하다
- [ ] In Progress 상태에서는 To Do와 Review로만 전이가 가능하다
    - 불가능을 알리는 스낵바를 표시한다
- [ ] Review 상태에서는 In Progress와 Done으로만 전이가 가능하다
- [ ] Done 상태에서는 To Do로만 전이가 가능하다
- [ ] 불가능한 상태 전이가 발생하면 해당 상태로 옮길 수 없다는 스낵바를 표시한다.

# 🚀 1단계 - 칸반 보드 관리(프로젝트)

## 기능 목록

_[피그마 시안](https://www.figma.com/design/3aBG3UfkTwmHM8BnPyahtT/8%EA%B8%B0-Android-%EB%A0%88%EB%B2%A81-%EB%AF%B8%EC%85%98-%EB%94%94%EC%9E%90%EC%9D%B8?node-id=23136-23&t=xCxwxtFTp8FpfgFV-1)
에 맞춰 UI를 구성한다._

### 사이드바

- [X] 칸반 보드 프로젝트 탭을 화면 좌측에 표시한다.
    - [x] 프로젝트 이름이 너무 길면 ellipsis로 표시한다.
- [x] 선택한 프로젝트에 대한 칸반 보드를 보여준다.

### 태스크 이동

- [x] 드래그 앤 드롭으로 태스크 상태를 변경한다.
- [x] 상태 변경 시 스낵바를 표시한다.

## 테스트

- [x] 사용자가 프로젝트 선택하면 해당하는 해당 프로젝트 화면으로 전환된다
    ```gherkin
    Scenario: 사용자가 프로젝트를 전환하려는 상황
        Given 현재 프로젝트가 A프로젝트
        When B 프로젝트를 선택한다.
        Then B 프로젝트에 대한 태스크 목록이 표시된다
    ```

- [x] 태스크박스가 아닌 곳에 드롭할 경우 상태가 바뀌지 않는다

    ```gherkin
    Scenario: 사용자가 태스크의 상태를 전환하려는 상황
        Given TODO 상태의 A 태스크가 있다
        When 사용자가 태스크를 사이드바로 드래그앤드롭한다
        Then TODO 상태 태스크 박스에 A태스크가 표시된다
    ```

- [x] 동일한 상태의 태스크박스에 드롭할 경우 상태가 바뀌지 않는다
    ```gherkin
    Scenario: 사용자가 태스크의 상태를 전환하려는 상황
        Given TODO 상태의 A 태스크가 있다
        When 사용자가 태스크를 TODO 테스크 박스로 드래그앤드롭한다
        Then TODO 상태 태스크 박스에 A태스크가 표시된다
    ```

- [x] 다른 상태의 태스크박스에 드롭할 경우 해당 상태로 변경한다
    ```gherkin
    Scenario: 사용자가 태스크의 상태를 전환하려는 상황
        Given TODO 상태의 A 태스크가 있다
        When 사용자가 태스크를 IN_PROGRESS 테스크 박스로 드래그앤드롭한다
        Then TODO 상태 태스크 박스에서 A태스크가 사라지고, IN_PROGRESS 상태 태스크 박스에 A태스크가 표시된다
    ```

## 프로그래밍 요구 사항

- 여러 번 그려지지 않아도 되는 뷰는 매번 리컴포지션 되지 않아야 한다.
- 적절한 테스트 방법을 활용하여 기능 요구 사항을 테스트한다.
- 모든 요구 사항이 테스트 가능하진 않다. 스스로 판단해서 구분한다.
- 프로젝트 생성을 위한 뷰는 없다. 가짜 데이터와 테스트 더블을 활용한다.
