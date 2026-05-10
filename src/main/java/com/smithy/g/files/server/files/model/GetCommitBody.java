package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.CommitDTO;
import com.smithy.g.files.server.files.model.CommitFile;
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
 * GetCommitBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class GetCommitBody {

  private CommitDTO commit;

  @Valid
  private List<@Valid CommitFile> files = new ArrayList<>();

  public GetCommitBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public GetCommitBody(CommitDTO commit) {
    this.commit = commit;
  }

  public GetCommitBody commit(CommitDTO commit) {
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
  public CommitDTO getCommit() {
    return commit;
  }

  public void setCommit(CommitDTO commit) {
    this.commit = commit;
  }

  public GetCommitBody files(List<@Valid CommitFile> files) {
    this.files = files;
    return this;
  }

  public GetCommitBody addFilesItem(CommitFile filesItem) {
    if (this.files == null) {
      this.files = new ArrayList<>();
    }
    this.files.add(filesItem);
    return this;
  }

  /**
   * Archivos modificados en este commit
   * @return files
  */
  @Valid 
  @Schema(name = "files", description = "Archivos modificados en este commit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("files")
  public List<@Valid CommitFile> getFiles() {
    return files;
  }

  public void setFiles(List<@Valid CommitFile> files) {
    this.files = files;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetCommitBody getCommitBody = (GetCommitBody) o;
    return Objects.equals(this.commit, getCommitBody.commit) &&
        Objects.equals(this.files, getCommitBody.files);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commit, files);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetCommitBody {\n");
    sb.append("    commit: ").append(toIndentedString(commit)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
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

