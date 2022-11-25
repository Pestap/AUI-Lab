package eti.pg.lab.pilot.event.repository;


import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.event.dto.CreatePilotRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class PilotEventRepository {
    private RestTemplate restTemplate;

    @Autowired
    public PilotEventRepository(@Value("${lab.licenses.url}") String baseURL){
        restTemplate = new RestTemplateBuilder().rootUri(baseURL).build();
    }

    public void delete(Pilot pilot){
        restTemplate.delete("/pilots/{id}", pilot.getId());
    }

    public void create(Pilot pilot){
        restTemplate.postForLocation("/pilots", CreatePilotRequest.entityToDtoMapper().apply(pilot));
    }

}
