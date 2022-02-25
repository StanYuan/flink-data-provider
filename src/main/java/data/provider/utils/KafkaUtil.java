package data.provider.utils;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

/**
 * Desc: 往kafka中写数据,可以使用这个main函数进行测试
 * Created by zhisheng on 2019-02-17
 * Blog: http://www.54tianzhisheng.cn/tags/Flink/
 */
public class KafkaUtil {
    public static final String broker_list = "localhost:9092";
    private static Logger logger = LoggerFactory.getLogger(KafkaUtil.class);

    public static void writeToKafka(String topic, String message) {
        Properties props = new Properties();
        props.put("bootstrap.servers", broker_list);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer<String, String>(props);
        ProducerRecord record = new ProducerRecord<String, String>(topic, null, null, message);
        producer.send(record);
        logger.info("发送数据:{} ", message);
        producer.flush();
    }

}
