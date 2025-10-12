package car_marketplace.car_marketplace.controller;

import com.car_marketplace.car_marketplace.controller.CarController;
import com.car_marketplace.car_marketplace.dto.CarDto;
import com.car_marketplace.car_marketplace.dto.CreateCarDto;
import com.car_marketplace.car_marketplace.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
@Import(CarControllerTest.CarServiceMockConfig.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ новый способ — мок зарегистрирован через @Import внутреннего класса
    static class CarServiceMockConfig {
        @Mock
        CarService carService;

        public CarService carService() {
            MockitoAnnotations.openMocks(this);
            return carService;
        }
    }

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUp() {
        Mockito.reset(carService);
    }

    @Test
    void createCar_ShouldReturnCreatedCar() throws Exception {
        CreateCarDto createCar = new CreateCarDto("Toyota", "Camry", 2020, "Black", 2.5, 20000, "A reliable sedan");
        CarDto carDto = new CarDto(1L, "Toyota", "Camry", 2020, "Black", 2.5, 20000, "A reliable sedan");

        Mockito.when(carService.createCar(any(CreateCarDto.class))).thenReturn(carDto);

        mockMvc.perform(post("/api/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"));
    }

    @Test
    void getAllCars_ShouldReturnList() throws Exception {
        List<CarDto> cars = List.of(
                new CarDto(1L, "Toyota", "Camry", 2020, "Black", 2.5, 20000, "A reliable sedan"),
                new CarDto(2L, "Honda", "Civic", 2018, "Blue", 2.0, 15000, "A compact car")
        );
        Mockito.when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/api/cars/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getCarById_ShouldReturnCar() throws Exception {
        CarDto carDto = new CarDto(1L, "Toyota", "Camry", 2020, "Black", 2.5, 20000, "A reliable sedan");
        Mockito.when(carService.getCarById(1L)).thenReturn(carDto);

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.color").value("Black"));
    }

    @Test
    void updateCar_ShouldReturnUpdatedCar() throws Exception {
        CarDto updateDto = new CarDto(1L, "Toyota", "Camry", 2021, "White", 2.5, 22000, "Updated sedan");
        Mockito.when(carService.updateCar(Mockito.eq(1L), any(CarDto.class))).thenReturn(updateDto);

        mockMvc.perform(put("/api/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(2021))
                .andExpect(jsonPath("$.color").value("White"));
    }

    @Test
    void updateCarPrice_ShouldReturnUpdatedCar() throws Exception {
        CarDto carDto = new CarDto(1L, "Toyota", "Camry", 2020, "Black", 2.5, 25000, "A reliable sedan");
        Mockito.when(carService.updateCarPrice(Mockito.eq(1L), any(CarDto.class))).thenReturn(carDto);

        mockMvc.perform(patch("/api/cars/1/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25000));
    }

    @Test
    void deleteCar_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/cars/1"))
                .andExpect(status().isNoContent());
    }
}
