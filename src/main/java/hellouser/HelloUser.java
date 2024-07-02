package hellouser;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloUser {
    @GetMapping("/api/hello-user")
    public HelloResponse hello(
            @RequestParam("Mark") String Mark,
            HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        String location = "New York"; // Simplified for this example
        String greeting = "Hello, " + Mark + "!, the temperature is 11 degrees Celsius in " + location;
        return new HelloResponse(clientIp, location, greeting);
    }
}

class HelloResponse {
    private String clientIp;
    private String location;
    private String greeting;

    public HelloResponse(String clientIp, String location, String greeting) {
        this.clientIp = clientIp;
        this.location = location;
        this.greeting = greeting;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getLocation() {
        return location;
    }

    public String getGreeting() {
        return greeting;
    }
}



    

