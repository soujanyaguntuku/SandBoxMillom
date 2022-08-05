package millom.sandbox.service;

import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import millom.sandbox.CustomException.JdkException;
import millom.sandbox.mapper.JdkMapper;
import millom.sandbox.pojos.ReleaseNames;

public class JdkService {

  @Inject
  private JdkClientService jdkClientService;
  @Inject
  private JdkMapper jdkMapper;

  public List<String> getReleaseNamesMapping(String architecture) throws JdkException {
    List<String> allPagesReleaseNames = new ArrayList<>();
    String marsWeatherAsString = "";

    for (int page = 0; marsWeatherAsString != null; page++) {
      marsWeatherAsString = jdkClientService.getJdkJsonReleaseNames(architecture, page);

      if (marsWeatherAsString != null) {
        ReleaseNames eachPageReleaseNames = jdkMapper.deserializeReleaseNames(marsWeatherAsString);
        for (String release : eachPageReleaseNames.releases()) {
          allPagesReleaseNames.add(release);
        }
      }
    }
    return allPagesReleaseNames;
  }
}
