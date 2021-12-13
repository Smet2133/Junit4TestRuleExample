package ignore;

/**
 * Condition that is used by JUnit rule to ignore test methods.
 */
@FunctionalInterface
public interface IgnoreCondition {
    /**
     * Check whether ignore condition is satisfied and annotated test method must be ignored.
     *
     * @return {@code true} if test method must be ignored, {@code false} otherwise.
     */
    boolean check();
}
