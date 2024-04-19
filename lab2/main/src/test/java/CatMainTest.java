import org.controllers.CatController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.model.Cat;
import org.services.CatService;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CatMainTest {
    @Mock
    private CatService catService;

    @InjectMocks
    private CatController catController;

    @Before
    public void setUp() {
        catService = mock(CatService.class);
        catController = new CatController(catService);
    }

    @Test
    public void testCreateCat() {
        String name = "Boris";
        LocalDate birthDate = LocalDate.of(2021, 4, 25);
        String breed = "Abyssinian";
        String color = "Red";
        catController.createCat(name, birthDate, breed, color, 1);
        verify(catService).saveCat(any());
    }

    @Test
    public void testUpdateCat() {
        Cat cat = new Cat();
        int id = 5;
        cat.setId(id);
        when(catService.getCatById(id)).thenReturn(cat);
        catController.updateCat(id, "Kotichek", "Cute", "Black");
        verify(catService).updateCat(cat);
    }


    @Test
    public void testDeleteCat() {
        Cat cat = new Cat();
        int id = 5;
        cat.setId(id);
        when(catService.getCatById(id)).thenReturn(cat);
        catController.deleteCat(id);
        verify(catService).deleteCat(cat);
    }
}