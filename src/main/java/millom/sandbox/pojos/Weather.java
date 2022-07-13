package millom.sandbox.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
  @JsonProperty("descriptions")
  private Descriptions descriptions;
  @JsonProperty("soles")
  private List<Sol> soles;
  private Weather(){
  }

  private Weather(WeatherBuilder weatherBuilder){
    this.descriptions = weatherBuilder.descriptions;
    this.soles = weatherBuilder.soles;
  }

  public Descriptions getDescriptions() {
    return descriptions;
  }

  public List<Sol> getSoles() {
    return soles;
  }

  public static class WeatherBuilder {
    // builder code
    private Descriptions descriptions;
    private List<Sol> soles;
    public WeatherBuilder(Descriptions descriptions,  List<Sol> soles){
      this.descriptions = descriptions;
      this.soles = soles;
    }
    public Weather build() {
      return new Weather(this);
    }
  }

}
