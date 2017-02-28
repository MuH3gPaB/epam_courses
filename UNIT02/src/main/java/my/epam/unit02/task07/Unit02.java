package my.epam.unit02.task07;

import java.lang.annotation.*;

@Documented
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.SOURCE)
public @interface Unit02 {
    int taskNumber();
}
