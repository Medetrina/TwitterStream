import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;


public class TwitterStreamConsumer extends Thread 
{
    private static final String STREAM_URI = "https://stream.twitter.com/1.1/statuses/filter.json";
    
    public void run(){
	try{
        System.out.println("Starting Twitter public stream consumer thread.");

        // Enter your consumer key and secret below
        OAuthService service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey("d9r84GHSbp4uadLK24sTAwiPq")
                .apiSecret("udP5YZob4pqpAc0hIjoOBPT7KJFsi9UUwyVwgZ1GZnN7U7LYDj")
                .build();

        // Set your access token
        Token accessToken = new Token("18487350-tM8ZZjdFxuDeRIchbK2xE17zoqA2aH7440mUbdK8Q", "b5K274gwO1eOwOUQpjFVHRcjW45ap1nXUVY3EHiy5ZGbo");

        // Let's generate the request
        System.out.println("Connecting to Twitter Public Stream");
        OAuthRequest request = new OAuthRequest(Verb.POST, STREAM_URI);
        request.addHeader("version", "HTTP/1.1");
        request.addHeader("host", "stream.twitter.com");
        request.setConnectionKeepAlive(true);
        request.addHeader("user-agent", "Twitter Stream Reader");
        request.addBodyParameter("track", "earthquake,fire,wildfire"); // Set keywords you'd like to track here
        service.signRequest(accessToken, request);
        Response response = request.send();

        // Create a reader to read Twitter's stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    catch (IOException ioe){
        ioe.printStackTrace();
    }
}
}

