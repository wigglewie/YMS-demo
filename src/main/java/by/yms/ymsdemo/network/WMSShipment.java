package by.yms.ymsdemo.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WMSShipment {

    @JsonProperty("id")
    public int id;

    @JsonProperty("plate")
    public String regNumber;

    @JsonProperty("phone")
    public String phoneNumber;

    @JsonProperty("order_number")
    public String[] orderNumber;

    @JsonProperty("car_type")
    public int vehicleType;

    @JsonProperty("place")
    public int dockNumber;

    @JsonProperty("destination")
    public String destination;

    @Override
    public String toString() {
        return "WMSShipment{id=" + id + ", regNumber=" + regNumber + ", phoneNumber=" + phoneNumber + ", dockNumber=" + dockNumber + '}' + "\n";
    }

}
