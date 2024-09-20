package com.basketball.league.domain.valueobject;

import com.basketball.league.domain.valueobject.base.BaseId;

public class TeamId extends BaseId<Long> {
    public TeamId(Long value) {
        super(value);
    }
}
