package hellouser;



import hellouser.MyApiKeys.IpInfo;
import hellouser.MyApiKeys.WeatherResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    private static final String IP_INFO_URL = "http://ipinfo.io/{ip}/json";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=New%20York,us&APPID=75a8dfa1c63a0090b854207cf11fbe42";

    private final String apiKey = "75a8dfa1c63a0090b854207cf11fbe42";

    @GetMapping("/api/hello-controller")
    public HelloResponse hello(
            @RequestParam("Phangi") String visitorName,
            HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        RestTemplate restTemplate = new RestTemplate();

        // Fetch location
        IpInfo ipInfo = restTemplate.getForObject(IP_INFO_URL, IpInfo.class, clientIp);
        String location = ipInfo.getCity();

        // Fetch weather
        WeatherResponse weatherResponse = restTemplate.getForObject(WEATHER_API_URL, WeatherResponse.class, location, apiKey);
        String temperature = weatherResponse.getMain().getTemp().toString();

        String greeting = "Hello, " + visitorName + "!, the temperature is " + temperature + " degrees Celsius in " + location;
        return new HelloResponse(clientIp, location, greeting);
    }
}

    

