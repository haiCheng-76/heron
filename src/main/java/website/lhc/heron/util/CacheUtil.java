package website.lhc.heron.util;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import website.lhc.heron.commo.enums.CacheEnum;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: guava缓存
 * @author: 582895699@qq.com
 * @time: 2020/11/14 下午 11:19
 */
@Slf4j
public class CacheUtil {

    /**
     * 分隔符
     */
    private static final String SEPARATOR = ":";

    private static final Cache<String, Object> cache;

    static {
        cache = CacheBuilder.newBuilder()
                .maximumSize(500)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .recordStats()
                .concurrencyLevel(1)
                .build();
    }

    public static void set(CacheEnum prefix, String key, Object val) {
        if (StringUtils.hasLength(key) && Objects.nonNull(val)) {
            cache.put(prefix.name() + SEPARATOR + key, val);
        }
    }

    public static Object get(CacheEnum prefix, String key) {
        if (StringUtils.hasLength(key)) {
            log.info("缓存命中率：{}；命中次数：{}", cache.stats().hitRate(), cache.stats().hitCount());
            return cache.getIfPresent(prefix.name() + SEPARATOR + key);
        }
        return null;
    }

    public static Object getWithCallBack(CacheEnum prefix, String key, Callable callable) throws ExecutionException {
        if (StringUtils.hasLength(key)) {
            return cache.get(prefix.name() + SEPARATOR + key, callable);
        }
        return null;
    }

    public static void del(CacheEnum prefix, String key) {
        if (StringUtils.hasLength(key)) {
            cache.invalidate(prefix.name() + SEPARATOR + key);
        }
    }

    public static void batchDelete(List<String> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            cache.invalidateAll(list);
        }
    }
}
