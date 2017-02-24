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

public class XMLFileStationeryDaoTest extends Assert {

    private static XMLFileStationeryDao xmlDao;
    private static final String FILE_PATH = "./stationery.xml";

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
        xmlDao = new XMLFileStationeryDao(file);
    }

    @Test
    public void getAll() {
        Pen[] pens = new Pen[3];
        pens[0] = new Pen(Color.black, Color.CYAN, "BIC", Pen.BALLPOINT_PEN_TYPE, "RollerBall");
        pens[1] = new Pen(Color.CYAN, Color.WHITE, "Parker", Pen.GEL_INK_PEN_TYPE, "Parker");
        pens[2] = new Pen(Color.RED, Color.RED, "Erich Krauze", Pen.FIBER_TIP_PEN_TYPE, "EK");

        for(Stationery st : pens){
            xmlDao.saveOrUpdate(st);
        }

        Stationery[] sts = xmlDao.getAll();
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

    }

    @Test
    public void removeById() {

    }

    @Test
    public void removeByInstance() {

    }

    @Test
    public void saveOrUpdate() {

    }

    @AfterClass
    public static void clearDao() {
        File file = new File(FILE_PATH);
        if (!file.delete()) {
            fail("Could not delete file " + FILE_PATH + " after tests.");
        }
    }
}

