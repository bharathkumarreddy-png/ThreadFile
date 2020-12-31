package SampleProgram;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class StudentStoreAndRead {
    FileReader fr = null;
    public void createToStore(StudentReal studentStore, String filePath) throws IOException {
        Properties p = new Properties();
        if(isKeyExists(studentStore.name)) {
            System.out.println("Key already exists");
        } else {


            p.setProperty("name", studentStore.name);
            ObjectMapper mapper = new ObjectMapper();

            p.setProperty("details", mapper.writeValueAsString(studentStore.details));

            FileWriter fw = null;
            if (filePath != null) {
                fw = new FileWriter(filePath);
            } else {
                fw = new FileWriter("Store.txt");
            }

            p.store(fw, "Stored student");
            fw.close();
        }
    }

    public StudentReal readFromStore() throws IOException {
        fr = new FileReader("Store.txt");
        Properties p = new Properties();
        p.load(fr);
        fr.close();

        StudentReal studentReal = new StudentReal();
        studentReal.name = p.getProperty("name");
        ObjectMapper mapper = new ObjectMapper();
        StudentPojo studentpojo = mapper.readValue(p.getProperty("details"), StudentPojo.class);
        studentReal.details = studentpojo;
        return studentReal;
    }

    public boolean isKeyExists(String name) throws IOException {
         fr = new FileReader("Store.txt");
        Properties p = new Properties();
        p.load(fr);
        fr.close();

        StudentReal studentReal = new StudentReal();
        studentReal.name = p.getProperty("name");
       return name.equals(studentReal.name);
    }


    public static void main(String[] args) throws IOException {
        StudentPojo studentPojoSrinivas = new StudentPojo("01","5.8","65");

        StudentReal srinivas = new StudentReal();
        srinivas.name = "Srinivas";
        srinivas.details = studentPojoSrinivas;
        String filePath = "";



        StudentStoreAndRead studentStoreAndRead = new StudentStoreAndRead();
        studentStoreAndRead.createToStore(srinivas,filePath);
        System.out.println(studentStoreAndRead.readFromStore().name);
        System.out.println(studentStoreAndRead.readFromStore().details.height);
        System.out.println(studentStoreAndRead.readFromStore().details.weight);

    }
}
