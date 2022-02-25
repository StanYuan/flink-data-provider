package data.provider.mongo.service;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/21 14:40
 */
public interface DataProviderService {

    /**
     * @param data 日期
     * @param date
     */
    void provideDataStream(String date);
}
