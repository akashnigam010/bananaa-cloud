package in.socyal.sc.helper.notification;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationsService {

    private static final String FIREBASE_SERVER_KEY = "AAAAa7LORrA:APA91bGjdxa3bBGMQh1Ubt8-hX14w6U1kOnBkur_PnJT7taFrV2Uc85GnYIhVnuOo1Uqk7yVYg-KQ6NyeDxxgFzvs_KeoY9TbrFTE6LTgEZiKjBgwSXJ7V7gdYfx_FXncMtwZobAl7r6";

    @Async
    public CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity, FirebaseResponse.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

}
