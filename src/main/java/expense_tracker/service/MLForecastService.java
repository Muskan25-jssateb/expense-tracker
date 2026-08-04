package expense_tracker.service;

import expense_tracker.dto.MLForecastRequest;
import expense_tracker.dto.MLForecastResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLForecastService {

    private final RestTemplate restTemplate;

    public MLForecastService() {
        this.restTemplate = new RestTemplate();
    }

    public MLForecastResponse getPrediction(
            MLForecastRequest request
    ) {

        String url = "http://localhost:8000/predict";

        return restTemplate.postForObject(
                url,
                request,
                MLForecastResponse.class
        );
    }
}