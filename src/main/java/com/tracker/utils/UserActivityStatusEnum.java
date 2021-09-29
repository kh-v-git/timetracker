package com.tracker.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserActivityStatusEnum {
    REQUESTED, ACCEPTED, REJECTED, CLOSED;

    public static List<String> getUserActivityStatuses () {
        return  Arrays.stream(UserActivityStatusEnum.values())
                .map(UserActivityStatusEnum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
