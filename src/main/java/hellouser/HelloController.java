package hellouser;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phang
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    private static final String IP_INFO_URL = "http://ipinfo.io/{ip}/json";
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";

    private final String apiKey = "75a8dfa1c63a0090b854207cf11fbe42";

    @GetMapping("/api/hello-controller")
    public HelloResponse hello(
            @RequestParam("Mark") String Mark,
            HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        RestTemplate restTemplate = new RestTemplate();

        // Fetch location
        IpInfo ipInfo = restTemplate.getForObject(IP_INFO_URL, IpInfo.class, clientIp);
        String location = ipInfo.getCity();

        // Fetch weather
        WeatherResponse weatherResponse = restTemplate.getForObject(WEATHER_API_URL, WeatherResponse.class, location, apiKey);
        String temperature = weatherResponse.getMain().getTemp().toString();

        String greeting = "Hello, " + Mark + "!, the temperature is " + temperature + " degrees Celsius in " + location;
        return new HelloResponse(clientIp, location, greeting);
    }
}

class IpInfo {
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

class WeatherResponse {
    private Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    class Main {
        private Double temp;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }
    }
}

    

