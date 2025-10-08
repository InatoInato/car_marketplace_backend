package car_marketplace.car_marketplace.controller;

import com.car_marketplace.car_marketplace.controller.CarController;
import com.car_marketplace.car_marketplace.dto.CarDto;
import com.car_marketplace.car_marketplace.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnAllCars() throws Exception {
        when(carService.getAllCars()).thenReturn(List.of(
                new CarDto(1L, "BMW", "M3", 2020, "Black", 3.0, 50000, "Fast car")
        ));

        mockMvc.perform(get("/api/cars/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].make").value("BMW"));
    }
}
