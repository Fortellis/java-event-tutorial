import java.io.IOException;
import java.io.File;

public class FileWatchDemo {
    public static long LAST_TIME = 0L;
    public static void main(String[] args) throws IOException {
        String fileName = "C:/Users/sandbern/Documents/MavenTest/apache-maven-3.8.6/asynchronousAPI/src/main/resources/payload.json";

        while(true){
            long timestamp = readLastModified(fileName);
            if (timestamp != LAST_TIME) {
                System. out.println("file updated:" + timestamp);
                LAST_TIME = timestamp;
                //Reload, file contents
            }
        }
    }
   
   
    public static long readLastModified(String fileName) {
        File file = new File(fileName);
        return file.lastModified();
    }
}