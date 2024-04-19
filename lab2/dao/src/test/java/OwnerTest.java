import org.junit.Test;
import static org.mockito.Mockito.*;
import org.dao.OwnerDao;
import org.mockito.Mockito;
import org.model.Owner;

import java.time.LocalDate;

public class OwnerTest {

    @Test
    public void testSaveOwner() {
        Owner owner = new Owner("Kotik Kitikovich", LocalDate.now());
        OwnerDao ownerDao = Mockito.mock(OwnerDao.class);
        ownerDao.saveOwner(owner);
        verify(ownerDao, times(1)).saveOwner(owner);
    }
    @Test
    public void testGetOwnerById() {
        OwnerDao ownerDao = Mockito.mock(OwnerDao.class);
        ownerDao.getOwnerById(1);
        verify(ownerDao, times(1)).getOwnerById(1);
    }

    @Test
    public void testUpdateOwner() {
        OwnerDao ownerDao = Mockito.mock(OwnerDao.class);
        Owner owner = new Owner("Kotik Kitikovich", LocalDate.now());
        ownerDao.updateOwner(owner);
        verify(ownerDao, times(1)).updateOwner(owner);
    }

    @Test
    public void testDeleteOwner() {
        OwnerDao ownerDao = Mockito.mock(OwnerDao.class);
        Owner owner = new Owner("Kotik Kitikovich", LocalDate.now());
        ownerDao.deleteOwner(owner);
        verify(ownerDao, times(1)).deleteOwner(owner);
    }
}
