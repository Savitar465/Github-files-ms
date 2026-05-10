package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.CommitSummaryDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * DeleteFileResponseBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class DeleteFileResponseBody {

  private CommitSummaryDTO commit;

  public DeleteFileResponseBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DeleteFileResponseBody(CommitSummaryDTO commit) {
    this.commit = commit;
  }

  public DeleteFileResponseBody commit(CommitSummaryDTO commit) {
    this.commit = commit;
    return this;
  }

  /**
   * Get commit
   * @return commit
  */
  @NotNull @Valid 
  @Schema(name = "commit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("commit")
  public CommitSummaryDTO getCommit() {
    return commit;
  }

  public void setCommit(CommitSummaryDTO commit) {
    this.commit = commit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteFileResponseBody deleteFileResponseBody = (DeleteFileResponseBody) o;
    return Objects.equals(this.commit, deleteFileResponseBody.commit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteFileResponseBody {\n");
    sb.append("    commit: ").append(toIndentedString(commit)).append("\n");
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

