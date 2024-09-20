package com.basketball.league.domain.valueobject;

import com.basketball.league.domain.valueobject.base.BaseId;

import java.util.UUID;

public class GameId extends BaseId<UUID> {
    public GameId(UUID value) {
        super(value);
    }
}
