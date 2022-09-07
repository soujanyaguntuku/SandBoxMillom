package millom.sandbox.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.List;
import millom.sandbox.CustomException.JdkException;
import millom.sandbox.Utility.JsonUtility;
import millom.sandbox.mapper.JdkMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JdkServiceTest {
  @Mock
  private JdkClientService jdkClientServiceMock;
  @InjectMocks
  private JdkService jdkService;

  @BeforeEach
  private void setUp() {
    MockitoAnnotations.initMocks(this);
    jdkService = new JdkService(jdkClientServiceMock, new JdkMapper());
  }

  @Test
  void ReleasesTest() throws  JdkException, IOException {
    doReturn(JsonUtility.getSampleResponse("JdkJsonResources/releaseNamesArmPage0.json"))
        .when(jdkClientServiceMock).getJdkJsonReleaseNames("arm",0);

    List<String> response = jdkService.getReleaseNamesMapping("arm");

    assertThat(response).isNotNull();
    assertThat(response).hasSize(10);
    assertThat(response.indexOf("jdk-18.0.2+9")).isEqualTo(1);
  }

  @Test
  void ReleasesTestPages3() throws  JdkException, IOException {

    doReturn(JsonUtility.getSampleResponse("JdkJsonResources/releaseNamesArmPage0.json"))
        .when(jdkClientServiceMock).getJdkJsonReleaseNames("arm",0);
    doReturn(JsonUtility.getSampleResponse("JdkJsonResources/releaseNamesArmPage1.json"))
        .when(jdkClientServiceMock).getJdkJsonReleaseNames("arm",1);
    doReturn(JsonUtility.getSampleResponse("JdkJsonResources/releaseNamesArmPage2.json"))
        .when(jdkClientServiceMock).getJdkJsonReleaseNames("arm",2);

    List<String> response = jdkService.getReleaseNamesMapping("arm");
    
    assertThat(response).isNotNull();
    assertThat(response).hasSize(24);
    assertThat(response.indexOf("jdk-18.0.2+9")).isEqualTo(1);
    assertThat(response.indexOf("jdk-18.0.2+19")).isEqualTo(11);
    assertThat(response.indexOf("jdk-18+88")).isEqualTo(23);
  }

}