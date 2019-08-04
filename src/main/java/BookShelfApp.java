import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;

public class BookShelfApp extends NanoHTTPD {


    public BookShelfApp(int port) throws IOException {
        super(port);
        start(5000,false);
        System.out.println("Server has been started.");
    }

    public static void main(String[] args){
        try{
            new BookShelfApp(8080);

        }catch (IOException e){
            System.err.println("Server cant started beacause of error: \n"+ e);
        }
    }

}
