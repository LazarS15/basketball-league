package com.basketball.league.domain.valueobject;

import com.basketball.league.domain.valueobject.base.BaseId;

import java.util.UUID;

public class PlayerStatsId extends BaseId<UUID> {
    public PlayerStatsId(UUID value) {
        super(value);
    }
}
