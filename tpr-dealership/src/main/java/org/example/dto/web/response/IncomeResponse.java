package org.example.dto.web.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeResponse {

    private Float dealership;

    private Float auction;

    private Float total;
}
