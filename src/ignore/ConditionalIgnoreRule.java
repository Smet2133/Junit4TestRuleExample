package ignore;

import org.junit.Assume;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.lang.reflect.InvocationTargetException;

public class ConditionalIgnoreRule implements MethodRule {

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        ConditionalIgnore annotation = method.getAnnotation(ConditionalIgnore.class);
        if (annotation == null) return base;

        try {
            IgnoreCondition ignoreCondition = createCondition(annotation, target);

            return new Statement() {

                @Override
                public void evaluate() throws Throwable {
                    Assume.assumeFalse("Test ignored by @ConditionalIgnore annotation.", ignoreCondition.check());
                    base.evaluate();
                }
            };
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Exception while processing @ConditionalIgnore annotation!", e);
        }
    }

    private static IgnoreCondition createCondition(ConditionalIgnore annotation, Object testInstance) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<? extends IgnoreCondition> conditionType = annotation.value();

        // first check if test class implements IgnoreConditionProvider, so it must be used to create IgnoreCondition instance
        if (testInstance instanceof IgnoreConditionProvider) {
            return ((IgnoreConditionProvider) testInstance).provideIgnoreCondition(conditionType);
        } else {
            // if IgnoreCondition type wasn't specified explicitly in annotation parameter, fail with exception
            if (conditionType == IgnoreCondition.class) {
                throw new RuntimeException("To use @ConditionalIgnore annotation you must either specify " +
                        "IgnoreCondition implementation in annotation parameter, or your test class must implement " +
                        "IgnoreConditionProvider interface!");
            }

            // try to create IgnoreCondition using constructor with test class instance as an argument
            try {
                return conditionType.getConstructor(testInstance.getClass()).newInstance(testInstance);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                // if there is no constructor with test class instance argument, use default constructor
                return conditionType.getConstructor().newInstance();
            }
        }
    }
}
