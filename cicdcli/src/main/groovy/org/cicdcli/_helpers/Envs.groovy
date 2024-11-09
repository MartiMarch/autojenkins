package org.cicdcli._helpers

import org.cicdcli._helpers.po.ConfItem
import java.text.SimpleDateFormat
import org.cicdcli.logger.Logger


class Envs {

    static <T> ConfItem getEnv(String key, boolean isSecret, Class expectedType, T defaultValue = null) {
        String parsed_key = "CICDCLI_${key.toUpperCase()}"
        parsed_key = parsed_key.replace('.', '_')

        String value = System.getenv(parsed_key)
        if(value == null && defaultValue != null){
            value = defaultValue
        }
        if(value == null) {
            String error_msg = "[_helpers][Envs] Can't get environment variable using key ${key} because value is null"

            Logger.error(error_msg)
            throw new Exception(error_msg)
        }
        value = castValueToType(value, expectedType)

        return new ConfItem(
            key: key,
            isSecret: isSecret,
            type: expectedType,
            value: value
        )
    }

    static String getEnvValue(String EnvName) {
        System.getenv(EnvName)
    }

    static <T> T castValueToType(String value, Class<T> expectedType) {
        switch (expectedType) {
            case Integer:
                return Integer.parseInt(value) as T
            case Double:
                return Double.parseDouble(value) as T
            case Boolean:
                return Boolean.parseBoolean(value) as T
            case String:
                return value as T
            case List:
                return value.split(',')*.trim() as List as T
            case Long:
                return Long.parseLong(value) as T
            case Float:
                return Float.parseFloat(value) as T
            case Date:
                return new SimpleDateFormat("yyyy-MM-dd").parse(value) as T
            default:
                try {
                    return expectedType.getConstructor(String).newInstance(value) as T
                } catch (NoSuchMethodException exc) {
                    throw new IllegalArgumentException("[_helpers][Envs] Unsupported type: ${expectedType}\n${exc}")
                }
        }
    }
}
