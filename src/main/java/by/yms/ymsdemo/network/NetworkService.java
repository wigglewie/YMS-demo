package by.yms.ymsdemo.network;

import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalDateTime;
import java.util.List;

public interface NetworkService {

    @GET("vehicle/get/all")
    Call<List<String>> getAllRegNumbers();

    @POST("register/gate")
    Call<String> registerFromGate(@Query("regNumber") String regNumber);

    @POST("vehicle/remove")
    Call<String> vehicleOut(@Query("regNumber") String regNumber);

    @POST("dock/lock")
    Call<String> dockLock(@Query("dock") int dock);

    @POST("dock/unlock")
    Call<String> dockUnlock(@Query("dock") int dock);

    @POST("dock/lock/period")
    Call<String> dockLockPeriod(@Query("dock") int dock, @Query("dateFrom") LocalDateTime dateFrom, @Query("dateTo") LocalDateTime dateTo);

    @GET("wms/get")
    Call<List<WMSShipment>> getVehiclesForDocks(@Query("docks") Integer[] docks);
}
