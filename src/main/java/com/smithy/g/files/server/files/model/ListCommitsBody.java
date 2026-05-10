package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.CommitDTO;
import com.smithy.g.files.server.files.model.PaginationMeta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ListCommitsBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class ListCommitsBody {

  @Valid
  private List<@Valid CommitDTO> commits = new ArrayList<>();

  private PaginationMeta pagination;

  public ListCommitsBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ListCommitsBody(List<@Valid CommitDTO> commits, PaginationMeta pagination) {
    this.commits = commits;
    this.pagination = pagination;
  }

  public ListCommitsBody commits(List<@Valid CommitDTO> commits) {
    this.commits = commits;
    return this;
  }

  public ListCommitsBody addCommitsItem(CommitDTO commitsItem) {
    if (this.commits == null) {
      this.commits = new ArrayList<>();
    }
    this.commits.add(commitsItem);
    return this;
  }

  /**
   * Get commits
   * @return commits
  */
  @NotNull @Valid 
  @Schema(name = "commits", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("commits")
  public List<@Valid CommitDTO> getCommits() {
    return commits;
  }

  public void setCommits(List<@Valid CommitDTO> commits) {
    this.commits = commits;
  }

  public ListCommitsBody pagination(PaginationMeta pagination) {
    this.pagination = pagination;
    return this;
  }

  /**
   * Get pagination
   * @return pagination
  */
  @NotNull @Valid 
  @Schema(name = "pagination", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pagination")
  public PaginationMeta getPagination() {
    return pagination;
  }

  public void setPagination(PaginationMeta pagination) {
    this.pagination = pagination;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListCommitsBody listCommitsBody = (ListCommitsBody) o;
    return Objects.equals(this.commits, listCommitsBody.commits) &&
        Objects.equals(this.pagination, listCommitsBody.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commits, pagination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListCommitsBody {\n");
    sb.append("    commits: ").append(toIndentedString(commits)).append("\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
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

