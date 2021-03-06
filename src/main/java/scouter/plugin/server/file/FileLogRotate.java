package scouter.plugin.server.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import scouter.server.Logger;
import scouter.util.StringUtil;

import java.io.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Getter
public class FileLogRotate {

    private final String name;
    private final String dir;
    private final String fileName;
    private final DateTimeFormatter dateformatter;
    private final String extension;
    private final ObjectMapper obejctMapper;
    private final boolean isJson;
    private final String moveDir;
    private long lastTime;
    PrintWriter dataFile;

    public FileLogRotate(String name,String extension, String dir,String moveDir){
        this.name= name;
        this.dir = dir;
        this.moveDir = moveDir;
        this.extension=extension;
        this.fileName = String.join(File.separator,dir,name+"."+this.extension);

        this.lastTime = System.currentTimeMillis();
        this.dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                              .withZone(ZoneId.systemDefault());
        this.obejctMapper = new ObjectMapper();
        this.obejctMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.isJson = Objects.equals("json",extension);
    }
    public boolean create() {
         File file = new File(fileName);
         File parentFile= file.getParentFile();
         try {
             if (!parentFile.exists() && !parentFile.isDirectory()) {
                 boolean mkdir = parentFile.mkdir();
                 Logger.println(parentFile.getAbsolutePath() + " create parent directory : " + mkdir);
             }
             dataFile = new PrintWriter(new FileWriter(file, true));
         }catch (IOException e){
             Logger.printStackTrace(e);
             return false;
         }
         return true;
    }

    protected void rotate()  {
        Calendar calendar= GregorianCalendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        final String rotateDate =dateformatter.format(calendar.getTime().toInstant());

        final String rotateName = String.join("",name,"_",rotateDate,".",this.extension);
        final File src = new File(this.fileName);
        final File dest = new File(String.join(File.separator,moveDir,rotateName));
        if (src.exists()) {
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdir();
            }
            final boolean isSuccess = src.renameTo(dest);
            if(isSuccess){
                //-기존파일 썬던 IO 종료
                this.dataFile.close();
                //-신규 Write
                this.create();
            }
        }
    }
    public void execute(Map<String,Object> data)  throws IOException{
        final String now  = dateformatter.format(new Date().toInstant());
        final String last = dateformatter.format(new Date(this.lastTime).toInstant());

        if(!Objects.equals(now,last)){
            this.rotate();
        }

        if(!isJson) {
            this.head(data.keySet());
            dataFile.println(new ArrayList<>(data.values()).stream().map(Object::toString).collect(Collectors.joining(",")));
            dataFile.flush();
        }else{
            Map<String,Object> rebuild = new LinkedHashMap<>();

            String name = data.remove("objName").toString();
            String[] objName = StringUtil.split(name,"/");

            rebuild.put("server_id",data.remove("server_id"));
            rebuild.put("startTime",data.get("startTime"));
            rebuild.put("objName",name);
            rebuild.put("objHash",data.remove("objHash"));
            rebuild.put("objType",data.remove("objType"));
            rebuild.put("objHost",objName[0]);
            if(objName.length > 1){
                rebuild.put("objId",objName[1]);
            }else{
                rebuild.put("objId",objName[0]);
            }
            // merge
            String objFamily= data.remove("objFamily").toString();
            rebuild.put("objFamily",objFamily);

            Map<String,Object> reData = new LinkedHashMap<>();
            for(Map.Entry<String,Object> get: data.entrySet()){
                reData.put(get.getKey(),get.getValue());
            }

            rebuild.put(objFamily ,reData);
            dataFile.println(this.obejctMapper.writeValueAsString(rebuild));
            dataFile.flush();
        }
        this.lastTime = System.currentTimeMillis();
    }

    private void head(Set<String> strings) {
        if( new File(this.fileName).length() == 0 ) {
            dataFile.println(strings.stream().collect(Collectors.joining(",")));
        }
    }

}
