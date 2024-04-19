import org.controllers.OwnerController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.model.Owner;
import org.services.OwnerService;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OwnerMainTest {
    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    @Before
    public void setUp() {
        ownerService = mock(OwnerService.class);
        ownerController = new OwnerController(ownerService);
    }

    @Test
    public void testCreateOwner() {
        String name = "Vanyok";
        LocalDate birthDate = LocalDate.of(2002, 2, 2);
        ownerController.createOwner(name, birthDate);
        verify(ownerService).saveOwner(any());
    }

    @Test
    public void testGetOwnerById() {
        int id = 5;
        ownerController.getOwnerById(id);
        verify(ownerService).getOwnerById(id);
    }

    @Test
    public void testUpdateOwner() {
        Owner owner = new Owner();
        int id = 5;
        owner.setId(id);
        when(ownerService.getOwnerById(id)).thenReturn(owner);
        ownerController.updateOwner(id, "Sanyok", LocalDate.now());
        verify(ownerService).updateOwner(owner);
    }

    @Test
    public void testDeleteOwner() {
        Owner owner = new Owner();
        int id = 5;
        owner.setId(id);
        when(ownerService.getOwnerById(id)).thenReturn(owner);
        ownerController.deleteOwner(id);
        verify(ownerService).deleteOwner(owner);
    }
}