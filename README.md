# scouter-plugin-server-metriclog

                                              
![Korean](https://img.shields.io/badge/language-Korean-blue.svg)
- Scouter Server Plugin으로 성능 counter 정보 와 XLOG 정보를 파일 형태로 남기는 plugin 이다.  
![Korean](./assert/filelogging-pipeline.png)     

### configuration (스카우터 서버 설치 경로 하위의 conf/scouter.conf)
#### 기본 설정
* **ext_plugin_fl_enabled** : 본 plugin 사용 여부 (default : true)
* **ext_plugin_fl_counter_index** : Counter 로깅 파일 index의 Prefix 명 (default : scouter-counter)
* **ext_plugin_fl_xlog_index** :  XLog 로깅 파일 index의 Prefix 명 (default : scouter-xlog)
* **ext_plugin_fl_counter_duration_day** : Counter index 저장 기간 (default : 3) ,3일
* **ext_plugin_fl_xlog_duration_day** : XLog index 저장 기간 (default : 3) , 3일
* **ext_plugin_fl_root_dir** : Log를 저장할 Root 디렉토리명 (default : 스카우터 설치 홈/ext_plugin_filelog)
* **ext_plugin_fl_rotate_dir** : Log를 Rotate 할 디렉토리명 (default : 스카우터 설치 홈/ext_plugin_filelog/rotate)
* **ext_plugin_fl_extension** : Log 파일 확장자 명(default : json ,저장 내용은 라인피드 json 포맷으로 저장된다, 그외의 포맷은 CSV 형태로 저장함 )


### dependencies
Refer to [pom.xml](./pom.xml)

### Build environment 
 - Java 1.8.x 이상 
 - Maven 3.x 이상 

### Build
 - mvn clean install
    
### Manual Deploy
 - target에 생성되는 scouter-plugin-server-metriclog-x.x.x.jar 와 target/lib에 생성되는 전체 library를 scouter sever의 lib 디렉토리에 저장하고 scouter server를 재시작한다
### Deploy
 - Release 페이지에서 아래 두개의 파일을 선택하여 다운로드 한다. 
    - dep-lib.tar.gz 
    - scouter-plugin-server-metriclog-1.0.0.jar       
 1. tar xvf dep-lib.tar.gz 파일을 압축을 푼후 모든 (*.jar) 파일을  scouter sever의 lib 디렉토리에 저장 한다. 
 2. scouter-plugin-server-metriclog-1.0.0.jar scouter sever의 lib 디렉토리에 저장 한다. 
 3. 그리고 scouter server를 재시작한다. 
### Metric Define 
 - [메트릭 로깅 데이터 정의 설명서](https://docs.google.com/spreadsheets/d/1tNjMa-wgqn1QglF5QtNvPR5t4P1ovojLnjE25TixRl0/edit?usp=sharing) 
### Support Scouter Version
 - 2.0.x 이상  
