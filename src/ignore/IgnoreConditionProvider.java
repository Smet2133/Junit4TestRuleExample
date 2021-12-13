package ignore;

/**
 * {@code IgnoreConditionProvider} is used during {@link ConditionalIgnore} annotation processing to provide
 * instance of the {@link IgnoreCondition} implementation.
 *
 * <p>Class that implements {@code IgnoreConditionProvider} must be the test class, where you are using
 * {@link IgnoreCondition} provided by this {@code IgnoreConditionProvider}.
 *
 * <p><p>Example of usage:
 * <blockquote><pre>
 * public class SampleTest implements IgnoreConditionProvider {
 *     private boolean shouldRunTest = false;
 *    {@literal @}Rule
 *     public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();
 *
 *    {@literal @}Test
 *    {@literal @}{@link ConditionalIgnore}(SampleCondition.class)
 *     public void testMethod() {
 *         some test logic....
 *     }
 *
 *    {@literal @}Override
 *     public IgnoreCondition {@link IgnoreConditionProvider#provideIgnoreCondition}(Class<? extends IgnoreCondition> type) {
 *         // provide condition implementation according to type required by annotation parameter
 *         if (type == SampleCondition.class) {
 *             return new SampleCondition(shouldRunTest);
 *         }
 *     }
 *
 *     public static class SampleCondition implements {@link IgnoreCondition} {
 *         private boolean shouldIgnoreTest;
 *
 *         public SampleCondition(boolean shouldRunTest) {
 *             shouldIgnoreTest = !shouldRunTest;
 *         }
 *
 *        {@literal @}Override
 *         public boolean check() {
 *             return shouldIgnoreTest;
 *         }
 *     }
 * }
 * </blockquote></pre>
 */
@FunctionalInterface
public interface IgnoreConditionProvider {

    /**
     * Provides instance of the {@link IgnoreCondition} of the specified type.
     *
     * @param type the required {@link IgnoreCondition} implementation class.
     * @return instance of the required {@link IgnoreCondition} type to be used in
     * {@literal @}{@link ConditionalIgnore} annotation processing.
     * <p>
     * <p>See {@link IgnoreConditionProvider} javadoc for sample implementation.
     */
    IgnoreCondition provideIgnoreCondition(Class<? extends IgnoreCondition> type);
}
