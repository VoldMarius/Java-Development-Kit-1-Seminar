package HomeWork;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileMassage {

    public boolean save(Serializable serializable, String path){
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(path, true))){
            objectOutputStream.writeObject(serializable);
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public List<String> load(String path)throws IOException{
        List<String> history;
        try{
            BufferedReader reader = new BufferedReader((new FileReader(path)));
            history = reader.lines().collect(Collectors.toList());
        }catch(IOException e){
            throw e;
        }
        return history;
    }


}