package cn.wolves.config.jsonConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Jumping
 */
@Configuration
public class JsonConfig {

    @Bean
    @Primary
    public ObjectMapper getObjectMapper() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .timeZone(TimeZone.getTimeZone("GMT+8"))
            .simpleDateFormat("yyyy-MM-dd HH:mm:ss").build();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, LongToStringSerializer.INSTANCE);
        module.addSerializer(Long.TYPE, LongToStringSerializer.INSTANCE);
        module.addDeserializer(Date.class, new StringToDateDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

}
