package my.epam.stationery.dao;

import my.epam.stationery.model.Pen;
import my.epam.stationery.model.Stationery;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StationeryFileDaoTest extends Assert {

    private static StationeryFileDao stDao;
    private static final String FILE_PATH = "./stationery.txt";

    @BeforeClass
    public static void createDao() {
        File file = new File(FILE_PATH);
        try {
            if (!file.createNewFile()) {
//                if (!file.delete()) {
//                    fail("File " + FILE_PATH + " already exist. Could not delete the file.");
//                } else {
//                    if (!file.createNewFile()) {
//                        fail("Could not create the file " + FILE_PATH);
//                    }
//                }
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
        stDao = new StationeryFileDao(file);
    }

    @Test
    public void getAll() {
        Pen[] pens = new Pen[3];
        pens[0] = new Pen(Color.black, Color.CYAN, "BIC", Pen.BALLPOINT_PEN_TYPE, "RollerBall");
        pens[1] = new Pen(Color.CYAN, Color.WHITE, "Parker", Pen.GEL_INK_PEN_TYPE, "Parker");
        pens[2] = new Pen(Color.RED, Color.RED, "Erich Krauze", Pen.FIBER_TIP_PEN_TYPE, "EK");

        for(Stationery st : pens){
            stDao.saveOrUpdate(st);
        }

        Stationery[] sts = stDao.getAll();
        assertEquals(pens.length, sts.length);
        for(int i = 0; i < sts.length; i++){
            boolean present = false;
            for (Stationery st : sts) {
                if (pens[i].equals(st)) present = true;
            }
            assertTrue(pens[i] + " NOT FOUND in getAll() results.", present);
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

        assertEquals(3, stDao.getAll().length);

        stDao.remove(id1);

        assertEquals(2, stDao.getAll().length);
        assertEquals(null, stDao.getById(id1));

        stDao.remove(id2);

        assertEquals(1, stDao.getAll().length);
        assertEquals(null, stDao.getById(id2));

        stDao.remove(id3);

        assertEquals(0, stDao.getAll().length);
        assertEquals(null, stDao.getById(id3));
    }

    @Test
    public void removeByInstance() {

    }

    @Test
    public void saveOrUpdate() {

    }

    @Test
    public void updateRecordTest(){
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
//        if (!file.delete()) {
//            fail("Could not delete file " + FILE_PATH + " after tests.");
//        }
    }
}

