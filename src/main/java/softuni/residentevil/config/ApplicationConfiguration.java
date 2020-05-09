package softuni.residentevil.config;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Gson gson(){
        return new Gson().newBuilder().create();
    }
}
