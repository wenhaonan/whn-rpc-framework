import com.will.register.ZkServiceRegistry;
import org.junit.Test;

import java.net.InetSocketAddress;

import static org.junit.Assert.assertEquals;

/**
 * @author haonan.wen
 * @createTime 2022/5/29 下午6:01
 */
public class ZkRegistryTest {

    @Test
    public void should_register_service_successful_and_lookup_service_by_service_name() {
        ZkServiceRegistry zkServiceRegistry = new ZkServiceRegistry();
        InetSocketAddress givenInetSocketAddress = new InetSocketAddress("127.0.0.1", 9333);
        zkServiceRegistry.registerService("com.will.registry.ZkServiceRegistry", givenInetSocketAddress);
        InetSocketAddress acquiredInetSocketAddress = zkServiceRegistry.lookupService("com.will.registry.ZkServiceRegistry");
        assertEquals(givenInetSocketAddress.toString(), acquiredInetSocketAddress.toString());
    }
}
