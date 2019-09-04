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
### 로깅 결과 샘플 
## 스카우터 시스템 모니터링 메트릭 샘플 ( scouter-counter-host.json)
```json
...
{
  "startTime": "20190904T110521.774+0900",
  "objName": "/sc-api-demo-s02.localdomain",
  "objHash": "x11uc3jm",
  "objType": "HOST-ScouterDemo",
  "objFamily": "host",
  "MemA": 342,
  "TcpStatEST": 21,
  "DiskReadBytes": 0,
  "DiskWriteBytes": 45056,
  "TcpStatTIM": 32,
  "UserCpu": 0.8020151,
  "NetRxBytes": 13170,
  "PageIn": 0,
  "NetOutBound": 18,
  "TcpStatFIN": 0,
  "SysCpu": 0.3,
  "NetTxBytes": 13847,
  "TcpStatCLS": 1,
  "MemU": 647,
  "Mem": 65.37895,
  "MemT": 990,
  "Cpu": 1.2020152,
  "PageOut": 0,
  "Swap": 0,
  "SwapU": 0,
  "SwapT": 0,
  "NetInBound": 37
}
...
```
## 스카우터 자바 모니터링 메트릭 샘플 ( scouter-counter-javaee.json)
```json
...
{
  "startTime": "20190904T110521.644+0900",
  "objName": "/sc-api-demo-s01.localdomain/another-web",
  "objHash": "x176ir0j",
  "objType": "ScouterAnotherDemoJava",
  "objFamily": "javaee",
  "ElapsedTime": 725,
  "ServiceCount": 217,
  "Elapsed90%": 1874,
  "RecentUser": 5,
  "ApiTime": 496,
  "ErrorRate": 6.8627453,
  "QueuingTime": 0,
  "GcCount": 0,
  "SqlErrorRate": 1.6666666,
  "GcTime": 0,
  "PermUsed": 63.24852,
  "SqlTime": 6,
  "ApiErrorRate": 2.631579,
  "SqlTPS": 6,
  "ApiTimeByService": 191,
  "HeapTotal": 111.69531,
  "HeapUsed": 69.89433,
  "TPS": 3.4,
  "ActiveService": 3,
  "ApiTPS": 1.2666667,
  "SqlTimeByService": 11
}
...
```
## 스카우터 XLOG 메트릭 샘플( scouter-counter-xlog.json)
```json
...
{
  "objName": "/sc-api-demo-m01.localdomain/sampleweb",
  "objHash": "z135cg1n",
  "objType": "tracing",
  "objFamily": "javaee",
  "startTime": "20190904T110521.798+0900",
  "endTime": "20190904T110521.800+0900",
  "startTimeEpoch": 1567562721798,
  "endTimeEpoch": 1567562721800,
  "serviceName": "/error",
  "threadName": "http-nio-8080-exec-9",
  "gxId": "0",
  "txId": "z1s3e3prvmld37",
  "caller": "0",
  "elapsed": 2,
  "error": 0,
  "cpu": 0,
  "sqlCount": 0,
  "sqlTime": 0,
  "ipAddr": "127.0.0.1",
  "allocMemory": 53,
  "userAgent": "unirest-java/1.3.11",
  "referrer": "",
  "group": "/**",
  "apiCallCount": 0,
  "apiCallTime": 0
}
...
```
