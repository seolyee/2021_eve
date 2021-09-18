[2021_06_23] README push
android studio에서 리드미 파일 생성

[2021_06_23] just menu name change
menu_bottom.xml
    bottom_navigation의 item 이름 수정

[2021_06_23] Add README.md
gitLab에서 리드미 파일 생성

[2021_06_23] 8 button all switch screens
8 button all switch screens의 파일에 추가한 파일 :
    +R.layout.activity_daily_stamp.xml
    +R.layout.activity_event_stamp.xml
    +R.layout.activity_plan_choose.xml
    +R.layout.activity_community.xml
    +R.layout.activity_inbody_check.xml
    +R.layout.activity_walking.xml
    +R.layout.activity_ranking.xml
    +R.layout.activity_record.xml
까지 총 8개의 액티비티 파일을 만들었음(홈에 있는 8개 버튼)

8 button java 추가 파일 :
    +com.example.front_end_home_test.daily_stamp.java
    +com.example.front_end_home_test.event_stamp.java
    +com.example.front_end_home_test.plan_choose.java
    +com.example.front_end_home_test.community.java
    +com.example.front_end_home_test.inbody_check.java
    +com.example.front_end_home_test.walking.java
    +com.example.front_end_home_test.ranking.java
    +com.example.front_end_home_record.java
까지 MainActivity.java를 제외한 8개의 자바파일을 만듦(홈에 있는 8개 버튼)

AndroidManifest.xml 수정
    <activity android:name=".record" />
    <activity android:name=".walking" />
    <activity android:name=".ranking" />
    <activity android:name=".inbody_check" />
    <activity android:name=".community" />
    <activity android:name=".event_stamp" />
    <activity android:name=".plan_choose" />
    <activity android:name=".daily_stamp" />
코드 추가로 activity 파일 추가 설정