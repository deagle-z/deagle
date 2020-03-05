/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.chinamobile.cmss.cpms.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadLocalMap {
    /**
     * The constant threadContext.
     */
    private final static ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new MapThreadLocal();

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public static void put(final String key, final Object value) {
        getContextMap().put(key, value);
    }

    /**
     * Remove object.
     *
     * @param key the key
     *
     * @return the object
     */
    public static Object remove(final String key) {
        return getContextMap().remove(key);
    }

    /**
     * Get object.
     *
     * @param key the key
     *
     * @return the object
     */
    public static Object get(final String key) {
        return getContextMap().get(key);
    }

    private static class MapThreadLocal extends ThreadLocal<Map<String, Object>> {
        /**
         * Initial value map.
         *
         * @return the map
         */
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>(8) {

                private static final long serialVersionUID = 3637958959138295593L;

                @Override
                public Object put(final String key, final Object value) {
                    return super.put(key, value);
                }
            };
        }
    }

    /**
     * 取得thread context Map的实例。
     *
     * @return thread context Map的实例
     */
    private static Map<String, Object> getContextMap() {
        return THREAD_CONTEXT.get();
    }

    /**
     * 清理线程所有被hold住的对象。以便重用！
     */
    public static void remove() {
        getContextMap().clear();
    }
}
