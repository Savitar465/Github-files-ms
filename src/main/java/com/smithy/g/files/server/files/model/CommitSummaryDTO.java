package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.CommitSignature;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Resumen de commit (para responses de operaciones)
 */

@Schema(name = "CommitSummaryDTO", description = "Resumen de commit (para responses de operaciones)")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CommitSummaryDTO {

  private String sha;

  private String message;

  private CommitSignature author;

  private CommitSignature committer;

  private String htmlUrl;

  public CommitSummaryDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CommitSummaryDTO(String sha, String message, CommitSignature author, CommitSignature committer) {
    this.sha = sha;
    this.message = message;
    this.author = author;
    this.committer = committer;
  }

  public CommitSummaryDTO sha(String sha) {
    this.sha = sha;
    return this;
  }

  /**
   * Get sha
   * @return sha
  */
  @NotNull 
  @Schema(name = "sha", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sha")
  public String getSha() {
    return sha;
  }

  public void setSha(String sha) {
    this.sha = sha;
  }

  public CommitSummaryDTO message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @NotNull 
  @Schema(name = "message", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public CommitSummaryDTO author(CommitSignature author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  @NotNull @Valid 
  @Schema(name = "author", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("author")
  public CommitSignature getAuthor() {
    return author;
  }

  public void setAuthor(CommitSignature author) {
    this.author = author;
  }

  public CommitSummaryDTO committer(CommitSignature committer) {
    this.committer = committer;
    return this;
  }

  /**
   * Get committer
   * @return committer
  */
  @NotNull @Valid 
  @Schema(name = "committer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("committer")
  public CommitSignature getCommitter() {
    return committer;
  }

  public void setCommitter(CommitSignature committer) {
    this.committer = committer;
  }

  public CommitSummaryDTO htmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
    return this;
  }

  /**
   * Get htmlUrl
   * @return htmlUrl
  */
  @Size(max = 500) 
  @Schema(name = "htmlUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("htmlUrl")
  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setHtmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitSummaryDTO commitSummaryDTO = (CommitSummaryDTO) o;
    return Objects.equals(this.sha, commitSummaryDTO.sha) &&
        Objects.equals(this.message, commitSummaryDTO.message) &&
        Objects.equals(this.author, commitSummaryDTO.author) &&
        Objects.equals(this.committer, commitSummaryDTO.committer) &&
        Objects.equals(this.htmlUrl, commitSummaryDTO.htmlUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sha, message, author, committer, htmlUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitSummaryDTO {\n");
    sb.append("    sha: ").append(toIndentedString(sha)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    committer: ").append(toIndentedString(committer)).append("\n");
    sb.append("    htmlUrl: ").append(toIndentedString(htmlUrl)).append("\n");
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

