package millom.sandbox.service;

import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import millom.sandbox.CustomException.JdkException;
import millom.sandbox.mapper.JdkMapper;
import millom.sandbox.pojos.ReleaseNames;

public class JdkService {

  private JdkClientService jdkClientService;
  private JdkMapper jdkMapper;
  @Inject
  public JdkService(JdkClientService jdkClientService, JdkMapper jdkMapper) {
    this.jdkClientService = jdkClientService;
    this.jdkMapper = jdkMapper;
  }


  public List<String> getReleaseNamesMapping(String architecture) throws JdkException {
    List<String> allPagesReleaseNames = new ArrayList<>();
    String releaseNamesAsString = "";

    for (int page = 0; releaseNamesAsString != null; page++) {
      releaseNamesAsString = jdkClientService.getJdkJsonReleaseNames(architecture, page);

      if (releaseNamesAsString != null) {
        ReleaseNames eachPageReleaseNames = jdkMapper.deserializeReleaseNames(releaseNamesAsString);
        for (String release : eachPageReleaseNames.releases()) {
          allPagesReleaseNames.add(release);
        }
      }
    }
    return allPagesReleaseNames;
  }
}
