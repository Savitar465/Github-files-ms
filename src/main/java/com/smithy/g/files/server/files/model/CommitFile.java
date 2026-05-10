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
 * Archivo cambiado en un commit o comparacion
 */

@Schema(name = "CommitFile", description = "Archivo cambiado en un commit o comparacion")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CommitFile {

  private String filename;

  private String status;

  private BigDecimal additions;

  private BigDecimal deletions;

  private BigDecimal changes;

  private String patch;

  public CommitFile() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CommitFile(String filename, String status) {
    this.filename = filename;
    this.status = status;
  }

  public CommitFile filename(String filename) {
    this.filename = filename;
    return this;
  }

  /**
   * Get filename
   * @return filename
  */
  @NotNull 
  @Schema(name = "filename", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("filename")
  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public CommitFile status(String status) {
    this.status = status;
    return this;
  }

  /**
   * added, modified, deleted, renamed
   * @return status
  */
  @NotNull 
  @Schema(name = "status", description = "added, modified, deleted, renamed", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CommitFile additions(BigDecimal additions) {
    this.additions = additions;
    return this;
  }

  /**
   * Get additions
   * @return additions
  */
  @Valid 
  @Schema(name = "additions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("additions")
  public BigDecimal getAdditions() {
    return additions;
  }

  public void setAdditions(BigDecimal additions) {
    this.additions = additions;
  }

  public CommitFile deletions(BigDecimal deletions) {
    this.deletions = deletions;
    return this;
  }

  /**
   * Get deletions
   * @return deletions
  */
  @Valid 
  @Schema(name = "deletions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deletions")
  public BigDecimal getDeletions() {
    return deletions;
  }

  public void setDeletions(BigDecimal deletions) {
    this.deletions = deletions;
  }

  public CommitFile changes(BigDecimal changes) {
    this.changes = changes;
    return this;
  }

  /**
   * Get changes
   * @return changes
  */
  @Valid 
  @Schema(name = "changes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("changes")
  public BigDecimal getChanges() {
    return changes;
  }

  public void setChanges(BigDecimal changes) {
    this.changes = changes;
  }

  public CommitFile patch(String patch) {
    this.patch = patch;
    return this;
  }

  /**
   * Diff del archivo
   * @return patch
  */
  
  @Schema(name = "patch", description = "Diff del archivo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("patch")
  public String getPatch() {
    return patch;
  }

  public void setPatch(String patch) {
    this.patch = patch;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitFile commitFile = (CommitFile) o;
    return Objects.equals(this.filename, commitFile.filename) &&
        Objects.equals(this.status, commitFile.status) &&
        Objects.equals(this.additions, commitFile.additions) &&
        Objects.equals(this.deletions, commitFile.deletions) &&
        Objects.equals(this.changes, commitFile.changes) &&
        Objects.equals(this.patch, commitFile.patch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filename, status, additions, deletions, changes, patch);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitFile {\n");
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    additions: ").append(toIndentedString(additions)).append("\n");
    sb.append("    deletions: ").append(toIndentedString(deletions)).append("\n");
    sb.append("    changes: ").append(toIndentedString(changes)).append("\n");
    sb.append("    patch: ").append(toIndentedString(patch)).append("\n");
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

