package my.epam.unit07.task02;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CachedPropertiesFileBundleTest extends Assert {

    private static final String TEST_PROPS_FILE_NAME = "test.properties";

    @Test
    public void parallelReadingFileTestShouldReadOnce() throws InterruptedException {
        int bundlesCount = 20;
        File testPropsFile = new File(getClass().getResource("/" + TEST_PROPS_FILE_NAME).getFile());

        readBundles(bundlesCount, testPropsFile);

        long readingCount = getReadingCount();

        assertEquals(1, readingCount);
    }

    private long getReadingCount() {
        byte[] logBytes = LogAppender.getLog();
        String logString = new String(logBytes, Charset.defaultCharset());
        String[] logStrings = logString.split(System.getProperty("line.separator"));

        long count = Arrays.stream(logStrings)
                .filter((s) -> s.matches("((Reading).*" + TEST_PROPS_FILE_NAME + ".*)"))
                .count();

        LogAppender.clear();

        return count;
    }

    private void readBundles(int bundlesCount, File testPropsFile) throws InterruptedException {
        ExecutorService ex = Executors.newCachedThreadPool();
        for (int i = 0; i < bundlesCount; i++) {
            ex.execute(() -> CachedPropertiesFileBundle.getBundle(testPropsFile));
        }

        ex.shutdown();
        ex.awaitTermination(10, TimeUnit.SECONDS);
        CachedPropertiesFileBundle.clearBundlesCache();
    }

}