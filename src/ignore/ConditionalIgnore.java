package ignore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to ignore test method depending on some condition check in runtime.
 * <p> <b>Important!</b> IgnoreCondition is evaluated earlier, than methods annotated with {@link org.junit.Before}
 * are called.
 * <p>
 * <p>There are 2 requirements to make this annotation work:
 * <p>1. Create instance of the {@link ConditionalIgnoreRule} in your test class.
 * <p>2. Either specify {@link IgnoreCondition} implementation in {@link ConditionalIgnore#value()}
 * parameter of this annotation, or mark your test class as {@link IgnoreConditionProvider} and implement
 * {@link IgnoreConditionProvider#provideIgnoreCondition(Class)} method.
 * <p>
 * <p>See {@link IgnoreConditionProvider} javadoc for sample usage.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ConditionalIgnore {
    /**
     * {@link IgnoreCondition} implementation, which is being checked in runtime to decide
     * whether to ignore test method or not.
     *
     * @return {@link IgnoreCondition} implementation class.
     */
    Class<? extends IgnoreCondition> value() default IgnoreCondition.class;
}
