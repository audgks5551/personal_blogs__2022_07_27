package site.itseasy.base;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperUtils {
    public static ModelMapper getModelMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setFieldMatchingEnabled(true)
//                .setFieldAccessLevel(PRIVATE)
                .setSkipNullEnabled(true);

        return mapper;
    }
}
