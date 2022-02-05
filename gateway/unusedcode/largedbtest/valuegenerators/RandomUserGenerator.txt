package es.jaime.gateway._shared.infrastrocture.test.largedbtest.valuegenerators;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RandomUserGenerator {
    private final List<String> users;
    private final ApplicationConfiguration configuration;
    private final int totalUsers;

    public RandomUserGenerator(ApplicationConfiguration configuration){
        this.configuration = configuration;
        this.totalUsers = configuration.getInt("NUMBER_OF_USERS");
        this.users = generateRandomList(configuration.getInt("NUMBER_OF_USERS"));
    }

    private List<String> generateRandomList(int limit){
        if(users != null && this.users.size() > 0) return this.users;

        List<String> users = new ArrayList<>();
        users.add("porro");
        for (int i = 0; i < (limit - 1); i++) {
            users.add(String.valueOf(i + 1));
        }

        return users;
    }

    public String get(){
        return this.users.get((int) (Math.random() * totalUsers));
    }
}
