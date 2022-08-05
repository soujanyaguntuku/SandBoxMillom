package millom.sandbox.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import millom.sandbox.CustomException.JdkException;
import millom.sandbox.pojos.ReleaseNames;

public class JdkMapper {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public ReleaseNames deserializeReleaseNames(String response)
      throws JdkException {
    try {
       return MAPPER.readValue(response, ReleaseNames.class);
    } catch (JsonProcessingException e) {
      throw new JdkException("Error while mapping the Json" + e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new JdkException("Content is null" + e.getMessage());
    }
  }
}
