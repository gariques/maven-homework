package com.id.filters;

import com.id.entity.CarStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarFilter {
    CarStatus status;
}
