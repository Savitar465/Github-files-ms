package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.CommitSummaryDTO;
import com.smithy.g.files.server.files.model.FileContentDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Respuesta de operacion de archivo (create/update/delete)
 */

@Schema(name = "FileOperationResponse", description = "Respuesta de operacion de archivo (create/update/delete)")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class FileOperationResponse {

  private FileContentDTO content;

  private CommitSummaryDTO commit;

  public FileOperationResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FileOperationResponse(FileContentDTO content, CommitSummaryDTO commit) {
    this.content = content;
    this.commit = commit;
  }

  public FileOperationResponse content(FileContentDTO content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
  */
  @NotNull @Valid 
  @Schema(name = "content", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("content")
  public FileContentDTO getContent() {
    return content;
  }

  public void setContent(FileContentDTO content) {
    this.content = content;
  }

  public FileOperationResponse commit(CommitSummaryDTO commit) {
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
    FileOperationResponse fileOperationResponse = (FileOperationResponse) o;
    return Objects.equals(this.content, fileOperationResponse.content) &&
        Objects.equals(this.commit, fileOperationResponse.commit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, commit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileOperationResponse {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
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

