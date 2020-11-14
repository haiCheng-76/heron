package webiste.lhc.heron.util;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/11/14 下午 11:19
 */
@Slf4j
public class CacheUtil {
    private static final Cache<String, Object> cache;

    static {
        cache = CacheBuilder.newBuilder()
                .maximumSize(500)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .recordStats()
                .concurrencyLevel(1)
                .build();
    }

    public static void set(String key, Object val) {
        if (StringUtils.hasLength(key) && Objects.nonNull(val)) {
            cache.put(key, val);
        }
    }

    public static Object get(String key) {
        if (StringUtils.hasLength(key)) {
            log.info("缓存命中率：{}；命中次数：{}", cache.stats().hitRate(), cache.stats().hitCount());
            return cache.getIfPresent(key);
        }
        return null;
    }

    public static Object getWithCallBack(String key, Callable callable) throws ExecutionException {
        if (StringUtils.hasLength(key)) {
            return cache.get(key, callable);
        }
        return null;
    }

    public static void del(String key) {
        if (StringUtils.hasLength(key)) {
            cache.invalidate(key);
        }
    }

    public static void batchDelete(List<String> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            cache.invalidateAll(list);
        }
    }
}
