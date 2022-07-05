import javax.servlet.http.HttpServlet;

import java.io.File;

import java.io.FileNotFoundException;  
import java.util.Scanner; 


import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class AsynchronousAPI extends HttpServlet{
    public static long LAST_TIME = 0L;

    public static String data ="";

    public AsynchronousAPI() throws UnsupportedEncodingException{
        ClassLoader classLoader = getClass().getClassLoader();
        File  wholeFile =new File (classLoader.getResource("payload.json").getFile());
        
        String fileName = wholeFile.toString();

        while(true){
            long timestamp = readLastModified(fileName);
            if (timestamp != LAST_TIME) {
                System.out.println("file updated:" + timestamp);

                //Read file:
                try{
                    Scanner readFile = new Scanner(wholeFile);
                    while(readFile.hasNextLine()){
                        data = data + readFile.nextLine();
                        //System.out.println(data);
                        

                        
                        
                        

                    }
                    readFile.close();

                    System.out.println(data);

                    HttpClient httpclient = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost("http://webhook.site/39c36db6-a973-41eb-adaa-bb1440512281");

                                    

                    List<NameValuePair> params = new ArrayList<NameValuePair>(3);
                    params.add(new BasicNameValuePair("action", "count"));
                    params.add(new BasicNameValuePair("fields", "Status"));
                    params.add(new BasicNameValuePair("filters", ""));
                    httpPost.setEntity(new StringEntity(data, "UTF-8"));
                    try{
                        HttpResponse response = httpclient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            InputStream instream = entity.getContent();
                            System.out.println(instream);

                        }

                    }catch(IOException e){
                        e.printStackTrace();
                    }
                        
                }catch(FileNotFoundException e){
                    System.out.println("An error occurred");
                    e.printStackTrace();
                }

                LAST_TIME = timestamp;
                
            }
        }
    }
   
    public static long readLastModified(String fileName) {
        File file = new File(fileName);
        return file.lastModified();
    }
}
