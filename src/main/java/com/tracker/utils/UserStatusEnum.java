package com.tracker.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserStatusEnum {
    NEW, ACTIVE, DEACTIVATED;

    public static List<String> getUserStatuses () {
        return  Arrays.stream(UserStatusEnum.values())
                .map(UserStatusEnum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
