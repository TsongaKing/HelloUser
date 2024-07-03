package hellouser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public MyApiKeys apiKeys() {
    return new MyApiKeys();
  }
}

@ConfigurationProperties("my.api.keys")
class MyApiKeys {
  private String ipGeo;
  private String weather;

  public String getIpGeo() {
    return ipGeo;
  }

  public void setIpGeo(String ipGeo) {
    this.ipGeo = ipGeo;
  }

  public String getWeather() {
    return weather;
  }

  public void setWeather(String weather) {
    this.weather = weather;
  }


@RestController
public class HelloController {
  private final String IP_GEO_URL = "https://api.ipgeolocation.io/ipgeo?apiKey=";
  private final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q={location}&APPID=";

  private final MyApiKeys apiKeys;
  private final RestTemplate restTemplate;

  public HelloController(MyApiKeys apiKeys, RestTemplate restTemplate) {
    this.apiKeys = apiKeys;
    this.restTemplate = restTemplate;
  }

  @GetMapping("/api/hello-user")
  public HelloResponse hello(
      @RequestParam("Phangi") String visitorName,
      HttpServletRequest request) {
    String clientIp = request.getRemoteAddr();

    // Fetch location
    IpInfo ipInfo = restTemplate.getForObject(IP_GEO_URL + apiKeys.getIpGeo(), IpInfo.class);
    String location = ipInfo.getCity();

    // Build weather API URL dynamically
    String weatherApiUrl = WEATHER_API_URL.replace("{location}", location).replace("{apiKey}", apiKeys.getWeather());

    // Fetch weather
    WeatherResponse weatherResponse = restTemplate.getForObject(weatherApiUrl, WeatherResponse.class);
    String temperature = weatherResponse.getMain().getTemp().toString();

    String greeting = "Hello, " + visitorName + "!, the temperature is " + temperature + " degrees Celsius in " + location;
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
}
