import static org.mockito.Mockito.*;
import org.dao.CatDao;
import org.junit.Test;
import org.mockito.Mockito;
import org.model.Cat;

import java.time.LocalDate;

public class CatTest {
    @Test
    public void testSaveCat() {
        CatDao catDao = Mockito.mock(CatDao.class);
        Cat cat = new Cat("Pushistaya Skotinka", LocalDate.now(), "Just Cat", "Gray", 1);
        catDao.saveCat(cat);
        verify(catDao, times(1)).saveCat(cat);
    }

    @Test
    public void testGetCatById() {
        CatDao catDao = Mockito.mock(CatDao.class);
        catDao.getCatById(1);
        verify(catDao, times(1)).getCatById(1);
    }

    @Test
    public void testGetCatByName() {
        CatDao catDao = Mockito.mock(CatDao.class);
        catDao.getCatByName("Yellow-Eyed Terrorist");
        verify(catDao, times(1)).getCatByName("Yellow-Eyed Terrorist");
    }

    @Test
    public void testUpdateCat() {
        CatDao catDao = Mockito.mock(CatDao.class);
        Cat cat = new Cat("Pushistaya Skotinka", LocalDate.now(), "Just Cat", "Gray", 1);
        catDao.updateCat(cat);
        verify(catDao, times(1)).updateCat(cat);
    }

    @Test
    public void testDeleteCat() {
        CatDao catDao = Mockito.mock(CatDao.class);
        Cat cat = new Cat("Pushistaya Skotinka", LocalDate.now(), "Just Cat", "Gray", 1);
        catDao.deleteCat(cat);
        verify(catDao, times(1)).deleteCat(cat);
    }

    @Test
    public void testAddFriendToCat() {
        CatDao catDao = Mockito.mock(CatDao.class);
        Cat cat1 = new Cat("Pushistaya Skotinka", LocalDate.now(), "Just Cat", "Gray", 1);
        Cat cat2 = new Cat("Yellow-Eyed Terrorist", LocalDate.now(), "Scottish Fold", "Gray", 2);
        catDao.addFriendToCat(cat1, cat2);
        verify(catDao, times(1)).addFriendToCat(cat1, cat2);
    }

}