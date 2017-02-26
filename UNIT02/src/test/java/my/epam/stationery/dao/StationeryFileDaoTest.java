package my.epam.stationery.dao;

import my.epam.stationery.model.Pen;
import my.epam.stationery.model.Stationery;
import my.epam.stationery.model.StringParser;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class StationeryFileDaoTest extends Assert {

    private static FiledDao<Stationery> stDao;
    private static final String FILE_PATH = "./stationery.txt";

    @BeforeClass
    public static void createDao() {
        File file = new File(FILE_PATH);
        try {
            if (!file.createNewFile()) {
                if (!file.delete()) {
                    fail("File " + FILE_PATH + " already exist. Could not delete the file.");
                } else {
                    if (!file.createNewFile()) {
                        fail("Could not create the file " + FILE_PATH);
                    }
                }
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
        stDao = new FiledDao<>(file, new StringParser<>(Stationery.class));
    }

    @Test
    public void getAll() {
        Pen[] pens = new Pen[3];
        pens[0] = new Pen(Color.black, Color.CYAN, "BIC", Pen.BALLPOINT_PEN_TYPE, "RollerBall");
        pens[1] = new Pen(Color.CYAN, Color.WHITE, "Parker", Pen.GEL_INK_PEN_TYPE, "Parker");
        pens[2] = new Pen(Color.RED, Color.RED, "Erich Krauze", Pen.FIBER_TIP_PEN_TYPE, "EK");

        for (Stationery st : pens) {
            stDao.saveOrUpdate(st);
        }

        List<Stationery> sts = stDao.getAll();
        for (Pen pen : pens) {
            assertTrue(pen + " NOT FOUND in getAll() results.", sts.contains(pen));
        }
    }

    @Test
    public void getById() {
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        long id = stDao.saveOrUpdate(pen);
        Pen readedPen = (Pen) stDao.getById(id);
        assertEquals(pen, readedPen);
    }

    @Test
    public void removeById() {
        Pen p1 = Pen.DEFAULT_BLACK_PEN;
        Pen p2 = Pen.DEFAULT_RED_PEN;
        Pen p3 = Pen.DEFAULT_BLUE_PEN;

        long id1 = stDao.saveOrUpdate(p1);
        long id2 = stDao.saveOrUpdate(p2);
        long id3 = stDao.saveOrUpdate(p3);

        stDao.remove(id1);

        assertEquals(null, stDao.getById(id1));

        stDao.remove(id2);

        assertEquals(null, stDao.getById(id2));

        stDao.remove(id3);

        assertEquals(null, stDao.getById(id3));
    }

    @Test
    public void removeByInstance() {
        Pen p1 = Pen.DEFAULT_BLACK_PEN;
        Pen p2 = Pen.DEFAULT_RED_PEN;
        Pen p3 = Pen.DEFAULT_BLUE_PEN;

        long id1 = stDao.saveOrUpdate(p1);
        long id2 = stDao.saveOrUpdate(p2);
        long id3 = stDao.saveOrUpdate(p3);

        stDao.remove(stDao.getById(id1));

        assertEquals(null, stDao.getById(id1));

        stDao.remove(stDao.getById(id2));

        assertEquals(null, stDao.getById(id2));

        stDao.remove(stDao.getById(id3));

        assertEquals(null, stDao.getById(id3));
    }
    
    @Test
    public void updateRecordTest() {
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        Pen pen1 = Pen.DEFAULT_BLUE_PEN;
        Pen pen2 = Pen.DEFAULT_RED_PEN;
        stDao.saveOrUpdate(pen1);
        stDao.saveOrUpdate(pen2);
        long id = stDao.saveOrUpdate(pen);
        Pen readedPen = (Pen) stDao.getById(id);
        stDao.saveOrUpdate(readedPen);
    }

    @AfterClass
    public static void clearDao() {
        File file = new File(FILE_PATH);
        if (!file.delete()) {
            fail("Could not delete file " + FILE_PATH + " after tests.");
        }
    }
}

