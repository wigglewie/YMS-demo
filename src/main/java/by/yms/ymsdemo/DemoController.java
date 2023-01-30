package by.yms.ymsdemo;

import by.yms.ymsdemo.network.ErrorResponse;
import by.yms.ymsdemo.network.RetrofitFactory;
import by.yms.ymsdemo.network.WMSShipment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class DemoController implements Initializable {

    public ListView<Integer> listViewDocksEmpty;

    public TextField textFieldTimeTo;

    public TextField textFieldTimeFrom;

    public DatePicker datePickerTo;

    public TextField textFieldDockNum;

    public ListView<String> listViewRegNumbers;

    public TextField textFieldRegNumberOUT;

    public TextField textFieldRegNumberIN;

    public DatePicker datePickerFrom;

    public TextField textFieldDocksCall;

    public Label infoVehicle;

    public Label infoDock;

    public Label infoWMS;

    private Set<Integer> docksAvailable = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRegNumbers();
        setupTextFields();
        setupListViews();
    }

    private void setupTextFields() {
        textFieldRegNumberIN.setTextFormatter(new TextFormatter<>((change -> {
            change.setText(change.getText().toUpperCase());
            return change;
        })));
    }

    private void loadRegNumbers() {
        RetrofitFactory.service().getAllRegNumbers().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                System.out.println(response.body());
                Platform.runLater(
                        () -> {
                            listViewRegNumbers.setItems(FXCollections.observableArrayList(response.body()));
                            textFieldRegNumberIN.setText("");
                            textFieldRegNumberOUT.setText("");
                        });
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable throwable) {
            }
        });
    }

    private void setupListViews() {
        listViewRegNumbers.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
            if (t1 != null) {
                textFieldRegNumberOUT.setText(t1);
            }
        }));
    }

    @FXML
    public void onVehicleINButtonClick() {
        if (!textFieldRegNumberIN.getText().isEmpty()) {
            RetrofitFactory.service().registerFromGate(textFieldRegNumberIN.getText()).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        loadRegNumbers();
                        Platform.runLater(() -> infoVehicle.setText(response.body()));
                    } else {
                        ErrorResponse res = new Gson().fromJson(response.errorBody().charStream(),
                                new TypeToken<ErrorResponse>() {
                                }.getType());
                        Platform.runLater(() -> infoVehicle.setText(res.getMessage()));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {

                }
            });
        }
    }

    @FXML
    public void onVehicleOUTButtonClick() {
        if (!textFieldRegNumberOUT.getText().isEmpty()) {
            RetrofitFactory.service().vehicleOut(textFieldRegNumberOUT.getText()).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        loadRegNumbers();
                        Platform.runLater(() -> infoVehicle.setText(response.body()));
                    } else {
                        ErrorResponse res = new Gson().fromJson(response.errorBody().charStream(),
                                new TypeToken<ErrorResponse>() {
                                }.getType());
                        Platform.runLater(() -> infoVehicle.setText(res.getMessage()));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {

                }
            });
        }
    }

    @FXML
    public void onDockLockButtonClick() {
        if (!textFieldDockNum.getText().isEmpty()) {
            if (textFieldTimeFrom.getText().isEmpty() || textFieldTimeTo.getText().isEmpty()
                    || datePickerFrom.getValue() == null || datePickerTo.getValue() == null) {
                dockLock();
            } else {
                dockLockPeriod();
            }
        }
    }

    @FXML
    public void onDockUnlockButtonClick() {
        if (!textFieldDockNum.getText().isEmpty()) {
            dockUnlock();
        }
    }

    private void dockLock() {
        try {
            RetrofitFactory.service().dockLock(Integer.parseInt(textFieldDockNum.getText())).enqueue(
                    new Callback<>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 200) {
                                Platform.runLater(() -> infoDock.setText(response.body()));
                            } else {
                                ErrorResponse res = new Gson().fromJson(response.errorBody().charStream(),
                                        new TypeToken<ErrorResponse>() {
                                        }.getType());
                                Platform.runLater(() -> infoDock.setText(res.getMessage()));
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable throwable) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dockUnlock() {
        try {
            RetrofitFactory.service().dockUnlock(Integer.parseInt(textFieldDockNum.getText())).enqueue(
                    new Callback<>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 200) {
                                Platform.runLater(() -> infoDock.setText(response.body()));
                            } else {
                                ErrorResponse res = new Gson().fromJson(response.errorBody().charStream(),
                                        new TypeToken<ErrorResponse>() {
                                        }.getType());
                                Platform.runLater(() -> infoDock.setText(res.getMessage()));
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable throwable) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dockLockPeriod() {
        try {
            int num = Integer.parseInt(textFieldDockNum.getText());
            LocalDate dateFrom = datePickerFrom.getValue();
            LocalDate dateTo = datePickerTo.getValue();

            String[] splitFrom = textFieldTimeFrom.getText().split(":");
            LocalTime timeFrom = LocalTime.of(Integer.parseInt(splitFrom[0]), Integer.parseInt(splitFrom[1]));
            LocalDateTime localFrom = dateFrom.atTime(timeFrom);

            String[] splitTo = textFieldTimeTo.getText().split(":");
            LocalTime timeTo = LocalTime.of(Integer.parseInt(splitTo[0]), Integer.parseInt(splitTo[1]));
            LocalDateTime localTo = dateTo.atTime(timeTo);

            RetrofitFactory.service().dockLockPeriod(num, localFrom, localTo).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        Platform.runLater(() -> infoDock.setText(response.body()));
                    } else {
                        ErrorResponse res = new Gson().fromJson(response.errorBody().charStream(),
                                new TypeToken<ErrorResponse>() {
                                }.getType());
                        Platform.runLater(() -> infoDock.setText(res.getMessage()));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {

                }
            });

        } catch (Exception e) {
            infoDock.setText("ERROR");
            e.printStackTrace();
        }
    }

    @FXML
    public void onDocksCallButtonClick() {
        if (!textFieldDocksCall.getText().isEmpty()) {
            textFieldDocksCall.setText(textFieldDocksCall.getText().replaceAll("\\s+",""));
            String[] split = textFieldDocksCall.getText().split(",");
            Integer[] docks = new Integer[split.length];
            for (int i = 0; i < split.length; i++) {
                docks[i] = Integer.parseInt(split[i]);
            }
            RetrofitFactory.service().getVehiclesForDocks(docks).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<WMSShipment>> call, Response<List<WMSShipment>> response) {
                    if (response.code() == 200) {
                        Platform.runLater(() -> infoWMS.textProperty().setValue(response.body().toString()));
                        List<Integer> docksOriginal = Arrays.asList(docks);
                        if (response.body() != null) {
                            List<Integer> dockCopy = new ArrayList<>(docksOriginal);
                            response.body().forEach(wmsShipment -> docksOriginal.forEach(value -> {
                                if (value == wmsShipment.getDockNumber()) {
                                    dockCopy.remove(value);
                                }
                            }));
                            docksAvailable.addAll(dockCopy);
                            Platform.runLater(() -> {
                                listViewDocksEmpty.setItems(FXCollections.observableArrayList(docksAvailable));
                            });
                        } else {
                            docksAvailable.addAll(docksOriginal);
                        }
                    } else {
                        ErrorResponse res = new Gson().fromJson(response.errorBody().charStream(),
                                new TypeToken<ErrorResponse>() {
                                }.getType());
                        Platform.runLater(() -> infoWMS.setText(res.getMessage()));
                    }
                }

                @Override
                public void onFailure(Call<List<WMSShipment>> call, Throwable throwable) {

                }
            });
        }
    }

}