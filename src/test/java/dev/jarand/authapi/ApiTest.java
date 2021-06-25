package dev.jarand.authapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jarand.authapi.grantedtype.repository.GrantedTypeRepository;
import dev.jarand.authapi.jaranduser.jarandclient.repository.JarandClientRepository;
import dev.jarand.authapi.jaranduser.repository.JarandUserRepository;
import dev.jarand.authapi.scope.repository.ScopeConnectionRepository;
import dev.jarand.authapi.scope.repository.ScopeRepository;
import dev.jarand.authapi.token.repository.TokenRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.flyway.enabled=false")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ContextConfiguration(classes = ApiTestConfig.class)
@AutoConfigureMockMvc
public class ApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JarandUserRepository jarandUserRepository;

    @MockBean
    protected JarandClientRepository jarandClientRepository;

    @MockBean
    protected TokenRepository tokenRepository;

    @MockBean
    protected ScopeRepository scopeRepository;

    @MockBean
    protected ScopeConnectionRepository scopeConnectionRepository;

    @MockBean
    protected GrantedTypeRepository grantedTypeRepository;
}
