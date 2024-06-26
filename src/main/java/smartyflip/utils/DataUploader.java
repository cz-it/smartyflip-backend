package smartyflip.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import smartyflip.accounting.dto.UserRegistrationRequestDto;
import smartyflip.accounting.service.UserAccountService;
import smartyflip.card.dto.NewCardDto;
import smartyflip.card.service.CardService;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.service.ModuleService;
import smartyflip.stacks.dto.NewStackRequestDto;
import smartyflip.stacks.service.StackService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(20)
public class DataUploader implements CommandLineRunner {

    private static final String DEMO_MODULES = "classpath:data/modules.json";

    private static final String DEMO_USERS = "classpath:data/users.json";

    private static final String DEMO_STACKS = "classpath:data/stacks.txt";

    private static final String DEMO_CARDS = "classpath:data/cards.json";

    private final ResourceLoader resourceLoader;

    private final UserAccountService userAccountService;

    private final StackService stackService;

    private final ModuleService moduleService;

    private final CardService cardService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;


    private List<UserRegistrationRequestDto> loadUsersFromJson() throws IOException {
        Resource resource = resourceLoader.getResource(DEMO_USERS);
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        }
    }

    private List<NewModuleDto> loadModulesFromJson() throws IOException {
        Resource resource = resourceLoader.getResource(DEMO_MODULES);
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        }
    }

    private List<NewCardDto> loadCardsFromJson() throws IOException {
        Resource resource = resourceLoader.getResource(DEMO_CARDS);
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        }
    }

    private List<NewCardDto> loadCardsFromJson(String path) throws IOException {
        Resource resource = resourceLoader.getResource(path);
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        }
    }

    public void createStacks() throws IOException {
        Resource resource = resourceLoader.getResource(DEMO_STACKS);

        try (InputStream inputStream = resource.getInputStream();

             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                NewStackRequestDto stack = new NewStackRequestDto(line);
                stackService.addStack(stack);
            }
        }
    }

    private void createUsers() throws IOException {
        List<UserRegistrationRequestDto> users = loadUsersFromJson();
        try {
            for (UserRegistrationRequestDto user : users) {

                userAccountService.registerUser(user);

            }
            userAccountService.changeRoleList(1, "ADMINISTRATOR", true);
        } catch (Exception e) {
            // Nothing to do
        }
    }

    private void createModules() throws IOException {
        List<NewModuleDto> modules = loadModulesFromJson();
        for (NewModuleDto module : modules) {
            try {
                moduleService.addModule(module);
            } catch (Exception e) {
                // Nothing to do
            }
        }
    }

    private void createCards() throws IOException {
        List<NewCardDto> cards = loadCardsFromJson(DEMO_CARDS);
        for (NewCardDto newCardDto : cards) {
            try {
                cardService.addCard(newCardDto);
            } catch (Exception e) {
                // Nothing to do
            }
        }
    }


    @Override
    public void run(String... args) throws Exception {
        if ("create".equals(ddlAuto)) {
            try {
                createUsers();
                createStacks();
                createModules();
                createCards();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
