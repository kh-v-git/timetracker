package com.tracker.impl.common.data;

import java.util.List;

public class DataService {
    private DataRepository dataRepository;
    public DataService (DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
    public List<AssignToUserActivity> assignToUserActivityList (int userId) {
        return  dataRepository.findAllUserActivityList(userId);
    }
}
