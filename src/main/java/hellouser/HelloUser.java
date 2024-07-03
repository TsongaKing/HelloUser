package hellouser;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloUser {
    @GetMapping("/api/hello-user")
    public HelloResponse hello(
            @RequestParam("Phangi") String visitorName,
            HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        String location = "New York"; 
        String greeting = "Hello, " + visitorName + "!, the temperature is 11 degrees Celsius in " + location;
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

}



    

