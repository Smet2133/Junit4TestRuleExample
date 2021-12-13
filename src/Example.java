import ignore.ConditionalIgnoreRule;
import ignore.IgnoreConditionProvider;

import java.util.Map;

@Slf4j
@RunWith(ArquillianConditionalRunner.class)
public class Example implements IgnoreConditionProvider {

    /**
     * Rule that allows to ignore tests using {@link com.netcracker.it.maas.ignore.ConditionalIgnore} annotation.
     */
    @Rule
    public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

    @Test
    @ConditionalIgnore
    public void checkZkDataExist() throws Exception {
        Map<String, Object> classifier = createSimpleClassifier("ZookeeperMaasIT", "it-test");
        VirtualHostResponse virtualHost = createVirtualHost(201, classifier);
        try (CuratorFramework zkClient = createZkClient()) {
            checkZkData(virtualHost, zkClient, true);
        }
    }

    @Override
    public IgnoreCondition provideIgnoreCondition(Class<? extends IgnoreCondition> type) {
        return () -> {
            initHelper();
            return StringUtils.isBlank(helper.getPodEnv(pod, "ENV_ZOOKEEPER_ADDRESS"));
        };
    }
}

