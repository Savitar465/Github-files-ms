package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PaginationMeta
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class PaginationMeta {

  private BigDecimal page;

  private BigDecimal perPage;

  private BigDecimal total;

  private BigDecimal totalPages;

  public PaginationMeta() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PaginationMeta(BigDecimal page, BigDecimal perPage, BigDecimal total, BigDecimal totalPages) {
    this.page = page;
    this.perPage = perPage;
    this.total = total;
    this.totalPages = totalPages;
  }

  public PaginationMeta page(BigDecimal page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   * @return page
  */
  @NotNull @Valid 
  @Schema(name = "page", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("page")
  public BigDecimal getPage() {
    return page;
  }

  public void setPage(BigDecimal page) {
    this.page = page;
  }

  public PaginationMeta perPage(BigDecimal perPage) {
    this.perPage = perPage;
    return this;
  }

  /**
   * Get perPage
   * @return perPage
  */
  @NotNull @Valid 
  @Schema(name = "perPage", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("perPage")
  public BigDecimal getPerPage() {
    return perPage;
  }

  public void setPerPage(BigDecimal perPage) {
    this.perPage = perPage;
  }

  public PaginationMeta total(BigDecimal total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  */
  @NotNull @Valid 
  @Schema(name = "total", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("total")
  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public PaginationMeta totalPages(BigDecimal totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Get totalPages
   * @return totalPages
  */
  @NotNull @Valid 
  @Schema(name = "totalPages", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalPages")
  public BigDecimal getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(BigDecimal totalPages) {
    this.totalPages = totalPages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaginationMeta paginationMeta = (PaginationMeta) o;
    return Objects.equals(this.page, paginationMeta.page) &&
        Objects.equals(this.perPage, paginationMeta.perPage) &&
        Objects.equals(this.total, paginationMeta.total) &&
        Objects.equals(this.totalPages, paginationMeta.totalPages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, perPage, total, totalPages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaginationMeta {\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    perPage: ").append(toIndentedString(perPage)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

