package com.app.airport.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class BookingDto {

  @Schema(description = "Booking number")
  @NotBlank(message = "Booking number cannot be blank!")
  @NotNull(message = "Booking number cannot be null!")
  private String bookingRef;

  @Schema(description = "booking date")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Date bookDate;

  @Schema(description = "Total amount")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private BigDecimal totalAmount;
}
