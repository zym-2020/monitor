package nnu.edu.back.common.auth;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * @Author: Yiming
 * @Date: 2023/07/28/20:01
 * @Description:
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
}
